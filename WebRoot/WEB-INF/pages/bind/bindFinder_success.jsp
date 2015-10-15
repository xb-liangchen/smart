<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
  <head>
	
    <title>大狗卫士</title>

    <%@include file="../../../common/jsp/include_css.jsp" %>
    <style type="text/css">
  hr{
     
      height: 1px;
      width:100%;
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
            <c:forEach items="${finderList }" var="it">
                 <c:if test="${not empty it}">
                 <c:if test="${it.id == f_kid }" >
                 <c:set var="next_name1" value="${it.name}"/>
                 </c:if>
                    <div style=""> <span class="font-size16 color5 float1"><c:if test="${it.name!=null}">${it.name }</c:if><c:if test="${it.name==null}">探测器</c:if></span><span class="color5 float2" >添加于<fmt:formatDate value="${it.createTime }"  pattern="yyyy/MM/dd" var="d"/>${d }</span> </div>
           			<hr>
                 </c:if>
            </c:forEach>
           
          <br/>
             <div class="font-size20 color5 margin-top1">报警主机与探测器绑定成功</div>
           
           <p class="text-left color1 margin-top1">请为此探测器命名</p>
      <div class="form-group">
	    <input type="text" class="form-control"  name="devName" id="devName" placeholder="" value="${next_name1}">
	  </div>     
   <p>
    <button type="button" id="linkwifiBtn" class="btn btn-lg btn-info"> &nbsp;&nbsp;&nbsp;完成&nbsp;&nbsp;&nbsp;&nbsp;</button></p>
    <br/><br>
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
     <script src="${ctx }/common/js/jquery-1.10.2.min.js" charset="UTF-8"></script>
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
        	 var flag=true;
             var kid=${kid};
             var f_kid=${f_kid};
             var openid='${openid}';
             var next_name1='${next_name1}';  			 			 			 
        	 $("#surediv").click(function (){
        		 if(flag)
        	 		wx.closeWindow();
        		 document.getElementById("openWinDiv").style.visibility = "hidden";
        	 });
        	$("#linkwifiBtn").click(function(){
        		 if (next_name1==''){
        		//  alert("此探测器已不存在！");
        		$("#openTitlediv").text("此探测器已不存在！");					   
			  		document.getElementById("openWinDiv").style.visibility = "visible";//	
        		    return ;
        		 }
        			 var devName=$("#devName").val();
        	        	
        			// if(devName!=""){
        				 $(this).attr("disabled","disabled");
        				 $.ajax({
      					   type: "POST",
      					   url: "${ctx}/bindDevice.htm?finderRename",
      					   data: "&devName="+devName+"&openid="+openid+"&kid="+kid+"&f_kid="+f_kid,
      					   timeout: 40000,
      					   dataType:'json',
      					   success: function(msg){
      						 if(msg.errcode=="-1"){
      							flag=false;
      							$("#openTitlediv").text("已存在该探测器名!");	
			  					document.getElementById("openWinDiv").style.visibility = "visible";//
			  					$("#linkwifiBtn").removeAttr("disabled");
      						 }else if(msg.errcode=="0"){
      							flag=true;
      							 $("#openTitlediv").text("操作成功!");					   
			  					 document.getElementById("openWinDiv").style.visibility = "visible";//	
      					     }else if(msg.errcode=="1"){
      					    	flag=true;
      					    	 $("#openTitlediv").text("操作失败！请重试");					   
			  					 document.getElementById("openWinDiv").style.visibility = "visible";//	
      					    	 $("#linkwifiBtn").removeAttr("disabled");
      					     }
      					     
      					   },
      					   error:function (XMLHttpRequest, textStatus, errorThrown) {
      						    $("#openTitlediv").text("请求异常！");					   
			  					document.getElementById("openWinDiv").style.visibility = "visible";//
                                $("#linkwifiBtn").removeAttr("disabled");
      					   }

      					});
        			// }
            		
            	 });//end click
        	 
        };
        	 
     </script>
       
  </body>
</html>
