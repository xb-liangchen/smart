<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>设备</title>
<style type="text/css">
.deviceOne{
	border-bottom: 1px solid #A9A9A9;
	height: 5em;
	width: 100%;
	overflow: hidden;
	margin-top: 0.7em;
}
</style>
<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>
<script src="${ctx  }/common/js/mqttws31.js"></script>
<script type="text/javascript">
	window.onload = function(){
		$("#menuc").click(function(){
			location.href="${ctx}/sceneControle.htm?did=${did}&openid=${openid }";
		});

		$(".deviceOne").each(function(){
			var idAndType = $(this).attr("id").split("_"); 
			if(idAndType[2] ==1){
				$(this)[0].addEventListener("click",oneLightControl,false);
				function oneLightControl(){
					location.href="${ctx}/oneLightControle.htm?fogDid=${fogDid}&openid=${openid }&lightId="+idAndType[0]+"&lightType="+idAndType[1];	
				}
			}
			
		});

		var device_id = "${fogDid}";
		 if ( device_id !== null ){
		        ez_connect(device_id);
		  }
		
	}

    function ez_connect(device_id) {
        var wsbroker = "api.easylink.io";  //mqtt websocket enabled broker
        var wsport = 1983 // port for above
        var client = new Paho.MQTT.Client(wsbroker, wsport, "v1-web-" + parseInt(Math.random() * 1000000, 12));
        // 基本参数配置
        client.onConnectionLost = onConnectionLost;
        client.connect({onSuccess:onConnect});
        // 连接成功
        function onConnect() {
            var subtopic = device_id+'/out';
            client.publish = function(topic, commond) {
                message = new Paho.MQTT.Message(commond);
                message.destinationName = topic;
                client.send(message);
            }
            client.subscribe(subtopic, {qos: 0});
        }
        // 连接丢失
        function onConnectionLost(responseObject) {
            if (responseObject.errorCode !== 0)
                console.log("onConnectionLost:"+responseObject.errorMessage);
        }

     function addDevice() {
            var topic = device_id+'/in';
            var commond = '{"type":0}';
            client.publish(topic, commond);
            client.onMessageArrived=function(message){ 
            	var payload =  eval('('+message.payloadString + ')'); 
				if(payload.ok==1){
					var device = payload.device;
					var id = payload.id;
					$.ajax({
						  type: "POST",
						   url: "${ctx}/addLight.htm",
						   data: {"lightId":id,"lightType":device,"fogDid":"${fogDid}","openid":"${openid}"},
						   timeout: 40000,
						   dataType: "json", 
						   success: function(data){ 
							   if(data.errmsg=='ok'){
								   location.href="${ctx}/lightList.htm?fogDid=${fogDid}&openid=${openid }";
								}else{
									alert("请重新配对");
								}
						   },
						   error:function (XMLHttpRequest, textStatus, errorThrown) {
							   
							 }
					});
				}
              }
        }
        document.querySelector('#add').addEventListener('click', addDevice);

    }
	
</script>
</head>
<body style=" margin:0; padding:0;">
<c:forEach var="ll" items="${lightList }">

	<div class="deviceOne" id="${ll.id }_${ll.lightType }_${ll.isOnline}">
			<c:choose>
				<c:when test="${1 eq ll.isOnline }">
					<div style="float:right;vertical-align: middle;margin-right: 1em;line-height: 5em;">在线</div>
					<div style="float: right;vertical-align: middle;margin-right: 3px;line-height: 5em;">
						 <img style="padding-top: 1.6em;" src="${ctx }/common/images/mainControl/online.png" /> 
					 </div>
				</c:when>
				<c:otherwise>
					<div style="float:right;vertical-align: middle;margin-right: 1em;line-height: 5em;">离线</div>
					<div style="float: right;vertical-align: middle;margin-right: 3px;line-height: 5em;">
						<img  style="padding-top: 1.6em;" src="${ctx }/common/images/mainControl/offline.png" />
					</div>
				</c:otherwise>
			</c:choose>
		
		<div style="float:left;padding-left: 1em;">
			<span id="device_name_${sysDevice.id }" style="font-size: 1.5em;position: relative;"> ${ll.name }</span><br/>
			<span style="font-size: 1.2em;position: relative;color: #C5CAD0;"> ID: ${ll.id }</span><br/>
			<span style="font-size: 1.2em;position: relative;color: #C5CAD0;">
				<c:choose>
					<c:when test="${1 eq ll.lightType }">
						 类型: RGB+调光调色LED灯 
					</c:when>
					<c:otherwise>
						 类型: 调光调色温LED灯 
					</c:otherwise>
				</c:choose>
			</span>
		</div>
	</div>	
</c:forEach>

<div style="width: 80%;margin:auto;line-height: 2.5em;font-weight: 600;margin-top:15%;font-size:125%;text-align: center;">
	<div id="add" style="border: solid 1px #2E86EA;border-radius:2em;color:#FFFFFF;background-color: #2E86EA; margin-top:10%;"">添加设备</div>
</div>

<div style="height:5em;position: fixed;bottom: 0px;width:100%;background-image: url(${ctx}/common/images/mainControl/menuBackground.png) ; background-repeat: repeat-x;">
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