var isScrollContent = true;//当拖动家电的时候设置这个值为false，那么content就不乱滑动了

/**
 * @description 封装localStorage的方法
 * @constructor Storage
 */
function Storage(){
    /**
     * @description 获得对应的键值
     * @param key
     * @returns {*}
     */
    this.get=function(key) {
        return localStorage.getItem(key);
    }
    /**
     * @description 添加键值
     * @param key
     * @param value
     */
    this.set=function(key, value){
        localStorage.setItem(key, value);
    }
    /**
     * @description 删除对应的键值
     * @param key
     */
    this.rm=function(key) {
        localStorage.removeItem(key);
    }
    /**
     * @description 清空localStorage
     */
    this.clear=function() {
        localStorage.clear();
    }
    /**
     * @description 获取对应的键值
     * @return 返回的键值为json对象
     */
    this.getJSON=function(key) {
        return JSON.parse(localStorage.getItem(key));
    }
    /**
     * @description 设置键值为json字符串
     * @param key
     * @param value
     */
    this.setJSON=function(key, value) {
        localStorage.setItem(key, JSON.stringify(value));
    }     
}

/**
 * @description Storage对象
 * @constructor Storage
 */
var globalStorage=new Storage();







function getbytelength(str){ //返回字符串的长度（汉字占2个字节）
	var byteLen=0,len=str.length;
	if(str){
		for(var i=0;i<len;i++)
		{
		   if(str.charCodeAt(i)>255)
		      byteLen+=2;
		   else
		      byteLen++;
		}
	    return byteLen;
	}
	    return 0;
}
function getcutedstring(sSource,iLen,dot){//按照长度截取字符串,并在后面加".."
    var str = "";
    var l = 0;
    var schar;
	if(!dot)
	  iLen=iLen-2;
    for(var i=0; schar=sSource.charAt(i); i++)
     {
         str += schar;
         l += (schar.match(/[^\x00-\xff]/) != null ? 2 : 1);
        if(l >= iLen)
         {
            break;
         }
     }
	if(dot)
	  return str+"..";  //这里修改成2个点
	else 
	  return str;
}
function isEmpty(obj){//判断一个对象是否为空
	var result = false;
	if(null==obj || undefined==obj || ""==obj){
		result = true;
	}	
	return result;
}


function caseToJosn(data){//将bridge传入过来的数据转为json格式
		var result = JSON.stringify(data);
		result = "["+result+"]"
		result =  eval('(' + result + ')')
		return result;
}

function setTextByLength(name){//第一次进入和我的家电页面的家电名称的长度
	if(getbytelength(name)>10){
		return getcutedstring(name,10,true)
	}else{
		return name;
	}

}
function initPagePop(){//初始化弹出提示loading
	 
	document.getElementById("popDivPint").style.marginTop = window.screen.height/2.4+"px";
	touchFunc(document.getElementById("popDiv"),"", "", "", false);	//防止浏览器拉动
}
function showPagePop(text){//显示弹出提示loading
	var hint = (isEmpty(text)) ? "加载中..." : text;

	document.getElementById("popDiv").style.display = "block";
	document.getElementById("popDivText").innerHTML = hint;
	
}
function hiddenPagePop(){//隐藏弹出提示loading
	document.getElementById("popDiv").style.display = "none";
}

function getRoomImgByName(roomId){//根据房间名称获取房间的图片
	var picUrl = "";
	if(roomId==2){ //我的e家
		picUrl = "icon_42.png";
	}else if(roomId==3){ //客厅
		picUrl = "icon_50.png";
	}else if(roomId==4){ //主卧
		picUrl = "icon_59.png";
	}else if(roomId==5){ //儿童房
		picUrl = "icon_75.png";
	}else if(roomId==6){ //老人房
		picUrl = "icon_68.png";
	}else{
		picUrl = "icon_168.png";
	}
	return picUrl;
	
}
function getApplianceListByNameLight(name){//根据家电名称获取家电的图片
	var imgurl = "";
	if(name.indexOf("加湿")!=-1){
		imgurl = "icon_39_on.png";
	}else if(name.indexOf("空调")!=-1){
		imgurl = "icon_38_on.png";		
	}else if(name.indexOf("除湿")!=-1){
		imgurl = "icon_55_on.png";		
	}else if(name.indexOf("净化")!=-1){
		imgurl = "icon_56_on.png";		
	}else if(name.indexOf("风扇")!=-1){
		imgurl = "icon_57_on.png";		
	}else{
		imgurl = "icon_57_on.png";	
	}
	return imgurl;
	
}

function getApplianceListByName(name){//根据家电名称获取家电的图片
	var imgurl = "";
	if(name.indexOf("加湿")!=-1){
		imgurl = "icon_39.png";
	}else if(name.indexOf("空调")!=-1){
		imgurl = "icon_38.png";		
	}else if(name.indexOf("除湿")!=-1){
		imgurl = "icon_55.png";		
	}else if(name.indexOf("净化")!=-1){
		imgurl = "icon_56.png";		
	}else if(name.indexOf("风扇")!=-1){
		imgurl = "icon_57.png";		
	}else{
		imgurl = "icon_57.png";	
	}
	return imgurl;
	
}
function getSceneImgByName(name){//根据情景名称获取情景的图片
	var imgurl = "";
	
	 
	if(name.indexOf("回家模式")!=-1){
		imgurl = "icon_179.png";
	}else if(name.indexOf("离家模式")!=-1){
		imgurl = "icon_48.png";		
	}else if(name.indexOf("一键OK")!=-1){
		imgurl = "icon_53.png";		
	}else if(name.indexOf("一键去")!=-1){
		imgurl = "icon_60.png";		
	}else if(name.indexOf("一键干爽")!=-1){
		imgurl = "icon_66.png";		
	}else if(name.indexOf("聚会模式")!=-1){
		imgurl = "icon_77.png";		
	}else if(name.indexOf("用餐模式")!=-1){
		imgurl = "icon_86.png";		
	}else if(name.indexOf("娱乐模式")!=-1){
		imgurl = "icon_70.png";		
	}else if(name.indexOf("一晚好眠")!=-1){
		imgurl = "icon_169.png";		
	}else if(name.indexOf("呵护模式")!=-1){
		imgurl = "icon_92.png";		
	}else if(name.indexOf("学习模式")!=-1){
		imgurl = "icon_100.png";		
	}else if(name.indexOf("健康模式")!=-1){
		imgurl = "icon_170.png";		
	}else{
		imgurl = "icon_171.png";	
	} 	
	
	return imgurl;
	
}

function setTextByLength(name,length){//第一次进入和我的家电页面的家电名称的长度
	if(getbytelength(name)>length){
		return getcutedstring(name,length,true)
	}else{
		return name;
	}

}

 


function getApplianceType(type){
	var result = -1;
	if(type.indexOf("AC")!=-1){
		result=1;
	}else if(type.indexOf("FC")!=-1){
		result=2;
	
	}else if(type.indexOf("FD")!=-1){
		result=3;
	}else if(type.indexOf("A1")!=-1){
		result=4;
	}else if(type.indexOf("FA")!=-1){
		result=5;
	}else{
		result=type;
	}
	return parseInt(result);
}

function getWeek(data){//根据week数组来返回周
	 var week='';
	for (var i=0;i<data.length;i++)
	{
		week+=getWeekNumByShuzi(data[i])+"&nbsp;";
	}	
	return week;
}
function getWeekNumByShuzi(i){//根据数字获取到周

	
	switch(i)
	{
	case 1:
	  return "一";
	  break;
	case 2:
	  return "二";
	  break;
	case 3:
	  return "三";
	  break;
	case 4:
	  return "四";
	  break;
	case 5:
	  return "五";
	  break;
	case 6:
	  return "六";
	  break;
	case 7:
	  return "日";
	  break;
	}

}


//返回上一级页面
var gotoUpPage = function(){
	location.href=upPageUrl
}
function loadFooter(){//加载完成后，显示底部的html
	document.getElementById("footer").style.visibility = "visible";
}
var initToastTime = null;
function initToast(content){//初始化toast提示层
	var screenHeight = window.screen.height;//当前屏幕高度
	var scrollTop = document.body.scrollTop//滚动的高度
	var globeWidthAll = window.screen.width;
	document.getElementById("toast").style.top = scrollTop+(screenHeight*0.5)+"px";	
	
	
	
	
	document.getElementById("toast").innerHTML = content;
	document.getElementById("toast").style.display = "block";
	document.getElementById("toast").style.left=(globeWidthAll-document.getElementById("toast").offsetWidth)/2+"px";
	
	 
	if(initToastTime==null){
		initToastTime = setTimeout(function(){
			document.getElementById("toast").style.display = "none";
		
		},1500);
	}else{
		clearTimeout(initToastTime)
		initToastTime = setTimeout(function(){
			document.getElementById("toast").style.display = "none";
		
		},1500);	
	}
	
	
	 
}




/**
@ description  用户触摸时防止浏览器滚动或者缩小，
@ param:obj  手指触摸的节点
@ param:moveObj  需要进行位置变换的节点
@ param:type  type和func结合起来用，调用什么事件
@ param:func  type和func结合起来用，在发生事件调用什么方法
@ param:isScroll  需要进行位置变换的节点是否需要滚动
@ 调用例子：	
	touchFunc(document.getElementById("bbb"), document.getElementById("div1"), "", "",true)
	touchFunc(document.getElementById("div2"),"", "", "",false)
	touchFunc(document.getElementById("div0"),"", "", "",false)
*/
var touchFunc = function(obj, moveObj, type, func, isScroll) {
    //滑动范围在5x5内则做点击处理，s是开始，e是结束
    var init = {x:5,y:5,sx:0,sy:0,ex:0,ey:0};
    var sTime = 0, eTime = 0;
    type = type.toLowerCase();
	 
	var lastMoveObjTop = obj.scrollTop;//记录移动节点的上一次top值
	 //节点滚动和触摸滚动的比较
 	var multiple = 1.3;//手指移动距离和屏幕滚动scrollTop的距离比率
	 
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
		if(  isScrollContent   ){
			if(isScroll){
				var changeY = init.ey - init.sy;
				 
				//multiple = moveObj.offsetHeight/obj.offsetHeight;
				
				if(changeY<0){//div往上滚动,scrollTop变大
					obj.scrollTop = (lastMoveObjTop+Math.abs(changeY)*multiple);
				}else if(changeY>0){//div往下滚动,scrollTop变小
					obj.scrollTop = (lastMoveObjTop-Math.abs(changeY)*multiple);
				}
			}else if(!isScroll){
				return;
			}
		}
		//scrollTop
		if(type.indexOf("move")!=-1) func();
    }, false);
 
    obj.addEventListener("touchend",function() {
        var changeX = init.sx - init.ex;
        var changeY = init.sy - init.ey;
		
		 
		
		
        if(Math.abs(changeX)>Math.abs(changeY)&&Math.abs(changeY)>init.y) {
            //左右事件
            if(changeX > 0) {
                if(type.indexOf("left")!=-1) func();
            }else{
                if(type.indexOf("right")!=-1) func();
            }
        }
        else if(Math.abs(changeY)>Math.abs(changeX)&&Math.abs(changeX)>init.x){
            //上下事件
            if(changeY > 0) {
                if(type.indexOf("top")!=-1) func();
            }else{
                if(type.indexOf("down")!=-1) func();
            }
        }
        else if(Math.abs(changeX)<init.x && Math.abs(changeY)<init.y){
            eTime = new Date().getTime();
            //点击事件，此处根据时间差细分下
            if((eTime - sTime) > 300) {
				//alert("长按")
                if(type.indexOf("long")!=-1) func(); //长按
            }
            else {
                if(type.indexOf("click")!=-1) func(); //当点击处理
            }
        }
		
	 
		
		if(isScroll){lastMoveObjTop = parseInt(obj.scrollTop);}//记录手指离开的时候，改变位置的节点的top值
		if(type.indexOf("end")!=-1) func();
    }, false);
};


//左右开关按钮绑定事件
var switchTouchFunc = function(obj, data, flag) {
    //滑动范围在5x5内则做点击处理，s是开始，e是结束
    var init = {x:5,y:5,sx:0,sy:0,ex:0,ey:0};
    var sTime = 0, eTime = 0;
    
	  
	 
    obj.addEventListener("touchstart",function(){
        sTime = new Date().getTime();
        init.sx = event.targetTouches[0].pageX;
        init.sy = event.targetTouches[0].pageY;
        init.ex = init.sx;
        init.ey = init.sy;
		isScrollContent = false;//阻止浏览器滚动
		 
    }, false);
  
    obj.addEventListener("touchmove",function() {
        event.preventDefault();//阻止触摸时浏览器的缩放、滚动条滚动
        init.ex = event.targetTouches[0].pageX;
        init.ey = event.targetTouches[0].pageY;

    }, false);
 
    obj.addEventListener("touchend",function() {
        var changeX = init.sx - init.ex;
        var changeY = init.sy - init.ey;
 				
				
		//document.getElementById("ppp").innerHTML = 	changeX+"=="+changeY;	
		if(changeX>0){
			sceneswitch(obj,data,flag,"left")	
		}else if(changeX<0){
			sceneswitch(obj,data,flag,"right")	
			
		}else{
			sceneswitch(obj,data,flag,"click")	
			
		} 
		 isScrollContent = true;//阻止浏览器滚动
    }, false);
};



//开启和关闭情景预约
//flag  表示情景预约和家电预约，1表示情景预约,2表示家电预约
function sceneswitch(obj,data,flag, status){
 	if(obj.src.indexOf("switch.png")==-1){//开启 
			if(status=="right" ||status=="click" ){
				   var queryObj = (flag==2)? {"serviceUrl":"/applianceAppoint/startApplianceAppoint"}: {"serviceUrl":"/sceneAppoint/startSceneAppoint"};
				   var functionParamers={					 
							queryStrings:queryObj,
							transmitData:data
						}; 
							//  alert("开启："+JSON.stringify(functionParamers))
				   bridge.requestDataTransmit(functionParamers, function (messageBack) {
						  // alert("开启："+JSON.stringify(messageBack))
						
							var  data =caseToJosn( messageBack);
							if(parseInt(data[0].result.returnData.errCode)!=0){
								initToast("开启失败!");
							}else if(parseInt(data[0].result.returnData.errCode)==0){
								initToast("开启成功!");
								obj.src = "../images/2x/switch.png";
							}
				   });
		 }
	}else{	
		if(status=="left" ||status=="click" ){
				   var queryObj = (flag==2)? {"serviceUrl":"/applianceAppoint/closeApplianceAppoint"}: {"serviceUrl":"/sceneAppoint/closeSceneAppoint"};
				 var functionParamers={
					 
						queryStrings:queryObj,
						transmitData:data
					};
					   // alert("关闭："+JSON.stringify(functionParamers))
			   bridge.requestDataTransmit(functionParamers, function (messageBack) {
					 // alert("关闭："+JSON.stringify(messageBack))												  
					var  data =caseToJosn( messageBack);
					var msg =   "关闭";
					if(parseInt(data[0].result.returnData.errCode)!=0){
						initToast("请重新进入编辑后在操作!");
					}else if(parseInt(data[0].result.returnData.errCode)==0){
						initToast(msg+"成功!");
						obj.src = "../images/2x/switch_161.png";
					}
					
			   });
		}
	}
	 
}
