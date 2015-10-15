<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>设备</title>
<style type="text/css">

.sceneDelete{
	width:43%;	
	margin-top:0.3em ;
	height:4.4em;
	float:left;
	margin-left: 1em;
	position:relative;
}
.sceneoff{
	line-height:4em;
	text-align:center;
	width:95%;
	height:4em;
	margin-top:0.5em;
	background: url(${ctx }/common/images/sceneControl/scenebg.png);
	background-repeat:no-repeat;
	background-size:100% 100%;
	position:absolute;
}

.sceneN{display: none;position:absolute;z-index: 10;}
.sceneY{display: block;position:absolute;z-index: 10;right:0;}
.round{width:80%;  margin:0 auto; position:relative;}
.img2{ position:absolute; left:0px; top:0px; z-index:5;   }


</style>
<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
window.onload = function(){
	var width = document.documentElement.clientWidth*0.8;
	var width3 = width/640*110;
	$("#img1").css("width",width+"px");
	$("#img2").css("width",width+"px");
	$("#img3").css("width",width+"px");
	$("#img4").css("width",width+"px");
	$("#imge").css("width",width+"px");
	
	$("#imga").css("width",width3+"px");
	$("#imgb").css("width",width3+"px");
	$("#imgc").css("width",width3+"px");
	$("#imgd").css("width",width3+"px");

	

	
	$("#menua").click(function(){
		location.href="${ctx}/lightList.htm?openid=${openid }";
	});

	$("#addScene").click(function(){
		document.getElementById("openWinDiv").style.visibility = "visible";
	});

	$("#editScene").click(function(){
		if($(".sceneN").length==0){
			$(".sceneY").removeClass("sceneY").addClass("sceneN");
		}else{
			$(".sceneN").removeClass("sceneN").addClass("sceneY");
		}
	});

	$(".sceneN,.sceneY").each(function(){ 
		$(this).click(function(){ alert(1);
			var id = $(this).attr("id"); 
			$.ajax({
				   type: "POST",
				   url: "${ctx}/deleteSceneForOneLight.htm",
				   data:{"id":id},
				   timeout: 40000,
				   dataType: "json", 
				   success: function(data){ 
					   if(data.errmsg=='ok'){
						   $("#oneScene_"+id).remove();
						}else{
							alert("数据错误！");
						}
				   },
				   error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert("数据错误！");
					 }
				});
		});	
	});

	$("#canclediv").click(function (){
		  document.getElementById("openWinDiv").style.visibility = "hidden";
	});

	$("#surediv").click(function (){
		document.getElementById("openWinDiv").style.visibility = "hidden";
		var sceneName = $("#sceneName").val();
		if(sceneName==null || sceneName==''){
			
		}else{
			var params = "";
			params +="?openid=${openid}";
			params +="&lightId=${sysLight.id}";
			params +="&did=${did}";
			params +="&brightness="+111;
			params +="&temperature="+111;
			params +="&rgb="+111;
			params +="&swithLight="+1;
			params +="&whiteBrightness="+1;
			params +="&colorBrightness="+1;
			params +="&lightType=${lightType}";
			params +="&onOff="+1;
			 $.ajax({
				   type: "POST",
				   url: "${ctx}/addSceneForOneLight.htm"+params,
				   data:{"name":sceneName},
				   timeout: 40000,
				   dataType: "json", 
				   success: function(data){ 
					   if(data.errmsg=='ok'){
						    var html = "<div id='oneScene_"+data.id+"' class='sceneDelete'>";
						    html += "<div id='"+data.id+"' class='sceneN'><img src='${ctx}/common/images/sceneControl/delete.png'/></div>";
							html += "<div class='sceneoff'>"+sceneName+"</div></div>";
							$("#sceneControle").before(html);
						}else{
							alert("数据错误！");
						}
					   
				   },
				   error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert("数据错误！");
					 }
				   });
		}
	});

}
</script>
</head>
<body style=" margin:0; padding:0;">

<div style="width:80%;  margin:0 auto; position:relative;">
	 <img src="${ctx}/common/images/light/rgb.png" id="img1"/> 
	 <img src="${ctx}/common/images/light/sw.png" id="img2" style="position: absolute;top:0;left:0;"/> 
	 <img src="${ctx}/common/images/light/ct1.png" id="img3" style="position: absolute;top:0;left:0;"/> 
	 <img src="${ctx}/common/images/light/ct2.png" id="img4" style="position: absolute;top:0;left:0;"/> 
     <img src="${ctx}/common/images/oneLightControl/btn1.png" id="imga" style="position:absolute;top:0;left:0;"/> 
	 <img src="${ctx}/common/images/oneLightControl/btn2.png" id="imgb" style="position:absolute;top:0;right:0;"/> 
	 <img src="${ctx}/common/images/oneLightControl/btn3.png" id="imgc" style="position:absolute;bottom:0;left:0;"/> 
	 <img src="${ctx}/common/images/oneLightControl/btn4.png" id="imgd" style="position:absolute;bottom:0;right:0;"/> 
	 <img src="${ctx}/common/images/light/on.png" id="imge" style="position: absolute;top:0;left:0;"/> 
</div>
<div  style="width:90%;  margin:0 auto;padding-top: 1em; ">
	<div >
		<span>彩色</span>
		<img id="img5" src="${ctx}/common/images/light/seta.png" style="width:5%;">
		
		<img id="color_1" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
		<img id="color_2" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
		<img id="color_3" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
		<img id="color_4" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
		<img id="color_5" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
		<img id="color_6" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
		<img id="color_7" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
		<img id="color_8" src="${ctx}/common/images/oneLightControl/grey.png" style="width:6%;">
		<img id="color_9" src="${ctx}/common/images/oneLightControl/grey.png" style="width:6%;">
		<img id="color_10" src="${ctx}/common/images/oneLightControl/grey.png" style="width:6%;">
		
		<img id="img6" src="${ctx}/common/images/light/setb.png" style="width:8%;">
	</div>
	<div style="padding-top: 1em;">
		<span>白色</span>
			<img id="img5" src="${ctx}/common/images/light/seta.png" style="width:5%;">
		
			<img id="white_1" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
			<img id="white_2" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
			<img id="white_3" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
			<img id="white_4" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
			<img id="white_5" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
			<img id="white_6" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
			<img id="white_7" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;">
			<img id="white_8" src="${ctx}/common/images/oneLightControl/grey.png" style="width:6%;">
			<img id="white_9" src="${ctx}/common/images/oneLightControl/grey.png" style="width:6%;">
			<img id="white_10" src="${ctx}/common/images/oneLightControl/grey.png" style="width:6%;">
			
			<img id="img6" src="${ctx}/common/images/light/setb.png" style="width:8%;">
	</div>
</div>

<div style="width:100%;padding-top: 1em;">
	<c:forEach items="${sysScenes }" var="oneScene">
		<div id="oneScene_${oneScene.id }" class="sceneDelete" >
			<div id="${oneScene.id }" class="sceneN"><img src="${ctx }/common/images/sceneControl/delete.png"/></div>
			<div class="sceneoff">
				${oneScene.name }
			</div>
		</div>
	</c:forEach>

	<div id="sceneControle" style="margin-top:0.7em ;line-height:4em;text-align:center;width:43%;height:4em;float:left;margin-left: 1em;margin-bottom: 5.5em;">
		<div id="addScene" style="line-height:4em;width:46%;height:100%;float:left;background: url(${ctx }/common/images/sceneControl/scenebg2.png);background-repeat:no-repeat;background-size:100% 100%;">
			<img style="padding:0.7em;" src="${ctx }/common/images/sceneControl/add.png">
		</div>
		<div id="editScene" style="line-height:4em;width:46%;height:100%;float:right;background: url(${ctx }/common/images/sceneControl/scenebg2.png);background-repeat:no-repeat;background-size:100% 100%;">
			<img style="padding:0.7em;" src="${ctx }/common/images/sceneControl/edit.png">
		</div>
	</div>	
</div>

<div style="height:5em;position: fixed;bottom: 0px;z-index:999;width:100%;background-image: url(${ctx}/common/images/mainControl/menuBackground.png) ; background-repeat: repeat-x;">
	<div style="width:25%;height:98%;float:left;padding-top: 0.5em;text-align: center;" id="menua">
		<img style="padding-bottom: 0.3em;" src="${ctx}/common/images/mainControl/menua_on.png"><br/>
		设备
	</div>
	<div style="width:25%;height:98%;float:left;padding-top: 0.7em;text-align: center;" id="menub">
		<img style="padding-bottom: 0.5em;" src="${ctx}/common/images/mainControl/menub.png"><br/>
		组控
	</div>
	<div style="width:25%;height:98%;float:left;padding-top: 0.5em;text-align: center;" id="menuc">
		<img style="padding-bottom: 0.3em;" src="${ctx}/common/images/mainControl/menuc.png"><br/>
		情景
	</div>
	<div style="width:25%;height:98%;float:right;padding-top: 0.5em;text-align: center;" id="menud">
		<img style="padding-bottom: 0.3em;" src="${ctx}/common/images/mainControl/menud.png"><br/>
		管理
	</div>
</div>

<!-- 提示框 -->
	<div id="openWinDiv" style="position: fixed;top: 0;z-index: 999;width: 100%;height: 100%;background: rgba(8, 8, 8, 0.7); visibility: hidden;">
		<div style="position: relative;top:30%;height: 60%;text-align: center;">
		      <div class="font-size18" id="popwindows1" style="width: 90%;background: #ffffff no-repeat;border-radius: 25px; position:absolute;margin: 0 auto;left:5%;">
		           <div id="openTitlediv" style="height: 4em;line-height: 4em;font-weight:bold;">添加情景模式</div>
		           <div id="inputdiv" style="border-bottom: 1px solid #EAEAEA;height:5em;">
		           		<input id="sceneName" type="text" style="width:90%;height:70%;font-size: 100%" placeholder="输入情景名"/>
		           	</div>
		           <div style="line-height: 3em;"> 
			           <div id="canclediv"  style="text-align: center;float: left;width: 49%;display: inline;cursor: pointer;border-right: 1px solid #e3e3e3;">取消</div> 
			           <div id="surediv"   style="text-align: center;display: inline;width: 50%;float: right;cursor: pointer;">确定</div>
		           </div>
		      </div>
		 </div>
	</div>

</body>
</html>