<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>设备</title>
<style type="text/css">
li {list-style: outside none none;}
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
.round{width:16px;height:16px;display: inline-block;font-size:20px;line-heigth:16px;text-align:center;color:#f00;text-decoration:none}
</style>
<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>
<script src="${ctx  }/common/js/mqttws31.js"></script>
<script src="${ctx  }/common/js/iscroll.js"></script>
<script type="text/javascript">
var min_temperature = 0;//最小的色温
var max_temperature = 10;//最大的色温
var min_degree = 0;//最小的圆圈度数
var max_degree = 180;//最大的圆圈度数
var round_x = 0;//圆的圆心的在当前可见页面中x坐标
var round_y = 0;//圆的圆心的在当前可见页面中y坐标
var round_r = 0;//圆的半径
var o_y = 0;
var old_temperature=0;
var cur_temperature =0;//当前温度

window.onload = function(){
	var width = document.documentElement.clientWidth*0.8;
	var width2 = width/461*340;
	var width3 = width/461*102;
	var width4 = width/461*72;
	$("#img1").css("width",width+"px");
	$("#img2").css("width",width2+"px");
	$("#img3").css("width",width4+"px");

	$("#imga").css("width",width3+"px");
	$("#imgb").css("width",width3+"px");

	old_temperature = cur_temperature =${sysLight.temperature};

	round_x = document.documentElement.clientWidth/2+document.getElementById("img3").offsetLeft;
	o_y =  document.getElementById("img1").offsetHeight + document.getElementById("img1").offsetTop;
	round_y = document.getElementById("img3").offsetHeight/2 - document.getElementById("img3").offsetLeft;
	round_r = round_x+document.getElementById("img3").offsetLeft;

	var device_id = "${fogDid}";
	var light_id = "${sysLight.id}";
	 if ( device_id !== null ){
        ez_connect(device_id,light_id);
  	  }


	$("#menua").click(function(){
		location.href="${ctx}/lightList.htm?fogDid=${fogDid}&openid=${openid }";
	});
	
	$("#addScene").click(function(){
		document.getElementById("openWinDiv").style.visibility = "visible";
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
			params +="&fogDid=${fogDid}";
			params +="&brightness="+$("#brightness").val();
			params +="&temperature="+cur_temperature;
			params +="&swithLight="+$("#onoff").val();
			 $.ajax({
				   type: "POST",
				   url: "${ctx}/addSceneForOneLight.htm"+params,
				   data:{"name":sceneName},
				   timeout: 40000,
				   dataType: "json", 
				   success: function(data){ 
					   if(data.errmsg=='ok'){
						    var html = "<div id='oneScene_"+data.id+"' class='sceneDelete'>";
						    html += "<div id='"+data.id+"' class='sceneN' onclick='deleteScene(this);' ><img src='${ctx}/common/images/sceneControl/delete.png'/></div>";
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
	
	$("#editScene").click(function(){
		if($(".sceneN").length==0){
			$(".sceneY").removeClass("sceneY").addClass("sceneN");
		}else{
			$(".sceneN").removeClass("sceneN").addClass("sceneY");
		}
	});

	$("#imgb").click(function(){
		document.getElementById("openTimeDiv").style.visibility = "visible";
	});
	
}



function ez_connect(device_id,id) {
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
	function onoff(){
		var val = $("#onoff").val();
		var src = "";
		if(val==1){
			val = 0;
			src = '${ctx}/common/images/tgtsCon/off.png';
		}else{
			val = 1;
			src = '${ctx}/common/images/tgtsCon/on.png';
		}
		 var topic = device_id+'/in';
         var commond = '{"type":2,"id":"'+id+'","cmd":1,"value": '+val+'}'; 
         client.publish(topic, commond);
         client.onMessageArrived=function(message){ 
         	var payload =  eval('('+message.payloadString + ')'); 
         	if(payload.ok==1){
				var light_id = payload.id;
				var sw = payload.sw;
				var bright = payload.bright;
				var temp = payload.temp;
             	updateLightStatus(light_id,sw,bright,temp);
         		$("#onoff").val(val); 
             	$("#img2").attr("src",src);
             }
           }
	}
    document.getElementById("img2").addEventListener("click",onoff,false);

	function whiteBright(){
		var imgid = $(this).attr("id").split("_");
		var val = imgid[1];
		var topic = device_id+'/in';
		var commond = '{"type":2,"id":"'+id+'","cmd":2,"value": '+val+'}'; 
	    client.publish(topic, commond);
	    client.onMessageArrived=function(message){ 
         	var payload =  eval('('+message.payloadString + ')'); 
         	if(payload.ok==1){
				var light_id = payload.id;
				var sw = payload.sw;
				var bright = payload.bright;
				var temp = payload.temp;
             	updateLightStatus(light_id,sw,bright,temp);
             	$("#brightness").val(val);
             	for(var i=1;i<=10;i++){
                 	if(i<=val){
                 		$("#white_"+i).attr("src","${ctx}/common/images/oneLightControl/blue.png");
                     }else{
                    	$("#white_"+i).attr("src","${ctx}/common/images/oneLightControl/grey.png");
                      }
                 }
             }
           }
	}
    document.getElementById("white_1").addEventListener("click",whiteBright,false);
    document.getElementById("white_2").addEventListener("click",whiteBright,false);
    document.getElementById("white_3").addEventListener("click",whiteBright,false);
    document.getElementById("white_4").addEventListener("click",whiteBright,false);
    document.getElementById("white_5").addEventListener("click",whiteBright,false);
    document.getElementById("white_6").addEventListener("click",whiteBright,false);
    document.getElementById("white_7").addEventListener("click",whiteBright,false);
    document.getElementById("white_8").addEventListener("click",whiteBright,false);
    document.getElementById("white_9").addEventListener("click",whiteBright,false);
    document.getElementById("white_10").addEventListener("click",whiteBright,false);


	function changeTemp(cur_temperature){
		var val = cur_temperature;
		 var topic = device_id+'/in';
         var commond = '{"type":2,"id":"'+id+'","cmd":3,"value": '+val+'}'; 
         client.publish(topic, commond);
         client.onMessageArrived=function(message){ 
         	var payload =  eval('('+message.payloadString + ')'); 
         	if(payload.ok==1){
				var light_id = payload.id;
				var sw = payload.sw;
				var bright = payload.bright;
				var temp = payload.temp;
             	updateLightStatus(light_id,sw,bright,temp);
             }
           }
	}
    var init = {sx:0,sy:0,ex:0,ey:0};
    document.getElementById("img3").addEventListener("touchstart",function(event){
        init.sx = event.targetTouches[0].pageX;
        init.sy = event.targetTouches[0].pageY;
        init.ex = init.sx;
        init.ey = init.sy;
    }, false);
    document.getElementById("img3").addEventListener("touchmove",function(event){
    	  event.preventDefault();//阻止触摸时浏览器的缩放、滚动条滚动
          init.ex = event.targetTouches[0].pageX;
          init.ey = event.targetTouches[0].pageY;
          var degree =0;
          if(init.ex<=round_x){//左半弧
              if(init.ey >=o_y){
  			    document.getElementById("img3").style.webkitTransformOrigin = round_x+"px "+round_y+"px";
  				document.getElementById("img3").style.webkitTransform = "rotate(0deg)";
  				cur_temperature = 0;
               }else{
            	 var a = round_x - init.ex;	
   			    degree = 180*Math.asin(a/round_r)/Math.PI;
   			    document.getElementById("img3").style.webkitTransformOrigin = round_x+"px "+round_y+"px";
   				document.getElementById("img3").style.webkitTransform = "rotate("+(90-degree-min_degree)+"deg)";
   				cur_temperature = parseInt((90-degree-min_degree)/(max_degree-min_degree)*(max_temperature-min_temperature) )+0;
              }
        	  
           }else{
        	   if(init.ey >=o_y){
     			    document.getElementById("img3").style.webkitTransformOrigin = round_x+"px "+round_y+"px";
     				document.getElementById("img3").style.webkitTransform = "rotate(180deg)";
     				cur_temperature = 10;
                  }else{
		        	   var a = init.ex - round_x ;	
					    degree = 180*Math.asin(a/round_r)/Math.PI;
					    document.getElementById("img3").style.webkitTransformOrigin = round_x+"px "+round_y+"px";
						document.getElementById("img3").style.webkitTransform = "rotate("+(90+degree+min_degree)+"deg)";
						cur_temperature = parseInt((90+degree+min_degree)/(max_degree-min_degree)*(max_temperature-min_temperature) )+0;
                  }
           }
          
    }, false);
    document.getElementById("img3").addEventListener("touchend",function(event) {
     //   alert(init.sx+"||"+init.sy+"||"+init.ex+"||"+init.ey+"||"+o_y+"||"+round_x+"||"+round_r);
    //alert(cur_temperature);
        var changeX = init.sx - init.ex;
        var changeY = init.sy - init.ey;
		if(old_temperature!=cur_temperature){
			if(!isNaN(cur_temperature)){
				changeTemp(cur_temperature);
			}
		}
	//	$(this).css({"-webkit-transform": "rotate(180deg)","-webkit-transform-origin":"161px 43px"});

    }, false);
}

function updateLightStatus(light_id,sw,bright,temp){
	$.ajax({
		type: "POST",
	    url: "${ctx}/updateLightStatus.htm",
	    data: {"id":light_id,"swithLight":sw,"brightness":bright,"temperature":temp},
	    timeout: 40000,
	    dataType: "json", 
	    success: function(data){ 
		   if(data.errmsg=='ok'){
			   
			}else{
				
			}
	    }
		});
}

function deleteScene(obj){
	var id =$(obj).attr("id");
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
}

</script>
</head>
<body style=" margin:0; padding:0;">
<input type="hidden"  id="onoff"  value="${sysLight. swithLight}"/>
<input type="hidden"  id="brightness"  value="${sysLight. brightness}"/>
<div style="width:80%;  margin:0 auto; position:relative;margin-top: 0.5em;" >
<c:if test="${sysLight.isOnline ==1 }">
	 <img src="${ctx}/common/images/tgtsCon/btn1.png" id="imga" style="position: absolute;top:0;left:0;z-index: 10"/> 
</c:if>
<c:if test="${sysLight.isOnline ==0 }">
	 <img src="${ctx}/common/images/tgtsCon/btn1_.png" id="imga" style="position: absolute;top:0;left:0;z-index: 10"/> 
</c:if>
	 <img src="${ctx}/common/images/tgtsCon/btn2.png" id="imgb" style="position: absolute;top:0;right:0;z-index: 10"/> 
</div>

<div style="width:80%;  margin:0 auto; position:relative;padding-top: 3em;" id="round_1">
	 <img src="${ctx}/common/images/tgtsCon/sw.png" id="img1" /> 
	 <img src="${ctx}/common/images/tgtsCon/on.png" id="img2" style="position: absolute;top:45%;left:15%;z-index: 10"/> 
	 <img src="${ctx}/common/images/tgtsCon/ct1.png" id="img3"  style="position: absolute;bottom:0;left:-1.2em;z-index: 10"/> 
</div>

<div  style="width:90%;  margin:0 auto;padding-top: 5em;">
	
	<div style="padding-top: 3em;">
		<span>白色</span>
			<img id="img5" src="${ctx}/common/images/light/seta.png" style="width:5%;">
			<c:forEach begin="1" step="1" end="10" var="iw">
				<c:if test="${iw<= sysLight.brightness }">
					<img id="white_${iw }" src="${ctx}/common/images/oneLightControl/blue.png" style="width:6%;z-index: 11;" >
				</c:if>
				<c:if test="${iw> sysLight.brightness }">
					<img id="white_${iw }" src="${ctx}/common/images/oneLightControl/grey.png" style="width:6%;z-index: 11;" >
				</c:if>
			</c:forEach>
			
			<img id="img6" src="${ctx}/common/images/light/setb.png" style="width:8%;">
	</div>
</div>

<div style="width:100%;padding-top: 1em;">
	<c:forEach items="${sysScenes }" var="oneScene">
		<div id="oneScene_${oneScene.id }" class="sceneDelete" >
			<div id="${oneScene.id }" onclick='deleteScene(this);' class="sceneN"><img src="${ctx }/common/images/sceneControl/delete.png"/></div>
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
	
	<div id="openTimeDiv" style="position: fixed;top: 0;z-index: 999;width: 100%;height: 100%;background: rgba(8, 8, 8, 0.7); visibility: hidden;">
		<div style="position: relative;top:45%;height: 55%;text-align: center;background: #ffffff no-repeat;">
				 <div style="line-height: 3em;border-bottom: 1px solid #e3e3e3;height:3em;"> 
				 	   <div id="cancleTimediv"  style="float: left;width: 49%;display: inline;"><div style="width: 30%;cursor: pointer;text-align: left;padding-left: 10%">取消</div></div> 
			           <div id="sureTimediv"   style="display: inline;width: 50%;float: right;"><div style="float: right;width: 30%;cursor: pointer;text-align: right;padding-right: 10%">确定</div></div>
				 </div>
				 <div id="dataMark" style="width:100%;border-top: 1px solid #e3e3e3;border-bottom: 1px solid #e3e3e3;height: 3em;position: absolute;top:9em;"></div>
				 <div style="line-height: 3em;height:15em;display: block;">
				 	<div id="hourwrapper" style="float:right;overflow: hidden;position: absolute;left:20%;top:3em;bottom:2.5em;width:30%;">
				 		<ul >
				 			<li>&nbsp;</li>
				 			<li>&nbsp;</li>
				 			<c:forEach begin="1" step="1" end="24" var="hour">
				 				<c:if test="${hour<10 }"><li>0${hour }</li></c:if>
				 				<c:if test="${hour>=10 }"><li>${hour } </li></c:if>
				 			</c:forEach>
				 			<li>&nbsp;</li>
				 			<li>&nbsp;</li>
				 		</ul>
				 	</div>
				 	<div id="minutewrapper" style="float:left;overflow: hidden;position: absolute;right:20%;top:3em;bottom:2.5em;width:30%;">
				 		<ul >
				 			<li>&nbsp;</li>
				 			<li>&nbsp;</li>
				 			<c:forEach begin="1" step="1" end="60" var="min">
				 				<c:if test="${min<10 }"><li>0${min }</li></c:if>
				 				<c:if test="${min>=10 }"><li>${min } </li></c:if>
				 			</c:forEach>
				 			<li>&nbsp;</li>
				 			<li>&nbsp;</li>
				 		</ul>
				 	</div>
			 </div>
			 
		</div>
	</div>

<script>
var indexH = 2;
var indexI = 2;
var HourScroll = new iScroll("hourwrapper",{snap:"li",vScrollbar:false,
    onScrollEnd:function () {
        indexH = Math.round((this.y/48)*(-1))+2;
        HourScroll.refresh();
}});
var MinuteScroll = new iScroll("minutewrapper",{snap:"li",vScrollbar:false,
    onScrollEnd:function () {
        indexI = Math.round((this.y/48)*(-1))+2;
        HourScroll.refresh();
}});
$("#cancleTimediv").click(function(){
	document.getElementById("openTimeDiv").style.visibility = "hidden";
});
$("#sureTimediv").click(function(){
	document.getElementById("openTimeDiv").style.visibility = "hidden";
	var nowdate = new Date();
	var hour = $("#hourwrapper ul li:eq("+indexH+")").html();
	var min = $("#minutewrapper ul li:eq("+indexI+")").html();
	var brightness = $("#brightness").val();
	var lightId = "${sysLight.id}";
	var openid = "${openid }";
	var runTime =  nowdate.getFullYear()+"-"+(nowdate.getMonth()<9?"0":"")+(nowdate.getMonth()+1)+"-"+(nowdate.getDate()<10?"0":"")+nowdate.getDate()+" "+hour +":"+min+":00";
	$.ajax({
		type: "POST",
	    url: "${ctx}/setTimeTask.htm",
	    data: {"runTime":runTime,"brightness":brightness,"temperature":cur_temperature,"lightId":lightId,"openid":openid},
	    timeout: 40000,
	    dataType: "json", 
	    success: function(data){ 
		   if(data.errmsg=='ok'){
				location.href="${ctx}/TimeTaskList.htm?lightId=${sysLight.id}&openid=${openid }";
			}else{
				
			}
	    }
	});
});
</script>
	
</body>
</html>