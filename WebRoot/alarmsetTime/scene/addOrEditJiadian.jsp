<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../common/jsp/include_tags.jsp" %>
<html>
<head>
<title>闹钟设置</title>
<meta charset="utf-8">
<meta name="viewport"
content="target-densitydpi=device-dpi, width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
 
<link rel="stylesheet" href="../css/ui-box.css">
<link rel="stylesheet" href="../css/ui-base.css">
<link rel="stylesheet" href="../css/public.css">

 <script src="../js/jquery-1.10.2.min.js"></script> 
 
 

 
 
 

</head>
<body   class="um-vp  content" style="position:relative;" ontouchstart>

 

 

<div class="timePop" id="timePop">  
   
    <div class="timeAll u-b-radius">
		<div class="jiange">&nbsp;</div>
        <div class="timeText">自定时间</div>	
    
        <div class="timeDiv">
            <div class="timexiaoshi float1" id="timexiaoshi">
            	<div class="timexiaoshiSub" id="timexiaoshiSub">
                        <div id="hourHeight_0">00</div>
                        <div id="">01</div>
                        <div id="">02</div>
                        <div id="">03</div>
                        <div id="">04</div>
                        <div id="">05</div>
                        <div id="">06</div>
                        <div id="">07</div>
                        <div id="">08</div>
                        <div id="">09</div>
                        <div id="">10</div>
                        <div id="">11</div>
                        <div id="">12</div>
                        <div id="">13</div>
                        <div id="">14</div>
                        <div id="">15</div>
                        <div id="">16</div>
                        <div id="">17</div>
                        <div id="">18</div>
                        <div id="">19</div>
                        <div id="">20</div>
                        <div id="">21</div>
                        <div id="">22</div>
                        <div id="">23</div>
                        
                        
                        <div id="">00</div>
                        <div id="">01</div>
                        <div id="">02</div>
                        <div id="">03</div>
                        <div id="">04</div>
                        <div id="">05</div>
                        <div id="">06</div>
                        <div id="">07</div>
                        <div id="">08</div>
                        <div id="">09</div>
                        <div id="">10</div>
                        <div id="">11</div>
                        <div id="">12</div>
                        <div id="">13</div>
                        <div id="">14</div>
                        <div id="">15</div>
                        <div id="">16</div>
                        <div id="">17</div>
                        <div id="">18</div>
                        <div id="">19</div>
                        <div id="">20</div>
                        <div id="">21</div>
                        <div id="">22</div>
                        <div id="">23</div>
                        
                    
                        <div id="">00</div>
                        <div id="">01</div>
                        <div id="">02</div>
                        <div id="">03</div>
                        <div id="">04</div>
                        <div id="">05</div>
                        <div id="">06</div>
                        <div id="">07</div>
                        <div id="">08</div>
                        <div id="">09</div>
                        <div id="">10</div>
                        <div id="">11</div>
                        <div id="">12</div>
                        <div id="">13</div>
                        <div id="">14</div>
                        <div id="">15</div>
                        <div id="">16</div>
                        <div id="">17</div>
                        <div id="">18</div>
                        <div id="">19</div>
                        <div id="">20</div>
                        <div id="">21</div>
                        <div id="">22</div>
                        <div id="">23</div>

                </div>               
            </div> 
            <!-- 
            <div class="timeJianGe float1 textAlign2" >时:</div>
             -->
            <div class="timefenzhong float1" id="timefenzhong"><!--åédiv-->
            	<div class="timefenzhongSub" id="timefenzhongSub">
                
                
                </div>
            </div>      
        </div>
        <!-- 
        <div style="height:2em;">
            <div class="timeConform1 color9 textAlign2 float1" onClick="hiddenTimePop()">取消</div>	
            <div class="timeConform2 color9 textAlign2 float1" onClick="showTimeText()">确定</div>	
        </div>
         -->
    </div>    
</div>
<div class="alarmsetBottomClass" id="alarmsetBottomClass">
	<div style="float: left;width: 8em;"><img style="height:2em;margin-top:1.5em;margin-left:25px" src="../images/2x/icon_back.png"></div>
	<div style="float: right;height: 3em;text-align:right;margin-right:30px;margin-top:0.5em;font-size:2em;">保存</div>
</div>
<style>
.alarmsetBottomClass{position:fixed; bottom:0px; left:0px; float:left; width:100%; height:5em; background-color:#2F4F4F; color:#fff;}
.timePop{position:absolute; left:0px; top:0px; background:url(../images/pop_bg.png); z-index:9999; width:100%; height:100%;}
.timeAll{width:100%; color:#9a9a9a; height:42em;position:absolute; position:relative; left:0; top:10%; background:#fff;   overflow:hidden;}
.timeText{ color:#3f444b;  height:2em;width:100%; color:#000;left:4%;font-size:3em; }
.jiangexian{ position:absolute; top:40%; border-bottom:1px solid #e8e8e8;border-top:1px solid #e8e8e8; height:2em; width:100%;}
.timeDiv{ color:#979797;height:100%;width:100%; overflow:hidden; position:relative; left:8em; }

.timeConform1{ border-top:1px solid #ccc; height:2em; line-height:2em;  border-right:1px solid #ccc; width:49%;    }
.timeConform2{ border-top:1px solid #ccc; height:2em; line-height:2em;width:50%;    }
.jiange{ height:2em; width:100%; border-bottom: 1px solid #e8e8e8; border-top: 1px solid #e8e8e8; position:absolute; top:6em; left:0;}
.timexiaoshi{height:100%; width:47%; overflow:hidden; }
.timexiaoshiSub{width:30%;  }
.timeJianGe{width:6%;line-height:10em;font-size:3em;}
.timefenzhong{height:100%; width:20%; overflow:hidden;}
.timefenzhongSub{width:30%;  }
 
.timexiaoshiSub div{font-size:6em;font-weight:100;}
.timefenzhongSub div{font-size:6em;font-weight:100;}
/*å¬å±å¼æ ·*/
.textAlign2{ text-align:left;}

</style>

<script>
 
var curHour = 0;//æ¶é´æ§ä»¶ï¼å½åéä¸­çå°æ¶
var curMinute = 0;//åéæ§ä»¶ï¼å½åéä¸­çåé
var averageTimeHeight = 0;//æ¶é´æ§ä»¶æ¯ä¸ªdivçé«åº¦




zy_init();//æ§å¶ä¸ååå·ææºçå­ä½å¤§å°

//æ§å¶ä¸ååå·ææºçå­ä½å¤§å°
function zy_init(){

	if(window.navigator.platform=="Win32")
		document.body.style.fontSize=window.localStorage["defaultfontsize"];
}

window.onload = function(){
	averageTimeHeight = document.getElementById("hourHeight_0").offsetHeight;
	initMinDiv();
	initDefaultTime();
	 
	touchFuncTime(document.getElementById("timexiaoshi"), document.getElementById("timexiaoshiSub"), "", "", true);
	touchFuncTime(document.getElementById("timefenzhong"), document.getElementById("timefenzhongSub"), "", "", true);
 
	
}

 

 
 
 




function initMinDiv(){//åå§ååéæ§ä»¶
		
	var html ='';
	
 	for (var i=0;i<60;i++)
	{
		if(i<10){
			html+="<div id='minHeight_"+i+"'>0"+i+"</div>"
		}else{
			html+="<div id='minHeight_"+i+"'>"+i+"</div>"
		}
		
	}
	 
	
	 minuteHtml = html;
	document.getElementById("timefenzhongSub").innerHTML = minuteHtml+minuteHtml+minuteHtml;
	

}


function showTime(){//æ¾ç¤ºæ¶é´æ§ä»¶
	
	document.getElementById("timePop").style.visibility = "visible";
	
}


function hiddenTimePop(){
	
		document.getElementById("timePop").style.visibility = "hidden";
	
	
	
}




var hourHtml = "<div id=''>00</div><div id=''>01</div><div id=''>02</div><div id=''>03</div><div id=''>04</div><div id=''>05</div><div id=''>06</div><div id=''>07</div><div id=''>08</div><div id=''>09</div><div id=''>10</div><div id=''>11</div><div id=''>12</div><div id=''>13</div><div id=''>14</div><div id=''>15</div><div id=''>16</div><div id=''>17</div><div id=''>18</div><div id=''>19</div><div id=''>20</div><div id=''>21</div><div id=''>22</div><div id=''>23</div>";
var minuteHtml = "";                        

function initCurTime(){//åå§åå½åæ¶é´ï¼æ·»å çæ¶åç¨

	
		var time = new Date();
		var hours = time.getHours();
		var minutes = time.getMinutes();
		curHour = parseInt(hours);
		curMinute = parseInt(minutes);
		
		document.getElementById("timexiaoshi").scrollTop = averageTimeHeight*22+parseInt(hours) * averageTimeHeight;
		document.getElementById("timefenzhong").scrollTop = averageTimeHeight*58+parseInt(minutes) * averageTimeHeight;
		//document.getElementById("hourHeight_"+parseInt(hours) ).style.color = "#000000";
		//document.getElementById("minHeight_"+parseInt(minutes)).style.color = "#000000";
		$("#timexiaoshiSub").children().eq(24+parseInt(hours)).css({'color':'#000000'});
		$("#timefenzhongSub").children().eq(60+parseInt(minutes)).css({'color':'#000000'});
		
		
		if(parseInt(hours)<10){hours="0"+hours;} 
		if(parseInt(minutes)<10){minutes="0"+minutes;} 
		document.getElementById("sencetime").innerHTML = hours+":"+minutes;	
}
function initDefaultTime(){//åå§åæ¶é´,,ç¼è¾çæ¶åç¨
	 
	//document.getElementById().scrollTop = "";	
	
}

var touchFuncTime = function(obj, moveObj, type, func, isScroll) {
    //æ»å¨èå´å¨5x5åååç¹å»å¤çï¼sæ¯å¼å§ï¼eæ¯ç»æ
    var init = {x:5,y:5,sx:0,sy:0,ex:0,ey:0};
    var sTime = 0, eTime = 0;
    type = type.toLowerCase();
	var lastMoveObjTop = obj.scrollTop;//è®°å½ç§»å¨èç¹çä¸ä¸æ¬¡topå¼
	 //èç¹æ»å¨åè§¦æ¸æ»å¨çæ¯è¾
 	var multiple = 1.3;
	var lastMinute = curMinute;
	var lastHour = curHour;	
	//alert(obj.scrollTop)
	
    obj.addEventListener("touchstart",function(){
        sTime = new Date().getTime();
        init.sx = event.targetTouches[0].pageX;
        init.sy = event.targetTouches[0].pageY;
        init.ex = init.sx;
        init.ey = init.sy;
		
		
		 
        if(type.indexOf("start") != -1) func();
    }, false);
 
    obj.addEventListener("touchmove",function() {
        event.preventDefault();//é»æ­¢è§¦æ¸æ¶æµè§å¨çç¼©æ¾ãæ»å¨æ¡æ»å¨
        init.ex = event.targetTouches[0].pageX;
        init.ey = event.targetTouches[0].pageY;
		
		if(isScroll){
			var changeY = init.ey - init.sy;
			//multiple = moveObj.offsetHeight/obj.offsetHeight;
			 
		 
			if(changeY<0){//divå¾ä¸æ»å¨,scrollTopåå¤§
				if((moveObj.offsetHeight-obj.offsetHeight-obj.scrollTop)<averageTimeHeight*5){
				 
					if(obj == document.getElementById("timexiaoshi")){
						$(moveObj).prepend(hourHtml);
					}else{
						$(moveObj).prepend(minuteHtml);
					}
				}
				obj.scrollTop = (lastMoveObjTop+Math.abs(changeY)*multiple);
				
				
			}else if(changeY>0){//divå¾ä¸æ»å¨,scrollTopåå°
				if(obj.scrollTop<averageTimeHeight*5){
					if(obj == document.getElementById("timexiaoshi")){
						$(moveObj).prepend(hourHtml);
						obj.scrollTop = (lastMoveObjTop+(24*averageTimeHeight)-Math.abs(changeY)*multiple);
						lastMoveObjTop +=(24*averageTimeHeight);
					}else{
						$(moveObj).prepend(minuteHtml);
						obj.scrollTop = (lastMoveObjTop+(60*averageTimeHeight)-Math.abs(changeY)*multiple);
						lastMoveObjTop +=(60*averageTimeHeight);
					}
					
					
					
				}else{
					obj.scrollTop = (lastMoveObjTop-Math.abs(changeY)*multiple);
				}
				
			}
		 
			
			
			
		}else if(!isScroll){
			return;
		}
			
		 
		if(type.indexOf("move")!=-1) func();
    }, false);
 
    obj.addEventListener("touchend",function() {
        var changeX = init.sx - init.ex;
        var changeY = init.sy - init.ey;		
		 
		
		//è¡¨ç¤ºéèäºå ä¸ªdiv
		var curTopNum = 0;
		if(obj == document.getElementById("timexiaoshi")){
			if(obj.scrollTop>22*averageTimeHeight){
				curTopNum = Math.round(obj.scrollTop%(24*averageTimeHeight)/averageTimeHeight)+2;
			}else{
				curTopNum = Math.round(obj.scrollTop/averageTimeHeight)+2;
			}
			 
			 $(moveObj).children().eq(curTopNum).css({'color':'#000000'});
			// alert("å°æ¶curTopNum=="+curTopNum)
			 
			curHour = Math.abs(curTopNum);
			if(curHour>23){curHour-=24;}
			//alert("å°æ¶curHour=="+curHour)
			//document.getElementById("hourHeight_"+curHour).style.color = "#000000";
			
		}else{
			if(obj.scrollTop>58*averageTimeHeight){
				curTopNum = Math.round(obj.scrollTop%(60*averageTimeHeight)/averageTimeHeight)+2;
			}else{
				curTopNum = Math.round(obj.scrollTop/averageTimeHeight)+2;
			}
			
			$(moveObj).children().eq(curTopNum).css({'color':'#000000'});
			//alert("åécurTopNum=="+curTopNum)
			curMinute = Math.abs(curTopNum);
			if(curMinute>=60){curMinute-=60;}
			//alert("åécurMinute=="+curMinute)
			//document.getElementById("minHeight_"+curMinute).style.color = "#000000";
		}
		obj.scrollTop = ((curTopNum-2)*averageTimeHeight);
		
		if(isScroll){
			lastMoveObjTop = parseInt(obj.scrollTop);
			//lastMinute = curMinute;
			//lastHour = curHour;
			
		}//è®°å½ææç¦»å¼çæ¶åï¼æ¹åä½ç½®çèç¹çtopå¼
        if(type.indexOf("end")!=-1) func();
    }, false);
};


 
 </script>
 
</body>
   
</html>