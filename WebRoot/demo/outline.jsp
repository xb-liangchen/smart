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
	 body{width:100%;color:black;background-color:#E7E6E6;text-align:center;font-weight: bold;filter: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#30bde8',endColorStr='#2195de',gradientType='0');}
	 img{border:0}
	 #content1{width:100%;height:9em;padding-top:1.7em;}
	 .band1{font-size:1.5em;}
	 .band2{font-size:1.5em;position: relative;top:-0.4em;}
	 #content2{width:100%;height:18em;}	 
	 #content3{width:100%;height:7em ;}
	</style>
	<script type="text/javascript">
		var openid = '${openid}';
        $(function () {
       	  //获得屏幕的高度,重新定义
       	  var height=document.body.clientHeight;      	
       	  var height1=height*0.24;
       	  var height2=height*0.48;
       	  document.getElementById("content1").style.height=height1;
       	  document.getElementById("content2").style.height=height2;
        });
        
    </script>
</head>
<body>
<div id="content1">
<div class="band1">大狗卫士A</div>
</div>
<div id="content2" >
	<img src="images/big.png">
</div>
<div class="content3"></div>




<div class='container'>
	<h4>我是拟态框的例子</h4>
	<div id='example' class='modal in' style='display:none;'>
	<div class='modal-header'>
	<a class='close' data-dismiss='modal'>X</a>
	<h3>我是拟态框的头部</h3>
	</div>
	<div class='modal-body'>
	<h4>我是拟态框的中间部分</h4>
	<p>我是描述部分</p>
	</div>
	<div class='modal-footer'>
	<a href='#' class='btn btn-success'>成功</a>
	<a href='#' class='btn' data-dismiss='modal'>关闭</a>
	</div>
	</div>
	<p>
	<a href='#example' data-toggle='modal' class='btn btn-primary btn-large'>点我弹出拟态框</a>
	</p>
</div>




    <a href="#myModal" role="button" class="btn" data-toggle="modal">查看演示案例</a>

 

<!-- Modal -->

<div id="myModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

  <div class="modal-header">

    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>

    <h3 id="myModalLabel">Modal header</h3>

  </div>

  <div class="modal-body">

    <p>One fine body…</p>

  </div>

  <div class="modal-footer">

    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>

    <button class="btn btn-primary">Save changes</button>

  </div>

</div>
</body>
</html>