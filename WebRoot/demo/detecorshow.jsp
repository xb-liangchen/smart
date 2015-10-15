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

<div style="height: 4em;width: 100%;overflow:hidden;">
	<div style="height: 3em; width: 100%;float: left;position: relative;left: 3em;text-align: left;top:1em;">
		<div style=""><img width="40%" src="common/images/title_device_list2.png"></div>
	</div>
</div>


<div class="yonghu">
	<div style="height: 3em; width: 100%;float: left;position: relative;left: 3em;text-align: left;top:1em;">
		<div style=""><span style="color: #22BFDC">探测器A</span>&nbsp&nbsp<span style="font-size: 3px;color: #FF9900;">&nbsp</span></div>
		<div><span>添加于2015/01/25</span></div>
	</div>
	<div style="float: right;position: relative;right: 5%;">
		<div style="float: right;"><a data-toggle="collapse" data-parent="#accordion" href="#collapse1"><img ontouchend="change(this)" width="70%" src="common/images/arrow_icon2.png"></a></div>
	</div>
</div>

<div id="collapse1" class="panel-collapse collapse ">
      <div class="panel-body" style="background: url('common/images/edit_menu3.png');height: auto;padding: 12px;background-repeat:no-repeat;background-size:100% 100%; ">
        <div style="float: left;"><a href="#myModal" role="button" class="btn" data-toggle="modal"><span style="color: white;font-size: 20px;position: relative;right: 0%;">重命名</span></a></div>
        <div style="float: right;"><a href="#" role="button" class="btn" data-toggle="modal"><span style="color: white;font-size: 20px;">删除</span></a></div>
      </div>
 </div>

<div class="yonghu">
	<div style="height: 3em; width: 100%;float: left;position: relative;left: 3em;text-align: left;top:1em;">
		<div ><span style="color: #22BFDC">探测器B</span>&nbsp&nbsp<span style="font-size: 3px;color: #FF9900;">&nbsp</span></div>
		<div><span>添加于2015/01/25</span></div>
	</div>
	<div style="float: right;position: relative;right: 5%;">
		<div style="float: right;"><a data-toggle="collapse" data-parent="#accordion" href="#collapse2"><img ontouchend="change(this)" width="70%" src="common/images/arrow_icon2.png"></a></div>
	</div>
</div>

<div id="collapse2" class="panel-collapse collapse ">
      <div class="panel-body" style="background: url('common/images/edit_menu3.png');height: auto;padding: 12px;background-repeat:no-repeat;background-size:100% 100%; ">
        <div style="float: left;"><a href="#myModal" role="button" class="btn" data-toggle="modal"><span style="color: white;font-size: 20px;position: relative;right: 0%;">重命名</span></a></div>
        <div style="float: right;"><a href="#" role="button" class="btn" data-toggle="modal"><span style="color: white;font-size: 20px;">删除</span></a></div>
      </div>
 </div>

<!-- #############################################################model############################### -->


<!-- Modal添加联系人 -->
<div id="myModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="model-my">
	  
	<div class="modal-header phoneDiv">
    	<span class="myfont">请输入新名字</span>
  	</div>
	  
    <div class="modal-body inputDiv">
    	<input type="text" style="height:2.5em;width: 82%;border: 1px solid #22BFDC;"/>
    </div>
	  <div class="modal-footer" >
	     <div style="float: left;text-align: center;width: 50%;"  data-dismiss="modal" aria-hidden="true"><span class="myfont">否</span></div>
	     <div style="float: right;text-align: center;width: 50%;" data-dismiss="modal" ontouchend="suUser()"><span class="myfont">是</span></div>
	  </div>
	</div>
</div>


</body>
</html>