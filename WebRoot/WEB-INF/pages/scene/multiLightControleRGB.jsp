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
//--------------------------------圆弧滑动--------------------------
var min_rgb = 0;//rgb最小值
var max_rgb = 30;//rgb最大值
var old_rgb = 0;//初始rgb
var cur_rgb = 0;//当前rgb，即修改后的rgb

var rgb_min_deg = 0;//rgb圆圈最小度数
var rgb_max_deg = 207;//rgb圆圈最大度数

var min_temp = 0;//最小的色温
var max_temp = 10;//最大的色温
var old_temp = 0;//初始温度
var cur_temp = 0;//当前温度，即修改后的温度

var sw_max_deg = 0;//sw圆圈最小度数
var sw_max_deg = -106;//sw圆圈最大移动度数

var o_x = 0;//页面的中心的x坐标，以判断是在右边滑动还是左边滑动;当然该坐标也就是rgb_img等一系列Img的中心x坐标
var o_y = 0;//rgb_img等一系列Img的中心y坐标。
var onoff_radius = 0;//onoff图片中圆环的半径

//滑动范围在5x5内则做点击处理，s是开始，e是结束
var main_init = {x:5,y:5,sx:0,sy:0,ex:0,ey:0};//初始位置
var rgbDeg = 0;
var swDeg = 0;
$(document).ready(function(){
	o_x = document.documentElement.clientWidth/2;//页面中心x坐标
	o_y = document.getElementById("rgb_img").width/2;//rgb_img等一系列Img的中心y坐标,因图片为正方形，且offsetTop为0;所以为width一半

	//计算rgb_x，rgb_y，rgb_limit_y根据页面的初始状态:ct的底部与圆环缺口的底部基本齐平
	var circle_width = document.getElementById("rgb_img").width*0.9;//rgb圆弧及sw圆弧所形成圆的宽度
	var rgb_ct_width = document.getElementById("rgb_ct").width*0.0758125;//rgb_ct的宽度
	
	old_rgb = cur_rgb = $("#rgb").val();
	old_temp = cur_temp = $("#temp").val();

	document.getElementById("rgb_ct").style.webkitTransform = "rotate("+(old_rgb*rgb_max_deg/max_rgb)+"deg)";
	document.getElementById("sw_ct").style.webkitTransform = "rotate("+(old_temp*sw_max_deg/max_temp)+"deg)";
	//-----------------------------------mainDiv-------------------------------------------
	var rgb_height = circle_width*376/576;//rgb圆高
	var rgb_bottom_y = document.getElementById("rgb_img").width*0.63322368;//底部y坐标
	
	onoff_radius = o_y*0.484375;//小数值为实际图片的(圆宽/图宽)所得
	
	var sw_width = circle_width*0.850695;//sw圆环两点的宽度
	var sw_left_x = o_x-document.getElementById("rgb_img").width*220/640;//sw圆环的最左边的时候的x坐标。
	document.getElementById("onoff_img").addEventListener("touchstart",function(event){
		event.preventDefault();//阻止触摸时浏览器的缩放、滚动条滚动
			main_init.sx = event.targetTouches[0].pageX;
			main_init.sy = event.targetTouches[0].pageY;
			main_init.ex = main_init.sx;
			main_init.ey = main_init.sy;
			
			//判断是否点击的是onoff圆环，如果是的话那么开关onoff
			var x = Math.abs(o_x - main_init.sx);
			var y = Math.abs(o_y - main_init.sy);
			if(x < onoff_radius && y<onoff_radius) {//都小于的时候，才算有效点击
				var initOff = $("#onoff").val();
				var src="";
				if(initOff == 0){
					initOff = 1;
					src = "${ctx}/common/images/scene/lightScene/on.png";
					
				}else{
					initOff = 0;
					src = "${ctx}/common/images/scene/lightScene/off.png";
				}
				$("#onoff").val(initOff);
				$("#onoff_img").attr("src",src);
			}
			
    }, false);
	
	//alert(rgb_height+"--"+rgb_bottom_y);
	document.getElementById("onoff_img").addEventListener("touchmove",function(event){
    	  event.preventDefault();//阻止触摸时浏览器的缩放、滚动条滚动
    	  main_init.ex = event.targetTouches[0].pageX;
    	  main_init.ey = event.targetTouches[0].pageY;

    	  if(main_init.sy <= rgb_bottom_y+5){//说明点击的是上面的rgb_ct
    		  if(main_init.ex <= o_x){//左侧的时候
   	    		 var a = rgb_bottom_y - main_init.ey;
   	    		 rgbDeg = (a/rgb_height)*rgb_max_deg/2;
   	    		 if(rgbDeg >= rgb_max_deg/2) {//限定不能超过一半值
   	    			 rgbDeg = rgb_max_deg/2;
   	    		 }
   	    		 if(rgbDeg <=0) {
   	    			 rgbDeg = 0;
   	    		 }
   	    		document.getElementById("rgb_ct").style.webkitTransform = "rotate("+rgbDeg+"deg)";
   				cur_rgb = Math.round(30*rgbDeg/rgb_max_deg);
    	     } 
  	    	  if(main_init.ex >= o_x){//右侧的时候
  	    		 var a = rgb_bottom_y - main_init.ey;
  	     		 rgbDeg = ((rgb_height-a)/rgb_height)*rgb_max_deg/2+rgb_max_deg/2;
  	     		 if(rgbDeg >= rgb_max_deg) {//限定不能超过最大值
  	     			 rgbDeg = rgb_max_deg;
  	     		 }
  	     		 document.getElementById("rgb_ct").style.webkitTransform = "rotate("+rgbDeg+"deg)";
  	 			 cur_rgb = Math.round(30*rgbDeg/rgb_max_deg);
  	    	  }
    	  }
    		
    	  if(main_init.sy >= rgb_bottom_y+5){//说明移动的是sw_ct
    		  if(main_init.ex >= sw_left_x){    
    	          	 var a = main_init.ex - sw_left_x;
    	 			    swDeg = a/sw_width*(sw_max_deg);
    	 			    if(swDeg <= sw_max_deg){//因为sw_max_deg是负值，所以才是小于的。
    	 			    	swDeg = sw_max_deg;
    	 			    }
    	 				document.getElementById("sw_ct").style.webkitTransform = "rotate("+(swDeg)+"deg)";
    	 				cur_temp = Math.round(10*swDeg/sw_max_deg);
    	         }
    	        if(main_init.ex <= sw_left_x){
    	        	document.getElementById("sw_ct").style.webkitTransform = "rotate(0deg)";
    				cur_temp = 0;
    	        }
    	  }
    }, false);
    
    document.getElementById("onoff_img").addEventListener("touchend",function(event) {
    	event.preventDefault();//阻止触摸时浏览器的缩放、滚动条滚动
    	$("#temp").val(cur_temp);
    	$("#rgb").val(cur_rgb);
    }, false);

 });

function setWhite(num){//调节白光亮度
	for(var i=1;i<=10;i++){
     	if(i<=num){
     		$("#white_"+i).attr("src","${ctx}/common/images/oneLightControl/blue.png");
         }else{
        	$("#white_"+i).attr("src","${ctx}/common/images/oneLightControl/grey.png");
          }
     }
		$("#bright").val(num);//将值更新到隐藏字段中
}

function setColor(num){//调节彩光亮度
	for(var i=1;i<=10;i++){
     	if(i<=num){
     		$("#color_"+i).attr("src","${ctx}/common/images/oneLightControl/blue.png");
         }else{
        	$("#color_"+i).attr("src","${ctx}/common/images/oneLightControl/grey.png");
          }
     }
		$("#bright").val(num);//将值更新到隐藏字段中
}

function turn_shade() {//开启、关闭shade
	var rgb_shade = $("#rgb_shade").val();
	if(rgb_shade == 255) {
		rgb_shade = 254;
		$("#shade_img").attr("src","${ctx}/common/images/scene/lightScene/btn4_.png");
	} else {
		rgb_shade = 255;
		$("#shade_img").attr("src","${ctx}/common/images/scene/lightScene/btn4.png");
	}
	$("#rgb_shade").val(rgb_shade);
}

function switch_light() {//切换灯源
	var switch_light = $("#switch_light").val();
	if(switch_light>=2 && switch_light<4) {//如果在2-4之间
		switch_light++;
	} else if(switch_light >= 4){//如果大于4
		switch_light = 2;
	} else if(!switch_light || switch_light<2) {//如果空置，或者小于2
		switch_light = 4;
	}
	$("#switch_light").val(switch_light);
	for(var i=2;i<=4;i++) {
		if(i==switch_light) {
		$("#switch_"+i).css("display","");
		}else {
			$("#switch_"+i).css("display","none");
		}
	}
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


</script>
</head>
<body style="width:100%;margin:0; padding:0;background-color:#E9E9E9;">
	<div style="position: absolute;top:1%;left:1%;font-size:0.8em;color:#A9A9A9;">
		<span id="switch_2" class="desSwitch" style="display: none;">光源：只开白光</span>
		<span id="switch_3" class="desSwitch" style="display: none;">光源：只开彩光</span>
		<span id="switch_4" class="desSwitch" style="display: none;">光源：白光彩光同时开启</span>
	</div>
<c:choose>
	<c:when test="${detail == null }">
		<form action="${ctx}/addMultiSceneDetail.htm" method="post">
			<input type="hidden" id="onoff" name="onoff" value="${light.onOff }"/>
			<input type="hidden" id="bright" name="bright" value="${light.brightness }"/>
			<input type="hidden" id="temp" name="temp" value="${light.temperature }"/>
			
			<input type="hidden" id="rgb" name="rgb" value="${light.rgb }">
			<input type="hidden" id="switch_light" name="switch_light" value="${light.swithLight }"/>
			<input type="hidden" id="rgb_bright" name="rgb_bright" value="${light.colorBrightness }"/> 
			<input type="hidden" id="rgb_shade" name="rgb_shade" value="${light.rgbshade }"/>
			
			<input type="hidden" name="lightId" value="${light.id }"/>
			<input type="hidden" name="sceneId" value="${sceneId }"/>
			<input type="hidden" name="openid" value="${openid }"/>
			<input type="hidden" name="fogDid" value="${fogDid }"/>
		</form>
	</c:when>
	<c:otherwise>
		<form action="${ctx}/editMultiSceneDetailRGB.htm" method="post">
			<input type="hidden" id="onoff" name="onoff" value="${detail.onOff }"/>
			<input type="hidden" id="bright" name="bright" value="${detail.brightness }"/>
			<input type="hidden" id="temp" name="temp" value="${detail.temperature }"/>
			
			<input type="hidden" id="rgb" name="rgb" value="${detail.rgb }">
			<input type="hidden" id="switch_light" name="switch_light" value="${detail.swithLight }"/>
			<input type="hidden" id="rgb_bright" name="rgb_bright" value="${detail.colorBrightness }"/> 
			<input type="hidden" id="rgb_shade" name="rgb_shade" value="${detail.rgbshade }"/>
			
			
			<input type="hidden" name="detailId" value="${detail.id }"/>
			<input type="hidden" name="openid" value="${openid }"/>
			<input type="hidden" name="fogDid" value="${fogDid }"/>
		</form>
	</c:otherwise>
</c:choose>
<div id="wrapDiv" style="margin:0 auto;padding:0;width:90%;">
<div id="mainDiv" style="width:80%;margin:0 auto;position:relative;">
	<img id="rgb_img" src="${ctx}/common/images/scene/lightScene/rgb.png" style="width: 100%;">
	<img id="rgb_ct" src="${ctx}/common/images/scene/lightScene/rgb_ct.png" style="width:100%;position:absolute;top:0;left:0;">
	<img id="sw_img" src="${ctx}/common/images/scene/lightScene/sw2.png" style="width:100%;position:absolute;top:0;left:0;">
	<img id="sw_ct" src="${ctx}/common/images/scene/lightScene/sw2_ct.png" style="width:100%;position:absolute;top:0;left:0;">
	
	<!-- 占位图片，防止滑动的过程中出现页面膨胀 -->
	<img id="hiddenImg" src="${ctx}/common/images/scene/lightScene/sw2_ct.png" style="width:100%;position:absolute;top:0;left:0;-webkit-transform:rotate(45deg);visibility: hidden;">
	<c:choose>
		<c:when test="${detail == null }">
			<img id="onoff_img" src="${ctx}/common/images/scene/lightScene/${light.onOff == 0?'off':'on' }.png" style="width:100%;position:absolute;top:0;left:0;" onclick="onoff(event)">
			<img id="source_img" src="${ctx}/common/images/scene/lightScene/${light.swithLight == 0?'btn3_':'btn3' }.png" style="width:20%;position:absolute;top:80%;left:-10%;" onclick="switch_light()">
			<img id="shade_img" src="${ctx}/common/images/scene/lightScene/${light.rgbshade == 254?'btn4_':'btn4' }.png" style="width:20%;position:absolute;top:80%;right:-10%;" onclick="turn_shade()">
		</c:when>
		<c:otherwise>
			<img id="onoff_img" src="${ctx}/common/images/scene/lightScene/${detail.onOff == 0?'off':'on' }.png" style="width:100%;position:absolute;top:0;left:0;" onclick="ofoff(event)">
			<img id="source_img" src="${ctx}/common/images/scene/lightScene/${detail.swithLight == 0?'btn3_':'btn3' }.png" style="width:20%;position:absolute;top:80%;left:-10%;" onclick="switch_light()">
			<img id="shade_img" src="${ctx}/common/images/scene/lightScene/${detail.rgbshade == 254?'btn4_':'btn4' }.png" style="width:20%;position:absolute;top:80%;right:-10%;" onclick="turn_shade()">
		</c:otherwise>
	</c:choose>
</div>

<div style="width:100%; margin:5% auto;font-size:80%;color:#A6A6A6;">
	<div style="position:relative;line-height:1em;">
		<span>彩色</span>
		<img src="${ctx}/common/images/light/seta.png" style="width:5%;" onclick="setColor(0)">
		<div style="width:73%;position:absolute;left:16%;bottom:0em;">
			<c:choose>
				<c:when test="${detail == null }">
					<c:forEach begin="1" end="10" step="1" var="iw">
						<img id="color_${iw }" src="${ctx}/common/images/scene/lightScene/${light.colorBrightness>=iw?'blue':'grey' }.png" style="width:8%;float:left;margin:0 1%;" onclick="setColor(${iw})">
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach begin="1" end="10" step="1" var="iw">
						<img id="color_${iw }" src="${ctx}/common/images/scene/lightScene/${detail.colorBrightness>=iw?'blue':'grey' }.png" style="width:8%;float:left;margin:0 1%;" onclick="setColor(${iw})">
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<img src="${ctx}/common/images/light/setb.png" style="width:8%;float:right;" onclick="setColor(10)">
	</div>
	
	<div style="position:relative;line-height:1em;margin-top:5%;">
		<span>白色</span>
		<img src="${ctx}/common/images/light/seta.png" style="width:5%;" onclick="setWhite(0)">
		<div style="width:73%;position:absolute;left:16%;bottom:0em;">
			<c:choose>
				<c:when test="${detail == null }">
					<c:forEach begin="1" end="10" step="1" var="iw">
						<img id="white_${iw }" src="${ctx}/common/images/scene/lightScene/${light.brightness>=iw?'blue':'grey' }.png" style="width:8%;float:left;margin:0 1%;" onclick="setWhite(${iw})">
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach begin="1" end="10" step="1" var="iw">
						<img id="white_${iw }" src="${ctx}/common/images/scene/lightScene/${detail.brightness>=iw?'blue':'grey' }.png" style="width:8%;float:left;margin:0 1%;" onclick="setWhite(${iw})">
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<img src="${ctx}/common/images/light/setb.png" style="width:8%;float:right;" onclick="setWhite(10)">
	</div>
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