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

 

 

<div class="timePop" id="timePop" >
    <!--时间控件---S-->  
    <div class="timeAll u-b-radius">
		<div class="jiange">
		 <div class="jiange1 float1">时</div>
		 <div class="jiange2 float1">分</div>
		</div>

        <div class="timeText textAlign2">自定时间</div>	
 
        <div class="timeDiv">
         
            
            <!--小时的最外侧div，高度固定不动-->
            <div class="timexiaoshi float1" id="timexiaoshi">
            	<!--里层包含小时的div，这个div高度是自动的-->
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
            

             <div class="timeJianGe float1" >
              <div class="timeJianGe1 "></div>
              <div class="timeJianGe2 ">:</div>
              <div class="timeJianGe4 "></div>
             </div>

            <div class="timefenzhong float1" id="timefenzhong"><!--分钟div-->
            	<div class="timefenzhongSub" id="timefenzhongSub">
                
                
                </div>
            </div>
            
        </div>
        
        <!--  	
        <div style="height:2em;">
            <div class="timeConform1 color9 textAlign2 float1" onClick="hiddenTimePop()">取消</div>	
            <div class="timeConform2 color9 textAlign2 float1" onClick="showTimeText()">确定</div>	
        </div>
        -->
    </div>   
    <!--时间控件---E-->  
</div>
<div class="alarmsetBottomClass" id="alarmsetBottomClass">
	<div style="float: left;width: 8em;padding-left: 9%;"><img style="margin-top:1em;" src="../images/2x/icon_back.png" onClick="hiddenTimePop()"></div>
	<div style="float: right;text-align: center;padding-right: 9%;margin-top: 0.5em;height: 100%;" onClick="showTimeText()">
     <span style="color: white;font-size:3em">保存</span>
    </div>
</div>
<style>
.timePop{ position:absolute; left:0px; top:0px; background:url(../images/pop_bg.png); z-index:9999; width:100%; height:100%;}
.timeAll{ width:100%; color:#9a9a9a; height:35.5em; position:absolute; position:relative; left:0%; top:10%; background:#fff;   overflow:hidden;}
.timeText{ color:#3f444b;  height:2.5em;width:94%; color:#000; font-weight:bold; font-size: 2.4em; left: 6%;}
.jiangexian{ position:absolute; top:40%; border-bottom:1px solid #e8e8e8;border-top:1px solid #e8e8e8; height:2em; width:100%;}
.timeDiv{ color:#979797;height:30em;   width:100%; overflow:hidden; position:relative;  }

.timeConform1{ border-top:1px solid #ccc; height:2em; line-height:2em;  border-right:1px solid #ccc; width:49%;    }
.timeConform2{ border-top:1px solid #ccc; height:2em; line-height:2em;width:50%;    }
.jiange{ height:6em; width:100%; border-bottom: 1px solid #e8e8e8; border-top: 1px solid #e8e8e8; position:absolute; top:18em; left:0;background-color:blue;}
.jiange1{height:3em;width: 37%;text-align: right;font-size: 2em;line-height: 5em;}
.jiange2{height:3em;width: 35%;text-align: right;font-size: 2em;line-height: 5em;}
 .timexiaoshi{ height:100%; width:47%;   overflow:hidden; }
 .timexiaoshiSub{ padding-left:50%; width:30%; font-size: 6em;}
 .timeJianGe{width:1%;height:30em; }
 .timeJianGe1{width:100%;height:40%; line-height:10em;border-right: solid 1px #000;}
 .timeJianGe2{width:100%;height:20%; text-align: right;color:#FFFFFF;font-size:4em;}
 .timeJianGe3{width:100%;height:10%; line-height:10em;text-align: center;color:black;}
 .timeJianGe4{width:100%;height:40%; line-height:10em;border-right: solid 1px #000;}
 

.timefenzhong{height:100%; width:47%;  overflow:hidden; }
.timefenzhongSub{ padding-left:22%; width:90%; font-size: 6em;}
 
.timexiaoshiSub div{height:1em; line-height:1em; font-weight:100;}
.timefenzhongSub  div{height:1em; line-height:1em; font-weight:100;}


 
/*公共式样*/
.textAlign2{ text-align:left;}

/*返回 保存 样式*/
.alarmsetBottomClass{
 position:fixed;
 bottom:0px;
 left:0px;
 float:left;
 width:100%;
 height:6em;
 background-color:black;
 color:#fff;
}
</style>

<script>
 
var curHour = 0;//时间控件，当前选中的小时
var curMinute = 0;//分钟控件，当前选中的分钟
var averageTimeHeight = 0;//时间控件每个div的高度




zy_init();//控制不同型号手机的字体大小

//控制不同型号手机的字体大小
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

 

 
 
 




function initMinDiv(){//初始化分钟控件
		
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


function showTime(){//显示时间控件
	
	document.getElementById("timePop").style.visibility = "visible";
	
}


function hiddenTimePop(){
	
		document.getElementById("timePop").style.visibility = "hidden";
	
	
	
}

function showTimeText(){
 alert(123456);
}


var hourHtml = "<div id=''>00</div><div id=''>01</div><div id=''>02</div><div id=''>03</div><div id=''>04</div><div id=''>05</div><div id=''>06</div><div id=''>07</div><div id=''>08</div><div id=''>09</div><div id=''>10</div><div id=''>11</div><div id=''>12</div><div id=''>13</div><div id=''>14</div><div id=''>15</div><div id=''>16</div><div id=''>17</div><div id=''>18</div><div id=''>19</div><div id=''>20</div><div id=''>21</div><div id=''>22</div><div id=''>23</div>";
var minuteHtml = "";                        

function initCurTime(){//初始化当前时间，添加的时候用

	
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
function initDefaultTime(){//初始化时间,,编辑的时候用
	 
	//document.getElementById().scrollTop = "";	
	
}

var touchFuncTime = function(obj, moveObj, type, func, isScroll) {
    //滑动范围在5x5内则做点击处理，s是开始，e是结束
    var init = {x:5,y:5,sx:0,sy:0,ex:0,ey:0};
    var sTime = 0, eTime = 0;
    type = type.toLowerCase();
	var lastMoveObjTop = obj.scrollTop;//记录移动节点的上一次top值
	 //节点滚动和触摸滚动的比较
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
        event.preventDefault();//阻止触摸时浏览器的缩放、滚动条滚动
        init.ex = event.targetTouches[0].pageX;
        init.ey = event.targetTouches[0].pageY;
		
		if(isScroll){
			var changeY = init.ey - init.sy;
			//multiple = moveObj.offsetHeight/obj.offsetHeight;
			 
		 
			if(changeY<0){//div往上滚动,scrollTop变大
				if((moveObj.offsetHeight-obj.offsetHeight-obj.scrollTop)<averageTimeHeight*5){
				 
					if(obj == document.getElementById("timexiaoshi")){
						$(moveObj).prepend(hourHtml);
					}else{
						$(moveObj).prepend(minuteHtml);
					}
				}
				obj.scrollTop = (lastMoveObjTop+Math.abs(changeY)*multiple);
				
				
			}else if(changeY>0){//div往下滚动,scrollTop变小
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
		 
		
		//表示隐藏了几个div
		var curTopNum = 0;
		if(obj == document.getElementById("timexiaoshi")){
			if(obj.scrollTop>22*averageTimeHeight){
				curTopNum = Math.round(obj.scrollTop%(24*averageTimeHeight)/averageTimeHeight)+2;
			}else{
				curTopNum = Math.round(obj.scrollTop/averageTimeHeight)+2;
			}
			 
			 $(moveObj).children().eq(curTopNum).css({'color':'#000000'});
			// alert("小时curTopNum=="+curTopNum)
			 
			curHour = Math.abs(curTopNum);
			if(curHour>23){curHour-=24;}
			//alert("小时curHour=="+curHour)
			//document.getElementById("hourHeight_"+curHour).style.color = "#000000";
			
		}else{
			if(obj.scrollTop>58*averageTimeHeight){
				curTopNum = Math.round(obj.scrollTop%(60*averageTimeHeight)/averageTimeHeight)+2;
			}else{
				curTopNum = Math.round(obj.scrollTop/averageTimeHeight)+2;
			}
			
			$(moveObj).children().eq(curTopNum).css({'color':'#000000'});
			//alert("分钟curTopNum=="+curTopNum)
			curMinute = Math.abs(curTopNum);
			if(curMinute>=60){curMinute-=60;}
			//alert("分钟curMinute=="+curMinute)
			//document.getElementById("minHeight_"+curMinute).style.color = "#000000";
		}
		obj.scrollTop = ((curTopNum-2)*averageTimeHeight);
		
		if(isScroll){
			lastMoveObjTop = parseInt(obj.scrollTop);
			//lastMinute = curMinute;
			//lastHour = curHour;
			
		}//记录手指离开的时候，改变位置的节点的top值
        if(type.indexOf("end")!=-1) func();
    }, false);
};


 
 </script>
 
</body>
   
</html>