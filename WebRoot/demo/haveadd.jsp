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
	body{background-color: #d3d3d3;}
.yonghu {
	
	height: 3.5em;
	width: 100%;
	overflow: hidden;
	background-color: white;
}
.conten1{
text-align: center;
color: #22BFDC;
}
.myfont{
	color: #22BFDC;font-size: 1.5em
}
.myinput {
	height: 2.5em;
  width: 100%;
  border: 1px solid #22BFDC;
  background-color: #d3d3d3;
  border-radius: 8px;
  line-height:normal;
}
.top1{height: 4em;width: 100%;overflow:hidden;background-color: white;}
.fontDiv{height: 3em; width: 82%;float: left;position: relative;left: 3em;text-align: left;top:1em;border-bottom: 8px solid #a9a9a9;}
</style>
	<script type="text/javascript">
		
        
    </script>
</head>
<body>

<div class="top1">
	<div style="height: 3em; width: 100%;float: left;position: relative;left: 3em;text-align: left;top:1em;">
		<div style=""><img width="40%" src="common/images/title_device_list3.png"></div>
	</div>
</div>


<div class="yonghu">
	<div class="fontDiv">
		<div style="float: left;"><span class="myfont">探测器A</span>&nbsp&nbsp<span style="font-size: 3px;color: #FF9900;">&nbsp</span></div>
		<div style="float: right;"><span>添加于2015/01/25</span></div>
	</div>
</div>
<div class="yonghu">
	<div class="fontDiv">
		<div style="float: left;"><span class="myfont">探测器B</span>&nbsp&nbsp<span style="font-size: 3px;color: #FF9900;">&nbsp</span></div>
		<div style="float: right;"><span>添加于2015/01/25</span></div>
	</div>
</div>
<div class="yonghu">
	<div class="fontDiv">
		<div style="float: left;"><span class="myfont">探测器C</span>&nbsp&nbsp<span style="font-size: 3px;color: #FF9900;">&nbsp</span></div>
		<div style="float: right;"><span>添加于2015/01/25</span></div>
	</div>
</div>
<div class="yonghu">
	<div class="fontDiv">
		<div style="float: left;"><span class="myfont">探测器D</span>&nbsp&nbsp<span style="font-size: 3px;color: #FF9900;">&nbsp</span></div>
		<div style="float: right;"><span>添加于2015/01/25</span></div>
	</div>
</div>

<div style="background-color: white;height: 2em;"></div>

<div class="conten1">
	<div>
		<div><h3>报警主机与探测器绑定成功</h3></div>
		<div style="height: 28%;">
		<div style="float: left;position: relative;left:  10%;color: black;width: 80%;height: 16%;line-height: 3em;">
			<div style="width: 100%;text-align: left;"><span>为此探测器命名</span></div>
			<div style="width: 100%;text-align: left;"><input type="text" class="myinput"></div>
		</div>
		</div>
		<div><img width="50%" src="common/images/buttonok.png"></div>
	</div>
</div>

<div>
	<form action="${path }/dog.htm">
		<button type="submit">提交</button>
	</form>
</div>


</body>
</html>