<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
  <head>
	
    <title>大狗卫士</title>

    <%@include file="../../../common/jsp/include_css.jsp" %>
    <style type="text/css">
  hr{
     
      height: 1px;
      border-top: 1px solid #cccccc;
      border-bottom: 0px;
      border-left: 0px;
      border-right: 0px;
    }
    </style>
  </head>
  <body>
    <div class="content">
           <div class="font-size16" style="background: #5bc0de;border-radius:6px;width: 8em;color: #FFFFFF;">已添加的探测器</div> 
            <br/>
           <div style="border-bottom: 1px solid red;"><span class="font-size16 color5 float1">探测器A</span> <span class="color5 float2" >添加于2015/1/1</span> </div>
           
             
             <div style="border-bottom: 1px solid red;"><span class="font-size16 color5 float1">探测器B</span> <span class="color5 float2" >添加于2015/1/1</span> </div>
            
            <br/>
             <div class="font-size20 color5 margin-top1">报警主机与探测器绑定成功</div>
           
           <p class="text-left color1 margin-top1">请为此主机命名</p>
      <div class="form-group">
	    <input type="text" class="form-control"  name="devName" id="devName" placeholder="" value="探测器${next_name}">
	  </div>     
   <p>
    <button type="button" id="linkwifiBtn" class="btn btn-lg btn-info"> &nbsp;&nbsp;&nbsp;完成&nbsp;&nbsp;&nbsp;&nbsp;</button></p>
    <br/><br>
    </div>
     <%@include file="../../../common/jsp/include_js.jsp" %>
     
     <script type="text/javascript">
         
        
         
         window.onload=function(){
        	 
             var kid=${dev_kid};
             var openid='${openid}';
             var devName=$("#devName").val();
        	
        		 $("#linkwifiBtn").click(function(){
        			 
        			 if(devName!=""){
        				 $(this).attr("disabled","disabled");
        				 $.ajax({
      					   type: "POST",
      					   url: "${ctx}/bindDevice.htm?rename",
      					   data: "&devName="+devName+"&openid="+openid+"&kid="+kid,
      					   timeout: 40000,
      					   dataType:'json',
      					   success: function(msg){
      						 if(msg.errcode==0){
      					    	 alert("操作成功！");
      					     }else{
      					    	 alert("操作失败！请重试");
      					    	$("#linkwifiBtn").removeAttr("disabled");
      					     }
      					     wx.closeWindow();
      					   },
      					   error:function (XMLHttpRequest, textStatus, errorThrown) {
      						   // alert(XMLHttpRequest.status);
                                //  alert(XMLHttpRequest.readyState);
                               //   alert(textStatus);
      						    alert("请求异常！");
                                  $("#linkwifiBtn").removeAttr("disabled");
      					   }

      					});
        			 }
            		
            	 });//end click
        	 
        };
        	 
     </script>
       <script type="text/javascript">
        
      
  	wx.config({
	  beta: true,
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${appId}', // 必填，公众号的唯一标识
	    timestamp: ${timestamp}, // 必填，生成签名的时间戳
	    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList: ['configWXDeviceWiFi',
	                'hideAllNonBaseMenuItem',
	                'showAllNonBaseMenuItem','closeWindow'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
  
  	wx.ready(function () {
	  	
	    wx.hideAllNonBaseMenuItem();

	   
	 	 wx.error(function(res){
	 		alert("errMsg:"+res.errMsg);
	 	});
	   
	   </script>
  </body>
</html>
