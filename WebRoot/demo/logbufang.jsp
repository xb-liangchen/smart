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
	 .con-top{background: -moz-linear-gradient(top, #30bde8 #2195de);background: -o-linear-gradient(top,#30bde8, #2195de);background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#30bde8), to(#2195de));}
	 .yonghu{border-bottom:1px solid   #A9A9A9 ;height:3em;}
	 .divline{display: inline;height: 2em;float: left;position: relative;left: 1em;}
bottom: 86%;}
	</style>
	<script type="text/javascript">
		var openid = '${openid}';
        $(function () {
       	  //获得屏幕的高度,重新定义
       	  var height=document.body.clientHeight;      	
       	  var height2=height*0.1;
       	  var height3=height*0.4;
       	  document.getElementById("content2").style.height=height2;
       	  document.getElementById("top1").style.height=height3;
       	 
        });
        
    </script>
</head>
<body>
<div class="con-top" id="top1">

<div id="content2"><div style="height:40%"><img width="65%" src="common/images/lock-51.png"></div>

</div>

</div>

<div class="yonghu">
	<div class="divline"><img src="common/images/icon-12.png"><span>用户A</span></div><div style="float: right;position: relative;right: 2em;top:1em;"><span>在家</span></div>
</div>
<div class="yonghu">
	<div class="divline"><img src="common/images/icon-12.png"><span>用户B</span></div><div style="float: right;position: relative;right: 2em;top:1em;"><span>在家</span></div>
</div>
<div class="yonghu">
	<div class="divline"><img src="common/images/icon-12.png"><span>用户C</span></div><div style="float: right;position: relative;right: 2em;top:1em;"><span>在家</span></div>
</div>
<div class="yonghu">
	<div class="divline"><img src="common/images/icon-12.png"><span>用户D</span></div><div style="float: right;position: relative;right: 2em;top:1em;"><span>在家</span></div>
</div>
<div class="yonghu">
	<div class="divline"><img src="common/images/icon-12.png"><span>用户E</span></div><div style="float: right;position: relative;right: 2em;top:1em;"><span>在家</span></div>
</div>
<div class="yonghu">
	<div class="divline"><img src="common/images/icon-12.png"><span>用户F</span></div><div style="float: right;position: relative;right: 2em;top:1em;"><span>在家</span></div>
</div>
<div class="yonghu">
	<div class="divline"><img src="common/images/icon-12.png"><span>用户R</span></div><div style="float: right;position: relative;right: 2em;top:1em;"><span>在家</span></div>
</div>

<div style="height: 8em;"></div>

<div>
	<img width="50%" src="common/images/button2.png">
</div>

<div style="height: 8em;"></div>
</body>
</html>