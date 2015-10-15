<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<%@include file="../../../common/jsp/include_css.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
  
	<!-- 提示框 -->
	<div id="openWinDiv"
		style="position: fixed;text-align: center;top: 0;z-index: 999;width: 100%;height: 100%;background: rgba(8, 8, 8, 0.7); visibility: hidden;">
		<div style="position: relative;top:40%;height: 60%;text-align: center;">
		      <div class="font-size18" id="popwindows1" style="width: 12em;background: #ffffff no-repeat;border-radius: 0.4em; margin: 0 auto;">
		           <div class="color5 " id="openTitlediv" style="height: 3em;line-height: 3em;">提示</div>
		           <div id="content" style="font-size:0.5em;border-bottom: 1px solid #EAEAEA;" >成功添加用户xxx</div>
		           <div style="line-height: 2.5em;"> 
			          
			            <%--<div style="background-color: #EAEAEA;display: inline;height: 1px;">&nbsp;</div>
			           --%>
			           <div id="surediv"  class="color5" style="text-align: center;display: inline;width: 80px;cursor: pointer;">是</div>
		           </div>
		      </div>
		 </div>
	</div>
     <%@include file="../../../common/jsp/include_js.jsp" %>
     <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
     <script type="text/javascript">
   
  	wx.config({
	    beta: true,
	    debug: false, 
	    appId: '${appId}', 
	    timestamp: '${timestamp}', 
	    nonceStr: '${nonceStr}', 
	    signature: '${signature}',
	    jsApiList: ['configWXDeviceWiFi'] 
	});
  
  	wx.ready(function () {	  	
  		    wx.hideOptionMenu();//隐藏右上角菜单接口
			wx.invoke('configWXDeviceWiFi', {
				"title": "上能智能",
				"link": "",
				"desc": "",
				"thumbimg": ""
				}, function(res){
					if(res.err_msg=='configWXDeviceWiFi:ok'){
						$.ajax({
							   type: "POST",
							   url: "${ctx}/airkiss.htm",
							   data: "&openid=${openid}",
							   timeout: 40000,
							   dataType:'json',
							   success: function(msg){
							    	$("#openTitlediv").text("配置成功 ");
							    	  $("#content").text("如果没绑定设备,请在主菜单点击“添加设备”已完成绑定");					   
			  						document.getElementById("openWinDiv").style.visibility = "visible";
							   },
							   error:function (XMLHttpRequest, textStatus, errorThrown) {
								   $("#openTitlediv").text("配置成功 ");	
 									$("#content").text("如果没绑定设备,请在主菜单点击“添加设备”已完成绑定");				   
			  						document.getElementById("openWinDiv").style.visibility = "visible";
							    	 
							   }

							});
					
					}else if(res.err_msg=='configWXDeviceWiFi:fail'){
						$("#openTitlediv").text("联网失败 ");	
						$("#content").text("请检查设备与路由器的距离，保证通信顺畅。");					   
			  			document.getElementById("openWinDiv").style.visibility = "visible";
							     
					}
			//		console.log(res);
				});
	
	});
	
	wx.error(function(res){
	 $("#openTitlediv").text("提示");		
	  $("#content").text("服务器忙，请稍后再试！");					   
	 document.getElementById("openWinDiv").style.visibility = "visible";
   	 
	});
    $(function (){        	 			 			 			 
	   	 $("#surediv").click(function (){
	   	 wx.closeWindow();
	   	 });
  	 });
	
  </script>
  </body>
</html>
