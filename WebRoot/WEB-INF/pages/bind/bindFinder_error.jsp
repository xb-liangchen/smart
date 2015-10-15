<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
  <head>
	
    <title>大狗卫士</title>

    <%@include file="../../../common/jsp/include_css.jsp" %>
    <style type="text/css">
  
    </style>
  </head>
  <body>
    <div class="content">
        <img id="pic1" width="90%" height="45%" src="${ctx }/common/images/image-56.png" />  
        <div style="z-index: 100;padding-top: -40px;position:absolute;font-weight: bold ;width: 90%">
           <p class="color2 font-size18">报警主机与探测器绑定不成功,探测器接收码出错</p>
           <p class="color2 font-size18">请重新再扫一次</p>
         </div> 
         <br>
   <p style="position: relative;margin-top: 60%;">
    <button type="button" id="saoBtn" class="btn btn-lg btn-info"> &nbsp;&nbsp;&nbsp;返回&nbsp;&nbsp;&nbsp;&nbsp;</button>
    </p>
    
    </div>
     <%@include file="../../../common/jsp/include_js.jsp" %>
     <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
      <script type="text/javascript">
        
      
  	wx.config({
	    beta: true,
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${appId}', // 必填，公众号的唯一标识
	    timestamp: ${timestamp}, // 必填，生成签名的时间戳
	    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList: ['closeWindow'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
  	
  	wx.ready(function () {
  		
  		wx.hideOptionMenu();//隐藏右上角菜单接口
  		
  		$("#saoBtn").click(function(){
  			 wx.closeWindow();
    	});
	});
	
	wx.error(function(res){
		//alert("errMsg:"+res.errMsg);
	});
  
  </script>
  </body>
</html>
