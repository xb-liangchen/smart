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
<title>大狗卫士</title>
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
	*{padding:0;margin:0;}
	 body{width:100%;text-align:center;font-weight: bold;filter: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#30bde8',endColorStr='#2195de',gradientType='0');}
	 img{border:0}
	 .band2{color: white;font-size:1.5em;position: relative;top:-0.4em;}
	 .yonghu{border-bottom:1px solid   #A9A9A9 ;height:3em;}
	 .divline{display: inline;height: 2em;float: left;position: relative;left: 1em;}
bottom: 86%;}
	</style>
	<script type="text/javascript">
        function addIDCar(){
        	window.location.href="addidcar.jsp"; 
        }
    </script>
</head>
<body>
<div style="height: 42%;">
	<div style="height: 20%;"><h3>你现在要添加身份卡吗？</h3></div>
	<div style="margin: auto;"><span >请确认报警主机处于WIFI联网状态下,并通讯正常。</span></div>
</div>

<div>
	<img ontouchend="addIDCar()" width="50%" src="common/images/button3.png">
</div>

<div style="height: 8em;"></div>
</body>
</html>