<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>管理</title>

<style>
	html {
		width:100%;
		height:100%;
	}
	body {
		width:100%;
		height:100%;
		margin:0;
		padding:0;
	}
</style>

<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>
<script>
var curOpenid = "";
var managerId = "";
	$(document).ready(function() {
		curOpenid = "${openid}";
		managerId = "${manager.openid}";
		if(curOpenid == managerId) {//如果是管理员的话，才绑定事件。否则不绑定。
			var touch_Element = 0;
			var lines = document.getElementsByClassName("lineDiv");
			for(var i=0;i<lines.length;i++){
				lines[i].addEventListener("click",function(event){
					if(this.style.left == "0%" || !this.style.left){
						$(this).css("left","-20%");
						$(this).siblings("img").css("display","");
					} else {
						$(this).css("left","0%");
						//$("#"+this.id+"_del").css("display","none");
						$(this).siblings("img").css("display","none");
					}
				},false);
			}
		}
	});
	
	function delUser(delOpenid,eve){
		if(curOpenid != managerId || delOpenid == managerId) {//如果不是管理员
			return;
		}
		eve.cancelBubble = true;
		if(confirm("确定删除该用户？")) {
			var fogDid = "${fogDid}";
			$.ajax({
				type : "POST",
				url : "${ctx}/deleteBind.htm",
				data : {
					"fogDid" : fogDid,
					"delOpenid" : delOpenid
				},
				timeout : 40000,
				dataType : "json",
				success : function(data) {
					if (data.errmsg == 'ok') {
						//如果删除成功,那么就删除该dom元素
						$("#"+delOpenid).remove();
					} else {
						alert("数据错误！");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("数据错误！");
				}		
			});
		} else {
			$("#"+delOpenid+"_line").css("left","0%");
			$("#"+delOpenid+"_del").css("display","none");
		}
		
	}
</script>
</head>
<body style="overflow: hidden;">
	<div style="height:100%;">
		<div style="width:100%;border-bottom:1px solid #A4A4A4;">
			<div style="width:40%;margin:2.5% auto;">
				<img src="${ctx }/common/images/user/QRcode.png" style="width:100%;">
			</div>
		</div>
		
		<!-- 管理员 -->
		<div id="${manager.openid }" style="width:90%;float:right;border-bottom:1px solid #A4A4A4;height:12%;position: relative;">
			<div id="${manager.openid }_line" class="" style="width:100%;height:100%;position: relative;-webkit-transition:0.5s;-moz-transition:0.5s;">
				<img src="${manager.headimgurl }" style="height: 90%;float:left;position: relative;top:5%;margin-right:2%;">
				<div style="height:90%;float:left;position: relative;top:5%;width:50%;font-size:1em;font-weight:550;">
					<span style="position: relative;top:20%;">${manager.nickName }</span>
					<img src="${ctx }/common/images/user/male.png" style="height:40%;position:relative;top:20%;">
					<br>
					<span style="position: absolute;bottom:0%;">${manager.sysFlag }</span>
				</div>
			</div>
		</div>
	<c:forEach items="${userList }" var="user">
		<div id="${user.openid }" style="width:90%;float:right;border-bottom:1px solid #A4A4A4;height:12%;position: relative;">
			<div id="${user.openid }_line" class="lineDiv" style="width:100%;height:100%;position: relative;-webkit-transition:0.5s;-moz-transition:0.5s;">
				<img src="${user.headimgurl }" style="height: 90%;float:left;position: relative;top:5%;margin-right:2%;">
				<div style="height:90%;float:left;position: relative;top:5%;width:50%;font-size:1em;font-weight:550;">
					<span style="position: relative;top:20%;">${user.nickName }</span>
					<img src="${ctx }/common/images/user/male.png" style="height:40%;position:relative;top:20%;">
					<br>
					<span style="position: absolute;bottom:0%;">备注备注备注</span>
				</div>
			</div>
			<img src="${ctx }/common/images/user/delete.png" id="${user.openid }_del" style="height:100%;width:25%;position:absolute;right:0;bottom:0;display:none;" onclick="delUser('${user.openid}',event)">
		</div>
	</c:forEach>

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
		<img style="height:50%;" src="${ctx}/common/images/mainControl/menuc.png" onclick="toShowScenes()"><br/>
		情景
	</div>
	<div style="width:25%;height:98%;float:right;padding-top: 2%;text-align: center;" id="menud">
		<img style="height:50%;" src="${ctx}/common/images/mainControl/menud_on.png" onclick="toUserManage()"><br/>
		管理
	</div>
	</div>
</div>
	
	
</body>
</html>