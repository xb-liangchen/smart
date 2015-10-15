<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<html>
<head>
<title>设备</title>
<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
window.onload = function(){
	$("#menua").click(function(){
		location.href="${ctx}/lightList.htm?did=${did}&uid=${uid }";
	});

}
</script>
</head>
<body style=" margin:0; padding:0;">

<div style="width:100%;padding-top: 1em;">
	<div style="margin-top:0.3em ;line-height:4em;text-align:center;width:43%;height:4em;float:left;margin-left: 1em;background: url(${ctx }/common/images/sceneControl/scenebg.png);background-repeat:no-repeat;background-size:100% 100%;">
		情景1
	</div>

	<div style="margin-top:0.3em ;line-height:4em;text-align:center;width:43%;height:4em;float:left;margin-left: 1em;">
		<div style="line-height:4em;width:46%;height:100%;float:left;background: url(${ctx }/common/images/sceneControl/scenebg2.png);background-repeat:no-repeat;background-size:100% 100%;">
			<img style="padding:0.7em;" src="${ctx }/common/images/sceneControl/add.png">
		</div>
		<div style="line-height:4em;width:46%;height:100%;float:right;background: url(${ctx }/common/images/sceneControl/scenebg2.png);background-repeat:no-repeat;background-size:100% 100%;">
			<img style="padding:0.7em;" src="${ctx }/common/images/sceneControl/edit.png">
		</div>
	</div>	
</div>

<div style="height:5em;position: fixed;bottom: 0px;z-index: 999;width:100%;background-image: url(${ctx}/common/images/mainControl/menuBackground.png) ; background-repeat: repeat-x;">
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

</body>
</html>