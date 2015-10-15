<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">

<title>设备</title>

<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	function preSaveSceneDetail(ele){
		var lightId = ele.attr("id");
		//需要传输的参数有lightId,sceneId,
		location.href = "${ctx}/preAddMultiSceneDetail.htm?lightId="+lightId+"&sceneId=${sceneId}&fogDid=${fogDid}&openid=${openid}";
	}
</script>
</head>
<body style=" margin:0; padding:0;height: 100%;">
<div class="wrapDiv" style="width: 96%;margin-left: 4%;margin-bottom: 20%;">
	<c:if test="${empty lightList }">
		<center><span style="font-size: 1.5em;font-weight: 600">该设备下所有灯都已加入该情景</span></center>
	</c:if>
	<c:forEach var="light" items="${lightList }">
		<div class="mainDiv" id="${light.id }" style="position: relative;border-bottom: 1px solid #D9D9D9;overflow: hidden;line-height:1em;font-size: 1em;color:#A9A9A9;" onclick="preSaveSceneDetail($(this))">
			<div style="float:left;padding:0.5em 0;line-height:1.25em;">
				<span style="font-size: 1.25em;color: black;font-weight: 600;" class="nameSpan">${light.name }</span><br>
				<span style="" class="">ID:${light.id }</span><br>
				<span style="" class="">类型: ${light.lightType == 1?'RGB+调光调色LED灯':'调光调色温LED灯' }</span><br>
			</div>
			<div style="float:right;height:100%;line-height:4.75em;position:absolute;right:1%;text-align: right;">
				<img style="height: 1em;" src="${ctx }/common/images/mainControl/${light.isOnline == 1?'online':'offline' }.png">
				<span>${light.isOnline == 1?'在线':'离线' }</span>
			</div>
		</div>
	</c:forEach>
</div>
<!--  -->
	
	<!-- footer -->
	<%@include file="../../../common/jsp/footer.jsp" %>
	<div style="heihgt:3.5em;">&nbsp;</div><!-- 空白占位符，防止等列表太多刷新不出來。 -->
	<div style="height:3.5em;font-size:0.8em;position: fixed;bottom: 0px;width:100%;background-image: url(${ctx}/common/images/mainControl/menuBackground.png) ; background-repeat: repeat-x;">
	<div style="width:25%;height:98%;float:left;padding-top: 2%;text-align: center;" id="menua">
		<img style="height:50%;" src="${ctx}/common/images/mainControl/menua_on.png"><br/>
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
		<img style="height:50%;" src="${ctx}/common/images/mainControl/menud.png" onclick="toUserManage()"><br/>
		管理
	</div>
	</div>
</body>
</html>