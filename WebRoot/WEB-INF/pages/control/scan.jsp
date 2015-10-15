<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
  <head>
	
    <title>添加设备</title>

    <%@include file="../../../common/jsp/include_css.jsp" %>

  </head>
  
  <body style="background-color: white;">
  <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
      <script type="text/javascript" src="${path }/js/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
        
      
  	wx.config({
	    beta: true,
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${appId}', // 必填，公众号的唯一标识
	    timestamp: ${timestamp}, // 必填，生成签名的时间戳
	    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList: ['scanQRCode',,'closeWindow'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
  
  	wx.ready(function ()
  	 {	  		   
		wx.hideOptionMenu();//隐藏右上角菜单接口
		wx.scanQRCode({
		    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
		    success: function (res) {
		      var mac = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
		      $.ajax({
				    type: "POST",
				   url: "${ctx}/bindDevice.htm?scanQRCode",
				   data: "&openid=${openid}&mac="+mac,
				   timeout: 20000,
				   dataType:'json',
				   success: function(msg){					
				    wx.closeWindow();
				    if(msg.errmsg=='ok'){
				    	location.href="${ctx}/deviceList.htm?openid=${openid}";
					 }else{
						 alert("数据错误");
					 }
				   },
				   error:function (XMLHttpRequest, textStatus, errorThrown) {
				 		alert("数据错误");
				   }

				});
		   
			     }  			      				 	           	
			});
 	  });
	 	  
			
	wx.error(function(res){
		//alert("errMsg:"+res.errMsg);
	});
  
  
  </script>
  </body>
</html>
