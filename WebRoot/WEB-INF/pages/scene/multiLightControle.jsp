<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">

<title>${light.name }</title>
<style type="text/css">
	html{
		height:100%;
	}
	body{
		height:100%;
	}
</style>
<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>

<script type="text/javascript">

function onoff(){//开关
	var initOff = $("#onoff").val();
	var src="";
	if(initOff == 0){
		initOff = 1;
		src = "${ctx}/common/images/tgtsCon/on.png";
		
	}else{
		initOff = 0;
		src = "${ctx}/common/images/tgtsCon/off.png";
	}
	$("#onoff").val(initOff);
	$("#img2").attr("src",src);
 }
 
function setWhite(num){
	for(var i=1;i<=10;i++){
     	if(i<=num){
     		$("#white_"+i).attr("src","${ctx}/common/images/oneLightControl/blue.png");
         }else{
        	$("#white_"+i).attr("src","${ctx}/common/images/oneLightControl/grey.png");
          }
     }
		$("#bright").val(num);//将值更新到隐藏字段中
}

function saveSceneDetail(){
	//获取隐藏域的字段。
	var onoff = $("#onoff").val();
	var brightness = $("#bright").val();
	var temp = $("#temp").val();
	alert("正在保存......");
	$("form").submit();
}

function canelSave(){//取消
	history.go(-1);
}
//--------------------------------设置温度--------------------------
var min_temp = 0;//最小的色温
var max_temp = 10;//最大的色温
var min_deg = 0;//最小的圆圈度数
var max_deg = 180;//最大的圆圈度数
var round_x = 0;//圆的圆心的x坐标
var round_y = 0;//圆的圆心的y坐标

var sw_width = 0;//圆宽
var sw_left_x = 0;//sw圆环左脚在页面中的
var old_temp = 0;//老温度
var cur_temp = 0;//当前温度
var limit_y = 0;//sw圆环底部在页面中的y坐标
//滑动范围在5x5内则做点击处理，s是开始，e是结束
var init = {x:5,y:5,sx:0,sy:0,ex:0,ey:0};//初始位置
var tempDeg =0;//
$(document).ready(function(){
	//img3围绕圆心来旋转，所谓旋转以目前的CSS标准是img3的左上角围绕圆心来旋转
	sw_width = document.getElementById("img1").width;
	sw_left_x = document.documentElement.clientWidth*0.14;

	round_x = sw_width/2+document.getElementById("img3").width*0.3;//加上img3左移的2.5%;
	round_y = document.getElementById("img3").width*0.45;//这个值没有什么特殊的,网页试出来的较为合理的值
	limit_y = document.getElementById("img1").height+document.getElementById("wrapDiv").offsetTop;
	
//alert(round_x+"--"+round_y+"--"+limit_y);//4s手机的值为121.9--10.35--128-

//初始的时候
	old_temp = cur_temp =$("#temp").val();//点进页面时，灯的色温
	document.getElementById("img3").style.webkitTransformOrigin = round_x+"px "+round_y+"px";
	document.getElementById("img3").style.webkitTransform = "rotate("+(old_temp*max_deg/max_temp)+"deg)";//初始旋转的度数，因为只有10个值，所以180分成10份为18

	document.getElementById("img3").addEventListener("touchstart",function(event){
		event.preventDefault();//阻止触摸时浏览器的缩放、滚动条滚动
		init.sx = event.targetTouches[0].pageX;
	    init.sy = event.targetTouches[0].pageY;
	    init.ex = init.sx;
	    init.ey = init.sy;
    }, false);
	
    document.getElementById("img3").addEventListener("touchmove",function(event){
  	  event.preventDefault();//阻止触摸时浏览器的缩放、滚动条滚动
        init.ex = event.targetTouches[0].pageX;
        init.ey = event.targetTouches[0].pageY;
        if(init.ex >= sw_left_x && init.ey <= limit_y*1.5){
  				if(init.ex >= sw_left_x*1.8 && init.ey >= limit_y*1.3) {
  					tempDeg = max_deg;
  				} else {
  					var a = init.ex - sw_left_x;
  	         	 	tempDeg = a/sw_width*(max_deg);
  				    if(tempDeg >= max_deg-3){//因是负值，所以才是小于的。
  				    	tempDeg = max_deg;
  				    }
  				}
			    document.getElementById("img3").style.webkitTransformOrigin = round_x+"px "+round_y+"px";
				document.getElementById("img3").style.webkitTransform = "rotate("+(tempDeg)+"deg)";
				cur_temp = Math.round(10*tempDeg/max_deg);
        }
       	if(init.ex <= sw_left_x){
       		document.getElementById("img3").style.webkitTransformOrigin = round_x+"px "+round_y+"px";
       		document.getElementById("img3").style.webkitTransform = "rotate(0deg)";
			cur_temp = 0;
        }	
  }, false);
    
  document.getElementById("img3").addEventListener("touchend",function(event) {
	$("#temp").val(cur_temp);
  }, false);
});

</script>
</head>
<body style="width:100%;margin:0; padding:0;">
<c:choose>
	<c:when test="${detail == null }">
		<form action="${ctx}/addMultiSceneDetail.htm" method="post">
			<input type="hidden" id="onoff" name="onoff" value="${light.onOff }"/>
			<input type="hidden" id="bright" name="bright" value="${light.brightness }"/>
			<input type="hidden" id="temp" name="temp" value="${light.temperature }"/>
			
			<input type="hidden" name="lightId" value="${light.id }"/>
			<input type="hidden" name="sceneId" value="${sceneId }"/>
			<input type="hidden" name="openid" value="${openid }"/>
			<input type="hidden" name="fogDid" value="${fogDid }"/>
		</form>
	</c:when>
	<c:otherwise>
		<form action="${ctx}/editMultiSceneDetail.htm" method="post">
			<input type="hidden" id="onoff" name="onoff" value="${detail.onOff }"/>
			<input type="hidden" id="bright" name="bright" value="${detail.brightness }"/>
			<input type="hidden" id="temp" name="temp" value="${detail.temperature }"/>
			
			<input type="hidden" name="detailId" value="${detail.id }"/>
			<input type="hidden" name="openid" value="${openid }"/>
			<input type="hidden" name="fogDid" value="${fogDid }"/>
		</form>
	</c:otherwise>
</c:choose>
<div id="wrapDiv" style="margin: 2.5% auto;padding:0;width:90%;height:40%;">
<div id="mainDiv" style="width:80%;margin:0 auto;position: relative;">
	<img id="img1" src="${ctx}/common/images/tgtsCon/sw.png" style="width: 100%;">
	<c:choose>
		<c:when test="${detail == null }">
			<img id="img2" src="${ctx}/common/images/tgtsCon/${light.onOff == 0?'off':'on' }.png" style="width:70%;left:15%;top:30%;position: absolute;z-index: 10" onclick="onoff()"/> 
		</c:when>
		<c:otherwise>
			<img id="img2" src="${ctx}/common/images/tgtsCon/${sceneDetail.onOff == 0?'off':'on' }.png" style="width:70%;left:15%;top:30%;position: absolute;z-index: 10" onclick="onoff()"/> 
		</c:otherwise>
	</c:choose> 
	
	<img id="img3" src="${ctx}/common/images/scene/lightScene/ct11.png" style="margin:0;width:10%;position: absolute;bottom:-2.5%;left:-2.5%;z-index: 13"/>
</div>

<div style="width:100%;margin-top:35%;padding:0;position:relative;height:10%;">
	<img id="img5" src="${ctx}/common/images/light/seta.png" style="width:4.5%;position:absolute;bottom:0;left:0;" onclick="setWhite(0)">
	<div style="width:84%;margin:0 auto;height:100%;position:absolute;left:8%;top:50%;">
	<c:choose>
		<c:when test="${detail == null }">
			<c:forEach begin="1" end="10" step="1" var="iw">
			<img id="white_${iw }" src="${ctx}/common/images/oneLightControl/${light.brightness>=iw?'blue':'grey' }.png" style="width:8%;float:left;margin:0 1%;" onclick="setWhite(${iw})">
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:forEach begin="1" end="10" step="1" var="iw">
				<img id="white_${iw }" src="${ctx}/common/images/oneLightControl/${detail.brightness>=iw?'blue':'grey' }.png" style="width:8%;float:left;margin:0 1%;" onclick="setWhite(${iw})">
			</c:forEach>
		</c:otherwise>
	</c:choose>
	</div>
	<img id="img6" src="${ctx}/common/images/light/setb.png" style="width:7%;position:absolute;bottom:0;right:0;" onclick="setWhite(10)">
</div>

<div style="width: 100%;margin:auto;line-height: 2em;font-weight: 600;margin-top:10%;font-size:125%;text-align: center;">
	<div style="border: solid 1px #2E86EA;border-radius:2em;color:#FFFFFF;background-color: #2E86EA;" onclick="saveSceneDetail()">${detail == null?'确定添加':'确定' }</div>
	<div style="border: solid 1px #2E86EA;border-radius:2em;color:#2E86EA; margin-top:5%;" onclick="canelSave()">取消</div>
</div>

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