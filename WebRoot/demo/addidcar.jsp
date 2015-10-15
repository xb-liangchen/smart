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
	 .persent{  float: left;
  position: relative;
  left: 30%;
  bottom: 10em;}
bottom: 86%;}
	</style>
	<script type="text/javascript">
        
    </script>
</head>
<body>
<div style="height: 100%;line-height:5em;">
	<div><span >请将身份卡按钮长按5秒直至蓝色指示灯亮起。</span></div>
	<div style="background: url('common/images/image-53.png')no-repeat;background-size:100% 100%;height: 40%;" >
		<span style="color: #22BFDC;font-size: 1.5em;">身份卡A</span>
	</div>
	<div style="height: 34%;">
		<div><img width="60%" src="common/images/study-55.png"></div>
		<div class="persent">
			<span style="font-size: 5em;color: #33CCFF">100</span>
			<span style="font-size: 3em;">%</span>
		</div>
	</div>
</div>
</body>
</html>