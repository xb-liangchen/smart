<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../common/jsp/include_tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">

<title>${scene.name }</title>

<style type="text/css">
	.mainDiv{/*主DIV*/
		line-height: 3em;
		border-bottom:solid 1px #D9D9D9;
		margin-left:4%;
		width: 96%;
		position:relative;
		overflow: hidden;
	}
	.nameSpan{/*灯名*/
		font-size: 100%;
		font-weight: 600;
	}
	.detailSpan{/*灯的明细信息*/
		position:absolute;
		right:1%;
		font-size:0.7em;
		color:#A0A0A0;
		text-align:right;
	}
	.detailImg{/*灯亮度、温度img*/
		width: 10%;
		height: 20%;
	}
	.onOffImg{/*灯的开关图片*/
		position: absolute;
		right:1%;
		top:25%;
		width:4em;
		height:70%;
	}
</style>

<!-- javascript开始 -->
<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>
<script src="${ctx  }/common/js/mqttws31.js"></script>

<script type="text/javascript">

	function turnOn(id,lightId,eve) {//将场景中的等是否设置为启用状态。
		eve.cancelBubble=true;//阻止冒泡寻找事件，即点击本元素，执行完事件后，并不会继续去执行父元素的事件。
		var imgId = lightId+"_img";
		$("#"+imgId).attr("src","${ctx }/common/images/scene/on.png");

		 $.ajax({
			   type: "POST",
			   url: "${ctx}/turnOnInSceneDetail.htm",
			   data: {"id":id},
			   timeout: 40000,
			   dataType: "json", 
			   success: function(data){ 
				   if(data.errmsg=='ok'){
					  //如果修改成功，那么将显示灯现在的数据
					  //并将灯的开启图片给隐藏
					  $("#"+imgId).css("display","none");
					  $("#"+lightId).css("display","");
					  $("#"+lightId+"_onoff").val(1);//将隐藏域设置为1
					}else{
						alert("数据错误！");
					}
			   },
			   error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert("数据错误！");
				 }
			   });
		
	}
	
	function preEdit(detailId){//准备待编辑场景的信息
		location.href="${ctx}/preEditMultiSceneDetail.htm?detailId="+detailId+"&openid=${scene.openid}&fogDid=${fogDid}";
	}
	
	function showLightList() {//显示,点击本方法跳转后显示的页面应当是显示灯列表。
		location.href="${ctx}/lightListInMultiScene.htm?fogDid=${fogDid}&openid=${scene.openid }&sceneId=${scene.id}";
	}
//------------------------------------------执行情景----------------------------------------------
	var fogDid = "${fogDid}";
	var wsbroker = "api.easylink.io";  //mqtt websocket enabled broker
    var wsport = 1983 // port for above
    var client = new Paho.MQTT.Client(wsbroker, wsport, "v1-web-" + parseInt(Math.random() * 1000000, 12));
    // 基本参数配置
    client.onConnectionLost = onConnectionLost;
    client.connect({onSuccess:onConnect});
    // 连接成功
    
    function onConnect() {
        var subtopic = fogDid+'/out';
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
    
	function doScene() {//执行本情景
		var commonds = traverse();
		var topic = fogDid+'/in';
		
		for(var i=0;i<commonds.length;i++){
			//client.publish(topic, commonds[i]);
			console.log(commonds[i]);
		}
	}
	
	function genericCommond(idArray){//根据灯的id集合生成推送语句
		String.prototype.format = function(){
		    var args = arguments;    
		    return this.replace(/\{(\d+)\}/g,                    
		        function(m,i){    
		            return args[i];    
		        });    
		}
	
		var commond = '{"type":2,"id":"{0}","cmd":5,"sw": {1}, "bright": {2}, "temp": {3}}';
		var commondRGB = '{"type":12,"id":"{0}","cmd":7,"sw": {1}, "bright1": {2}, "temp": {3},"bright2":{4},"rgb":{5}}';
		
		var commondArray = new Array();

		var lightId = "";
		var lightType = "";
		var onoff = "";
		var bright = "";
		var temp = "";
		
		var rgbBright = "";
		var rgb = "";
		var rgbshade = "";
		var switchLight ="";
		var count = 0;//因为onff为0时，不生成语句，为了防止commondArray有空位置，加个count指令。
		for(var i=0;i<idArray.length;i++){
			lightId = idArray[i];
			onoff =$("#"+lightId+"_onoff").val();

			if(onoff == 0 || !onoff) {//如果onoff为0那么就代表灯关闭，不用发送
				continue;
			}
			
			lightType = $("#"+lightId+"_type").val();
			bright =$("#"+lightId+"_bright").val();
			temp =$("#"+lightId+"_temp").val();
	
			if(lightType == 2){//普通灯
				commondArray[count++] = commond.format(lightId,onoff,bright,temp);
			} else {//rgb
				rgbBright = $("#"+lightId+"_rgbBright").val();
				rgbshade = $("#"+lightId+"_rgbshade").val();
				switchLight = $("#"+lightId+"_switchLight").val();
				
				if(!rgbshade || rgbshade == 254) {//如果为空或者为254就代表关闭渐变，让rgb为自己的值
					rgb = $("#"+lightId+"_rgb").val();
				} else { //否则让rgb为rgbshade的值，即打开渐变
					rgb = rgbshade;
				}
				commondArray[count++] = commondRGB.format(lightId,switchLight,bright,temp,rgbBright,rgb);
			}
			
		}
		return commondArray;
	}
	
	function traverse(){//遍历获得灯的id集合
		var idArray =new Array();
		var count = 0;
		$(".detailSpan").each(function(){
			idArray[count] = $(this).attr("id");
			count++;
		});
		return genericCommond(idArray);
	}
</script>
</head>
<body style="margin: 0; padding: 0;">
<c:forEach items="${sceneDetailList }" var="sceneDetail">
		<c:forEach items="${lightList }" var="light">
			<c:if test="${sceneDetail.lightId == light.id }">
				<div class="mainDiv" id="${sceneDetail.id }" onclick="preEdit(${sceneDetail.id})">
		
				<input type="hidden" id="${sceneDetail.lightId }_type" value="${light.lightType }"/>
				<input type="hidden" id="${sceneDetail.lightId }_onoff" value="${sceneDetail.onOff }"/>
				<input type="hidden" id="${sceneDetail.lightId }_bright" value="${sceneDetail.brightness }"/>
				<input type="hidden" id="${sceneDetail.lightId }_temp" value="${sceneDetail.temperature }"/>
				
					<c:if test="${light.lightType != 2 }">
						<input type="hidden" id="${sceneDetail.lightId }_rgb" value="${sceneDetail.rgb }"/>
						<input type="hidden" id="${sceneDetail.lightId }_rgbBright" value="${sceneDetail.colorBrightness }"/>
						<input type="hidden" id="${sceneDetail.lightId }_rgbshade" value="${sceneDetail.rgbshade }"/>
						<input type="hidden" id="${sceneDetail.lightId }_switchLight" value="${sceneDetail.swithLight }"/>
					</c:if>
					<!-- 上面生成隐藏字段 -->
					<span class="nameSpan">${light.name }</span>
					<span id="${sceneDetail.lightId }" class="detailSpan" style="display:${sceneDetail.onOff <= 0?'none':''}">
					<c:if test="${light.lightType == 12 }">RGB${sceneDetail.rgb }&nbsp;/</c:if>
						<img class="detailImg" src="${ctx }/common/images/scene/brightness.png">亮度${sceneDetail.brightness }&nbsp;/
						<img class="detailImg" src="${ctx }/common/images/scene/temperature.png">色温${sceneDetail.temperature }
					</span>
					<img id="${sceneDetail.lightId }_img" class="onOffImg" src="${ctx }/common/images/scene/off.png" style="display:${sceneDetail.onOff <= 0?'':'none'}" onclick="turnOn(${sceneDetail.id },'${sceneDetail.lightId}',event)">
				</div>
			</c:if>
		</c:forEach>
	</c:forEach>

	<div style="width: 80%;margin:auto;line-height: 2.5em;font-weight: 600;margin-top:15%;font-size:125%;text-align: center;">
		<div style="border: solid 1px #2E86EA;border-radius:2em;color:#2E86EA;" onclick="showLightList()">添加设备</div>
		<div style="border: solid 1px #2E86EA;border-radius:2em;color:#FFFFFF;background-color: #2E86EA; margin-top:10%;" onclick="doScene()">执行情景</div>
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