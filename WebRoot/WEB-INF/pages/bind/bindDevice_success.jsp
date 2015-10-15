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
           <img id="pic1" width="90%" height="45%" src="${ctx }/common/images/image-59.png" />  
         
           <p class="text-left color1 margin-top1">请为此主机命名</p>
      <div class="form-group">
	    <input type="text" class="form-control"  name="devName" id="devName" placeholder="" value="${dev_name}">
	  </div>     
   <p>
    <button type="button" id="linkwifiBtn" class="btn btn-lg btn-info"> &nbsp;&nbsp;&nbsp;完成&nbsp;&nbsp;&nbsp;&nbsp;</button></p>
    <br/><br>
    <p class="text-left color1 margin-top1">完成后，请点击首页菜单对报警主机设置"用户"及"探测器"</p>
    </div>
    
    
    <!-- 提示框 -->
	<div id="openWinDiv"
		style="position: fixed;text-align: center;top: 0;z-index: 999;width: 100%;height: 100%;background: rgba(8, 8, 8, 0.7); visibility: hidden;">
		<div style="position: relative;top:40%;height: 60%;text-align: center;">
		      <div class="font-size18" id="popwindows1" style="width: 12em;background: #ffffff no-repeat;border-radius: 0.4em; margin: 0 auto;">
		           <div class="color5 " id="openTitlediv" style="height: 3em;line-height: 3em;">学习成功</div>
			           <div id="surediv"  class="color5" style="text-align: center;display: inline;width: 80px;cursor: pointer;">确定</div>
		           </div>
		      </div>
		 </div>
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
	    jsApiList: ['hideOptionMenu','closeWindow'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
  
	  	wx.ready(function () {
		  	
	  		wx.hideOptionMenu();//隐藏右上角菜单接口
	  	});
	 	 wx.error(function(res){
	 		//alert("errMsg:"+res.errMsg);
	 	});
	   
	   </script>
	   <script type="text/javascript">
         
        
         
         window.onload=function(){
        	 
             var kid=${dev_kid};
             var openid='${openid}';
              $("#surediv").click(function (){
        	 wx.closeWindow();
        	 });
        	
        		 $("#linkwifiBtn").click(function(){
        			 var devName=$("#devName").val();
        			 if(devName!=""){
        				 $(this).attr("disabled","disabled");
        				 $.ajax({
      					   type: "POST",
      					   url: "${ctx}/bindDevice.htm?rename",
      					   data: "&devName="+devName+"&openid="+openid+"&kid="+kid,
      					   timeout: 40000,
      					   dataType:'json',
      					   success: function(msg){
      						 if(msg.errcode==0 || msg.errcode=="0"){
      					    	// alert("操作成功！");
      					    	$("#openTitlediv").text("操作成功！");					   
			  						document.getElementById("openWinDiv").style.visibility = "visible";//	
      					     }else{
      					    	// alert("操作失败！请重试");
      					    	 $("#openTitlediv").text("操作失败！请重试");					   
			  						document.getElementById("openWinDiv").style.visibility = "visible";//
      					    	$("#linkwifiBtn").removeAttr("disabled");
      					     }
      					   },
      					   error:function (XMLHttpRequest, textStatus, errorThrown) {
      						   // alert(XMLHttpRequest.status);
                                //  alert(XMLHttpRequest.readyState);
                               //   alert(textStatus);
      						   // alert("请求异常！");
      						    $("#openTitlediv").text("请求异常！");					   
			  						document.getElementById("openWinDiv").style.visibility = "visible";//
                                  $("#linkwifiBtn").removeAttr("disabled");
      					   }

      					});
        			 }
            		
            	 });//end click
        	 
        };
        	 
     </script>
  </body>
</html>
