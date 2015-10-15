<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("path", path);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自动布防设置</title>
<base href="<%=basePath%>">

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	
    <!-- bootstrap -->
    <link href="${path  }/css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="${path  }/css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

	<!-- scripts -->
    <script type="text/javascript" src="${path }/js/jquery/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="${path }/js/bootstrap/bootstrap.min.js"></script>
	
	<style type="text/css">
.yonghu {
	border-bottom: 1px solid #A9A9A9;
	height: 5em;
	width: 100%;
	overflow: hidden;
}

.conten1 {
	background: -moz-linear-gradient(top, #30bde8 #2195de);
	background: -o-linear-gradient(top, #30bde8, #2195de);
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#30bde8),
		to(#2195de) );
	text-align: center;
}
</style>
	<script type="text/javascript">
		 $(function () {
       	  //获得屏幕的高度,重新定义
       	  var height=document.body.clientHeight;      	
       	  var height3=height*0.7;
       	  var height2=height*0.82;
       	  document.getElementById("center1").style.height=height2;
       	  document.getElementById("top1").style.height=height3;
        });
        
    </script>
</head>
<body>
<div id="top1">
<div class="yonghu">
	<div style="float: left;height: 3em;width: 50%;position: relative;top:30%;text-align: left;">&nbsp&nbsp&nbsp&nbsp<span style="font-size: 1.5em;">自动布防</span></div>
	<div style="float: right;height: 3em;width: 50%;position: relative;top:30%;">
		<div style="float: right;width: 8em;">&nbsp&nbsp&nbsp&nbsp<img width="50%" src="common/images/switch_on.png"></div>
	</div>
</div>
<div class="conten1" id="center1">
	<div style="height: 4em;"></div>
	<div><img width="70%" src="common/images/lock-50.png"></div>
	<div><span style="color: white;font-size: 1.5em;">自动布防时段</span></div>
	<div><span style="color: white;font-size: 1.5em;">10:00-18:30</span></div>
</div>

<div style="background-color: black;height: 10%;">
	<div><span style="color: white;">保存</span></div>
</div>
</div>




</body>
</html>