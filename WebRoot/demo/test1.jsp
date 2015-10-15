<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/jsp/include_tags.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>大狗卫士用户</title>
 <%@include file="../common/jsp/include_css.jsp" %>
<link href="${ctx  }/common/css/web/user/usermanager.css" type="text/css" rel="stylesheet" />
	
<style type="text/css">

</style>
	<script type="text/javascript">
	
	   function showHienBtn(key)
	   {   
		   var att=$("#collapse_"+key).attr("class");
		   var ind=att.indexOf("collapse");
		   if(ind>0){
			   $("#collapse_"+key).removeClass();//显示
		   }else{
			   $("#collapse_"+key).addClass("panel-collapse collapse");//隐藏
		   }
		   //$("#collapse_"+key).addClass("panel-collapse collapse");
	   }
       function addWxUser(){
    	   alert("添加微信用户 -扫描二维码");
       }
       function addNoWxUser(){
	       	$("#title_user").text("无微信用户");
	       	$("#userflag").val(1);
	       	$("#popwin-edituser").css("visibility","visible");
       }
       
       function addEnergency(){
    	   $("#title_user").text("紧急联系人");
    	   $("#userflag").val(2);
	       $("#popwin-edituser").css("visibility","visible");
       }
        
        var title="";
        //点击添加 修改电话号码
        function showPhoneDiv(val){
        	if(val==''){
        		title="添加";
        	}else{
        		title="修改";
        	}
        	$("#title_phone").text(title);
        	$("#userphone_inpt").val(val);
        	$("#popwin-editPhone").css("visibility","visible");
        }
        //保存联系电话
        function savePhoneBtn(){
        	
        	$("#popwin-editPhone").css("visibility","hidden");
        }
        //取消保存联系电话
        function closePopPhoneBtn(){
        	
        	$("#popwin-editPhone").css("visibility","hidden");
        }
        
        //保存用户
        function saveUserBtn(){
        	
        	$("#popwin-edituser").css("visibility","hidden");
        }
        //关闭用户
        function closePopUserBtn(){
        	
        	$("#popwin-edituser").css("visibility","hidden");
        }
        
        //点击删除用户
        function deleteUserDiv(){
        	$("#deleuserWin").css("visibility","visible");
        }
        function sureDeleteUser(){
        	$("#deleuserWin").css("visibility","hidden");
        } 
        function closeDeluserWin(){
        	$("#deleuserWin").css("visibility","hidden");
        }
    </script>
</head>
<body>
<!-- -----------有微信用户------------ -->
<div id="content3" >
	<span class="title_user1">有微信用户</span>
	<div class="title_user2">
	<img width="80%" onclick="addWxUser()" height="80%" src="${ctx }/common/images/add_icon.png" />
	</div>
</div>

<!---------- 用户-------------->
<div class="yonghu">
	<div class="divline">
	<div style="float: left;position: relative;top:30%;"><img class="headicon1" src="${ctx }/common/images/headicon.jpg"></div>
	<div class="imgDiv1">
		<div><span class="title_user">用户A&nbsp;</span><span class="manage_flag">管理员</span></div>
		<div><span class="title_phone">12345678910</span></div>
		<div><span class="color1 font-size10">添加于2015/01/25</span></div>
	</div>
	</div>
	<div style="float: right;position: relative;right: 7%;bottom:60%;">
		<div class="float2" ><a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><img  onclick="showHienBtn(1)" width="70%" src="${ctx }/common/images/arrow_icon-2.png"></a></div>
		<div  class="float2" ><img width="70%" src="${ctx }/common/images/icon-d.png"></div>
		<div  class="float2" ><img width="70%" src="${ctx }/common/images/icon-d2.png"></div>
	</div>
</div>
		<!--隐藏操作按钮begin -->
		 <div id="collapse_1" class="panel-collapse collapse ">
		      <div class="panel-body height3"  style="background: url('${ctx }/common/images/edit_menu.png');padding: 12px;background-repeat:no-repeat;background-size:100% 100%; ">
		        <div class="float1 display-inline line-height-2 margin-top2"><a href="javascript:showPhoneDiv('1314322121');" role="button" class="btn user-hiden-left" data-toggle="modal">添加电话</a></div>
		        <div class=" text-align2 margin-top2 line-height-2 margin-left2" ><a href="#" role="button" class="btn user-hiden-center" data-toggle="modal">解除身份卡</a></div>
		        <div  class="float2 text-align3 display-inline line-height-2 margin-top3" ><a href="javascript:deleteUserDiv();"  class="btn" data-toggle="modal" style="color: white">删除</a></div>
		      </div>
		 </div>
		<!--隐藏操作按钮end-->
<!---------- 用户end---- -->
	
	
<!-- -----------无微信用户------------ -->
<div id="content3" >
	<span class="title_user1">无微信用户</span>
	<div class="title_user2">
	<img width="80%" height="80%" onclick="addNoWxUser()" src="${ctx }/common/images/add_icon.png" />
	</div>
</div>

<!---------- 用户-------------->
<div class="yonghu">
	<div class="divline">
	<div style="float: left;position: relative;top:30%;"><img class="headicon1" src="${ctx }/common/images/headicon.jpg"></div>
	<div class="imgDiv1">
		<div><span class="title_user">用户A&nbsp;</span></div>
		<div><span class="title_phone">未添加</span></div>
		<div><span class="color1 font-size10">添加于2015/01/25</span></div>
	</div>
	</div>
	<div style="float: right;position: relative;right: 7%;bottom:60%;">
		<div class="float2" ><a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><img onclick="showHienBtn(2)" width="70%" src="${ctx }/common/images/arrow_icon-2.png"></a></div>
		<div  class="float2" style="width: 30px">&nbsp;&nbsp;&nbsp;&nbsp;</div>
		<div  class="float2" ><img width="70%" src="${ctx }/common/images/icon-d2.png"></div>
	</div>
</div>
		<!--隐藏操作按钮begin -->
		 <div id="collapse_2" class="panel-collapse collapse ">
		      <div class="panel-body height3"  style="background: url('${ctx }/common/images/edit_menu.png');padding: 12px;background-repeat:no-repeat;background-size:100% 100%; ">
		        <div class="float1 display-inline line-height-2 margin-top2"><a href="javascript:showPhoneDiv('1314322121');" role="button" class="btn user-hiden-left" data-toggle="modal">添加电话</a></div>
		        <div class=" text-align2 margin-top2 line-height-2 margin-left2" ><a href="#" role="button" class="btn user-hiden-center" data-toggle="modal">解除身份卡</a></div>
		        <div  class="float2 text-align3 display-inline line-height-2 margin-top3" ><a href="javascript:deleteUserDiv();"  class="btn" data-toggle="modal" style="color: white">删除</a></div>
		      </div>
		 </div>
		<!--隐藏操作按钮end-->
<!---------- 用户end---- -->
	
	
	
	
<!-- -----------紧急联系人------------ -->
<div id="content3" >
	<span class="title_user1">紧急联系人</span>
	<div class="title_user2">
	<img width="80%" height="80%" onclick="addEnergency()" src="${ctx }/common/images/add_icon.png" />
	</div>
</div>

<!---------- 用户-------------->
<div class="yonghu">
	<div class="divline">
	<div style="float: left;position: relative;top:30%;"><img class="headicon1" src="${ctx }/common/images/headicon.jpg"></div>
	<div class="imgDiv1">
		<div><span class="title_user">用户A&nbsp;</span></div>
		<div><span class="title_phone">未添加</span></div>
		<div><span class="color1 font-size10">添加于2015/01/25</span></div>
	</div>
	</div>
	<div style="float: right;position: relative;right: 7%;bottom:60%;">
		<div class="float2" ><a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><img onclick="showHienBtn(3)" width="70%" src="${ctx }/common/images/arrow_icon-2.png"></a></div>
	</div>
	</div>
		<!--隐藏操作按钮begin -->
		 <div id="collapse_3" class="panel-collapse collapse ">
		      <div class="panel-body height3"  style="background: url('${ctx }/common/images/edit_menu.png');padding: 12px;background-repeat:no-repeat;background-size:100% 100%; ">
		        <div class="float1 display-inline line-height-2 margin-top2"><a href="javascript:showPhoneDiv('1314322121');" role="button" class="btn user-hiden-left" data-toggle="modal">添加电话</a></div>
		        <div class=" text-align2 margin-top2 line-height-2 margin-left2" ><a href="#" role="button" class="btn user-hiden-center" data-toggle="modal">解除身份卡</a></div>
		        <div  class="float2 text-align3 display-inline line-height-2 margin-top3" ><a href="javascript:deleteUserDiv();"  class="btn" data-toggle="modal" style="color: white">删除</a></div>
		      </div>
		 </div>
		<!--隐藏操作按钮end-->
<!---------- 用户end---- -->
<!-- ####################################################模板窗口############################################################################ -->	
	
	
<!-- Modal删除用户 -->
<div id="deleuserWin"
		style="position: fixed;top: 0;z-index: 999;width: 100%;height: 100%;background: rgba(8, 8, 8, 0.7);visibility: hidden;">
	<div class="model-my">
	  <div class="modal-body">
	    <span class="myfont">是否删除用户</span>
	  </div>
	  <div class="modal-footer">
	     <div style="float: left;text-align: center;width: 50%;"  data-dismiss="modal" aria-hidden="true" onclick="closeDeluserWin()"><span class="myfont">否</span></div>
	     <div style="float: right;text-align: center;width: 50%;"  data-dismiss="modal" ontouchend="sureDeleteUser()"><span class="myfont">是</span></div>
	  </div>
	</div>
</div>
<!-- 弹出窗口--添加用户 -->
<div id="popwin-edituser"
		style="position: fixed;top: 0;z-index: 999;width: 100%;height: 100%;background: rgba(8, 8, 8, 0.7);visibility: hidden;">
	<div class="model-my">
	  
	<div class="modal-header phoneDiv">
    	<span class="myfont">添加<span id="title_user"></span></span>
  	</div>
	  
    <div class="modal-body inputDiv line-height-2" style="text-align: center;padding: 0px;">
    	<input id="name_inpt" placeholder="请输入昵称" type="text" class="" style="width: 89%;border: 1px solid #22BFDC;font-size: 1.5em;font-weight: normal;"/>
    </div>
     <div class="modal-body inputDiv line-height-2" style="text-align: center;padding: 0px;top: 1em;">
        <input type="hidden" id="userflag">
    	<input id="phone_inpt" placeholder="请输入联系电话" type="text" class="" style="width: 89%;border: 1px solid #22BFDC;font-size: 1.2em;font-weight: normal;"/>
    </div>
	  <div class="modal-footer" style="margin-top: 2em;border-top: 1px solid #EAEAEA;">
	     <div style="float: left;text-align: center;width: 50%;"  data-dismiss="modal" aria-hidden="true" onclick="closePopUserBtn()"><span class="myfont">否</span></div>
	     <div style="float: right;text-align: center;width: 50%;" data-dismiss="modal" onclick="saveUserBtn()"><span class="myfont">是</span></div>
	  </div>
	</div>
</div>
<!-- end 弹出窗口 添加用户 -->
<!-- 弹出窗口--添加手机号码 -->
<div id="popwin-editPhone"
		style="position: fixed;top: 0;z-index: 999;width: 100%;height: 100%;background: rgba(8, 8, 8, 0.7);visibility: hidden;">
	<div class="model-my">
	  
	<div class="modal-header phoneDiv">
    	<span class="myfont">请<span id="title_phone"></span>联系方式</span>
  	</div>
	  
    <div class="modal-body inputDiv line-height-2" style="text-align: center;">
    	<input id="userphone_inpt" placeholder="请输入联系电话" type="text" style="width: 89%;border: 1px solid #22BFDC;font-size: 1.5em;font-weight: normal;"/>
    </div>
	  <div class="modal-footer" style="margin-top: 0px;border-top: 1px solid #EAEAEA;">
	     <div style="float: left;text-align: center;width: 50%;"  data-dismiss="modal" aria-hidden="true" onclick="closePopPhoneBtn()"><span class="myfont">否</span></div>
	     <div style="float: right;text-align: center;width: 50%;" data-dismiss="modal" onclick="savePhoneBtn()"><span class="myfont">是</span></div>
	  </div>
	</div>
</div>
<!-- end 弹出窗口 添加手机号码 -->

 <%@include file="../common/jsp/include_js.jsp" %>
</body>
</html>