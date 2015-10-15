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
<title>设置闹钟</title>
<base href="<%=basePath%>">

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	
    <!-- bootstrap -->
    <link href="${path  }/css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="${path  }/css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />
    <link href="${path  }/common/css/usermanager.css" type="text/css" rel="stylesheet" />

	<!-- scripts -->
    <script type="text/javascript" src="${path }/js/jquery/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="${path }/js/bootstrap/bootstrap.min.js"></script>
	
	<style type="text/css">

</style>
	<script type="text/javascript">
		
        
    </script>
</head>
<body>

<div class="yonghu">
	<div style="float: left;height: 3em;width: 50%;position: relative;top:30%;text-align: left;">&nbsp&nbsp&nbsp&nbsp<span style="font-size: 1.5em;color: #22BFDC">全部</span></div>
	<div style="float: right;height: 3em;width: 50%;position: relative;top:30%;">
		<div style="float: right;width: 8em;"><img width="50%" src="common/images/switch_off.png"></div>
	</div>
</div>

<div class="yonghu">
	<div style="float: left;height: 3em;width: 50%;position: relative;top:30%;text-align: left;">&nbsp&nbsp&nbsp&nbsp<span style="font-size: 1.5em;color: #22BFDC">06:00</span></div>
	<div style="float: right;height: 3em;width: 50%;position: relative;top:30%;">
		<div style="float: right;width: 8em;"><img width="50%" src="common/images/switch_on.png"></div>
	</div>
</div>

<div class="yonghu">
	<div style="float: left;height: 3em;width: 50%;position: relative;top:30%;text-align: left;">&nbsp&nbsp&nbsp&nbsp<span style="font-size: 1.5em;color: #22BFDC">07:00</span></div>
	<div style="float: right;height: 3em;width: 50%;position: relative;top:30%;">
		<div style="float: right;width: 8em;"><img width="50%" src="common/images/switch_off.png"></div>
	</div>
</div>



</body>
</html>