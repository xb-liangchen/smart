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
	 #content1{width:100%;height:9em;padding-top:2.7em;}
	 .band2{color: white;font-size:1.5em;position: relative;top:-0.4em;}
	 #content2{width:100%;height:18em;}	 
	 #content3{width:100%;height:2em ;background-color:   #D3D3D3;border-bottom:1px solid   #A9A9A9 ; }
	 .menu1{margin:0 auto;width:80%;height:2.9em;line-height:2.9em;background:url('images/wechat/button-07.png') no-repeat top center;background-size:100% 2.8em;overflow:hidden;}
	 .menu2{margin:0 auto;width:80%;height:4.9em;line-height:4.9em;background:url('images/wechat/button-07.png') no-repeat center;background-size:100% 2.8em;overflow:hidden;}
	 .content3button{border:0}
	 .con-top{background: -moz-linear-gradient(top, #30bde8 #2195de);background: -o-linear-gradient(top,#30bde8, #2195de);background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#30bde8), to(#2195de));}
	 .img-bufang{}
	 .img-log{position: relative;right: 35%;}
	 .yonghu{border-bottom:1px solid   #A9A9A9 ;height:3em;}
	 .divline{display: inline;height: 2em;float: left;position: relative;left: 1em;}
	 .divtan{  background: url('common/images/alert_box2.png')no-repeat;
  height: 100%;
  width: 80%;
  background-size: 80%;
  position: relative;
  float: left;
  left: 18%;}
bottom: 86%;}
	</style>
	<script type="text/javascript">
		var openid = '${openid}';
        $(function () {
       	  //获得屏幕的高度,重新定义
       	  var height=document.body.clientHeight;      	
       	  var height1=height*0.2;
       	  var height2=height*0.5;
       	  var height3=height*0.7;
       	  document.getElementById("content1").style.height=height1;
       	  document.getElementById("content2").style.height=height2;
       	  document.getElementById("top1").style.height=height3;
       	 
        });
        
        function noFang(){
        	$("#tipsDiv").css("display","none");
        }
        
        function chefang(){
        	$("#tipsDiv").css("display","");
        }
        
        function sucFang(){
        	$("#tipsDiv").css("display","none");
        	$("#yesDiv").css("display","");
        	 setTimeout("yinc()",2000);
        }
        function yinc(){
        	$("#yesDiv").css("display","none");
        }
    </script>
</head>
<body>
<div class="con-top" id="top1">
<div id="content1">
<div class="band2">我家大狗卫士</div>
<a><img style="position: relative;right: 27%" width="18%" src="common/images/log.png"/></a>
<img  ontouchend="chefang()" style="position: relative;left: 26%" width="18%" src="common/images/chefang2.png"/>

</div>
<div id="content2"><div style="height:90%"><img width="65%" src="common/images/lock-51.png"><div ><span style="color: #00FFFF;">当前状态</span></div></div>

<div><span style="color: white;font-size: 15px">自动布防时段:&nbsp</span><span style="color: yellow;">10:00-18：00</span></div>

</div>

</div>

<div id="content3"><span style="color: #808080;position: relative;right: 30% ">家庭成员状态</span></div>

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



 <!-- 提示框 -->
	<div id="tipsDiv" style="display: none;position: fixed;top: 0;z-index: 999;width: 100%;height: 100%;">
		<div style="position: relative;top:40%;height: 60%;">
			<div class="divtan">
				<div ontouchend="noFang()" style="position: relative;float: left;top:20%;left: 15%;"><span style="color: #22BFDC;font-size: 2em;">否</span></div>
				<div ontouchend="sucFang()" style="position: relative;float: right;right: 35%;top:20%;"><span style="color: #22BFDC;font-size: 2em;">是</span></div>
			</div>
		</div>
	</div>
	
	<!-- 提示框 -->
	<div id="yesDiv" style="display: none;position: fixed;top: 0;z-index: 999;width: 100%;height: 100%;">
		<div style="position: relative;top:55%;height: 60%;background: url('common/images/tips_bg2.png')no-repeat;background-size: 30%;left: 35%;">
		</div>
	</div>

</body>
</html>