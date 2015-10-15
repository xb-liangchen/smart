<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../common/jsp/include_tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>

<script type="text/javascript">
	var idArray = new Array();
	var nameArray = new Array();
	var count = 0;
	
	$(document).ready(function() {
		
	});
	function showMultiSceneDetail(ele) {
		var sceneId = ele.attr("id");
		location.href = "${ctx}/showMultiSceneDetail.htm?sceneId=" + sceneId+"&fogDid=${fogDid}&openid=${openid}";
	}
	
	function showAddBlock() {//显示输入框
		$("#addBlock").css("display", "");//点击添加按钮触发,显示输入框
		$("#wrapDiv").css("opacity","0.5");//将背景设置为半透明
		$("input").focus();//焦点定位
	}

	function cancelAddBlock() {//点击取消按钮，让输入框隐藏,并将输入框的值和颜色设置为初始状态
		$("#addBlock").css("display", "none");
		$("input").val("");
		$("#wrapDiv").css("opacity","1");//将背景设置为实体
	}
	
	function addDivElement(id,name){//添加成功后，操作DOM直接在页面增加一行数据，就不用刷新页面了。
		var ele = $(".j_editBlock").prev().clone();
		ele.css("display","");
		ele.attr("id",id);
		ele.children("span").text(name);
		ele.children("span").attr("id","span_"+id);
		ele.insertBefore(".j_editBlock");
	}
	
	function submitAddBlock() {//提交输入单，这里还是不通过ajax访问为好。
		var name = $("#addInput").val().trim();
		var fogDid = "${fogDid}";
		var openid = "${openid}";
		//对name进行验证，如果通不过，那么返回；
		if (!name || name.length>6) {
			alert("情景名不规范！");
			$("input").focus();//焦点定位
			return;
		}
		$.ajax({
			type : "POST",
			url : "${ctx}/addMultiScene.htm",
			data : {
				"name" : name,
				"fogDid" : fogDid,
				"openid" : openid
			},
			timeout : 40000,
			dataType : "json",
			success : function(data) {
				if (data.errmsg == 'ok') {
					//如果添加成功,那么直接在页面增加一个div用来显示数据
					addDivElement(data.sceneId,name);
					cancelAddBlock();//隐藏添加模块。
				} else {
					alert("数据错误！");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据错误！");
			}
		});
	}
	
	function addInQueue(ele) {//点击图片后，将该图片表示的情景加入预删除队列
		idArray[count] = ele.attr("id");//将要删除的sceneId加入数组
		nameArray[count] = ele.children("span").text();//将要删除的sceneName加入name数组，方便给予用户提示
		count++;
		ele.css("display", "none");
	}
	
	function recover(idArray) {//取消删除后，恢复显示相应的div
		for(var i=0;i<idArray.length;i++){
			$("#"+idArray[i]).css("display","");
		}
	}
	
	function removeElement(delArray){//此方法真正意义上将元素移除掉，即在用户删除成功后，调用该方法，将element真正的移除掉，而不是仅仅的display:none;
		for(var i=0;i<delArray.length;i++){
			$("#"+delArray[i]).remove();
		}
	}
	//不知道为什么$().toggle()会把元素给隐藏掉，只好通过这种手动的方法来达成效果
	function preDelete(ele) {//准备删除，即将图片从点击查看明细，变成点击预删除
		$(".deleteImg").css("display", "");//显示删除图片
		ele.children("img").attr("src", "${ctx }/common/images/scene/g.png");//将图片转换为g.png
		$(".mainDiv").attr("onclick", "addInQueue($(this))");//将mainDiv的事件切换
		ele.attr("onclick", "ensureDelete($(this))");//将本身事件切换为ensureDelete();
	}

	function ensureDelete(ele) {//确定删除刚才这些对象
		if (count > 0 && confirm("确认删除情景:\n" + nameArray)) {//如果确认删除，那么就将刚才那些全部删除
			var openid = "${openid}";
			var delArray = idArray;
			$.ajax({
				type : "GET",
				url : "${ctx}/deleteMultiScene.htm",
				data : {
					"sceneIds" : idArray,
					"openid" : openid
				},
				timeout : 40000,
				dataType : "json",
				cache : false,
				ansy : false,
				success : function(data) {
					if (data.errmsg == 'ok') {//如果删除成功
						removeElement(delArray);//将删除掉的情景移除掉
						alert("已经成功删除！");
					} else {
						alert("数据错误！");
						recover(idArray);//删除失败的话,就将图片设置为可见状态
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("数据错误！");
					recover(idArray);//删除失败的话,就将图片设置为可见状态
				}
			});
		} else {
			//为了少访问一次数据库，居然写了三遍这条语句，有没有更好点的办法？？
			recover(idArray);
		}
		$(".deleteImg").css("display", "none");//将删除的小图片隐藏
		ele.children("img").attr("src", "${ctx }/common/images/scene/edit.png");//将子图片转换为edit.png
		idArray = [];//将id数组清空
		nameArray = [];//将name数组清空
		count = 0;//count归0
		$(".mainDiv").attr("onclick", "showMultiSceneDetail($(this))");//将mainDiv的事件切换为查看状态
		ele.attr("onclick", "preDelete($(this))");//将本身事件还原
	}
	
</script>

<style type="text/css">
.html {
	width: 100%;
	height: 100%;
}
</style>
<title>情景</title>
</head>
<body style="margin: 0; padding: 0; text-align: center;">
	<div id="wrapDiv"
		style="width: 100%; margin-top: 2%; filter: alpha(opacity = 40); opacity: 1;">
		
		<!-- 隐藏的div，以解决第一次添加情景时，页面中没有可供clone的div模板而导致的小bug -->
		<div style="display:none;width:48%;line-height:3.5em;position: relative;float:left;margin:1%;background-image: url('${ctx}/common/images/scene/btn1.png');background-repeat:no-repeat;background-size:100% 100%;"
			class="mainDiv" id="${scene.id }" onclick="showMultiSceneDetail($(this))">
			<span id="span_${scene.id }" style="font-size: 1.2em;">${scene.name }</span>
			<img class="deleteImg"
				style="margin: 0px; right: 0; top: 0; display: none; position: absolute;"
				src="${ctx }/common/images/scene/delete.png" width="14%"
				height="18%">
		</div>
		
		<c:forEach items="${sceneList }" var="scene">
			<div
				style="width:48%;line-height:3.5em;position: relative;float:left;margin:1%;background-image: url('${ctx}/common/images/scene/btn1.png');background-repeat:no-repeat;background-size:100% 100%;"
				class="mainDiv" id="${scene.id }"
				onclick="showMultiSceneDetail($(this))">
				
				<span id="span_${scene.id }" style="font-size: 1.2em;">${scene.name }</span>
				
				<img class="deleteImg"
					style="margin: 0px; right: 0; top: 0; display: none; position: absolute;"
					src="${ctx }/common/images/scene/delete.png" width="14%"
					height="18%">
			</div>
		</c:forEach>
		
		<div class="j_editBlock"
			style="width: 48%; text-align: center; line-height: 3.5em; margin: 1%; float: left;">
			<div id="jDiv_${scene.id }"
				style="float:left;line-height:3.5em;width:48%;position: relative; background-image: url('${ctx}/common/images/scene/btn2.png');background-repeat:no-repeat;background-size:100% 100%;"
				onclick="showAddBlock()">
				&nbsp;<img id="jImg_${scene.id }"
					style="position: absolute; left: 30%; top: 30%;"
					src="${ctx }/common/images/scene/j.png" width="40%" height="40%">
			</div>
			<div id="editDiv_${scene.id }"
				style="float:right;line-height:3.5em;width:48%;position: relative;background-image: url('${ctx}/common/images/scene/btn2.png');background-repeat:no-repeat;background-size:100% 100%;"
				onclick="preDelete($(this))">
				&nbsp;<img id="editImg_${scene.id }"
					style="position: absolute; left: 30%; top: 30%;"
					src="${ctx }/common/images/scene/edit.png" width="40%" height="40%">
			</div>
		</div>
	</div>
	<!-- wrapDiv end -->
	<div id="addBlock"
		style="line-height: 3em; position: fixed; margin-top: 25%; margin-left: 10%; width: 80%; display: none; background-color: #F2F2F2; border-radius: 15px;">
		<span style="font-size: 1.3em; font-weight: 500;">添加情景模式</span><br>
		<input id="addInput" type="text" placeholder="请输入情景名！"
			style="width: 80%; border-radius: 0px; margin: 1em; padding: 2%;line-height:2em;font-size:1em;">
		<br>
		<div
			style="width: 50%; border-top: solid 1px #D9D9D9; border-right: solid 1px #D9D9D9; float: left; box-sizing: border-box;"
			onclick="cancelAddBlock()">取消</div>
		<div
			style="width: 50%; border-top: solid 1px #D9D9D9; float: right; box-sizing: border-box; color: #0D75E0" onclick="submitAddBlock()">确定</div>
	</div>
	
	
	<!-- footer -->
	<%@include file="../../../common/jsp/footer.jsp" %>
	<div style="heihgt:3.5em;">&nbsp;</div><!-- 空白占位符，防止等列表太多刷新不出來。 -->
	<div style="height:3.5em;font-size:0.8em;position: fixed;bottom: 0px;width:100%;background-image: url(${ctx}/common/images/mainControl/menuBackground.png) ; background-repeat: repeat-x;">
	<div style="width:25%;height:98%;float:left;padding-top: 2%;text-align: center;" id="menua">
		<img style="height:50%;" src="${ctx}/common/images/mainControl/menua.png"><br/>
		设备
	</div>
	<div style="width:25%;height:98%;float:left;padding-top: 2%;text-align: center;" id="menub">
		<img style="height:50%;" src="${ctx}/common/images/mainControl/menub.png"><br/>
		组控
	</div>
	<div style="width:25%;height:98%;float:left;padding-top: 2%;text-align: center;" id="menuc">
		<img style="height:50%;" src="${ctx}/common/images/mainControl/menuc_on.png" onclick="toShowScenes()"><br/>
		情景
	</div>
	<div style="width:25%;height:98%;float:right;padding-top: 2%;text-align: center;" id="menud">
		<img style="height:50%;" src="${ctx}/common/images/mainControl/menud.png" onclick="toUserManage()"><br/>
		管理
	</div>
	</div>
</body>
</html>