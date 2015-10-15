<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../../../common/jsp/include_tags.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>设备列表</title>
<style type="text/css">
.deviceOne{
	border-bottom: 1px solid #A9A9A9;
	height: 5em;
	width: 100%;
	overflow: hidden;
	margin-top: 1em;
}
</style>
<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
function deleteWifi(did,openid){
	   if(confirm("是否删除该设备？")){
		   $.ajax({
			   type: "POST",
			   url: "${ctx}/deleteWifi.htm",
			   data: {"did":did,"openid":openid},
			   timeout: 40000,
			   dataType: "json", 
			   success: function(data){ 
				   if(data.errmsg=='ok'){
						$("#device_a_"+did).remove();
					}else{
						alert("数据错误！");
					}
			   },
			   error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert("数据错误！");
				 }
			   });
	   }
}
function editWifi(did,dname){
	$("#did").val(did);
	$("#dname").val(dname);
	$("#dnameOld").val(dname);
	document.getElementById("openWinDiv").style.visibility = "visible";
}
window.onload=function() {
	$("#canclediv").click(function (){
		  document.getElementById("openWinDiv").style.visibility = "hidden";
	    
	});
	$("#surediv").click(function (){
		var did = $("#did").val();
		var dname = $("#dname").val();
		var dnameOld = $("#dnameOld").val();
		if(dname==dnameOld || dname.length==0){ 
			  document.getElementById("openWinDiv").style.visibility = "hidden";
		}else{
			 $.ajax({
				   type: "POST",
				   url: "${ctx}/modifyWifiName.htm",
				   data: {"did":did,"dname":dname},
				   timeout: 40000,
				   dataType: "json", 
				   success: function(data){ 
					   if(data.errmsg=='ok'){
						   $("#device_name_"+did).html(dname);
						   document.getElementById("openWinDiv").style.visibility = "hidden";
						}else{
							alert("数据错误！");
						}
				   },
				   error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert("数据错误！");
					 }
				   });
		}
	});

	$(".deviceOne").each(function(){
		var startX,startY,endX,endY;
		$(this)[0].addEventListener("touchstart",touchStart,false);
		$(this)[0].addEventListener("touchmove",touchMove,false);
		$(this)[0].addEventListener("touchend",touchEnd,false);
		$(this)[0].addEventListener("click",nextPage,false);
		
		function touchStart(event){
			var touch = event.touches[0];
			startX = touch.pageX;
			startY = touch.pageY;
		}
		function touchMove(event){
			var touch = event.touches[0];
			endX = (startX-touch.pageX);
			endY = (startY-touch.pageY);
		//	alert(endX);alert(endY);
			if(endX<=-11 && endY>=-12 && endY<=12){
				//右滑
				var id = $(this).attr("id");
				$("#device_button_"+id).hide();
			}
			if(endX>=11 && endY>=-12 && endY<=12){
				//左滑
				var id = $(this).attr("id");
				$("#device_button_"+id).show();
			}
		}
		function touchEnd(event){
		}

		function nextPage(event){
			var id = $(this).attr("id");
			location.href="${ctx}/lightList.htm?fogDid="+id+"&openid=${openid }";
		}
		});
	
}
</script>
</head>
<body style=" margin:0; padding:0;">
<c:forEach var="sysDevice" items="${sd }">
<div id="device_a_${sysDevice.id }" >
	<div class="deviceOne" id="${sysDevice.fogDid }">
		<div style="float: left;margin-left: 1em;">
			<c:choose>
				<c:when test="${1 eq sysDevice.isOnline }">
					 <img  src="${ctx }/common/images/online.png" /> 
				</c:when>
				<c:otherwise>
					<img  src="${ctx }/common/images/offline.png" /> 
				</c:otherwise>
			</c:choose>
		</div>
		<div style="float:left;padding-left: 1em;">
			<span id="device_name_${sysDevice.id }" style="font-size: 1.5em;position: relative;top: 0.4em;"> ${sysDevice.name }</span><br/>
			<span style="font-size: 1.3em;position: relative;top: 0.7em;color: #C5CAD0;"> ID: ${sysDevice.id }</span>
		</div>
	</div>	

	<div style="border-bottom: 1px solid #A9A9A9;height: 5em;width: 100%;overflow: hidden;display: none;" id="device_button_${sysDevice.id }">
		<div style="float: right;">
			 <img height="100%"  style="float:right;padding: 0;margin: 0;" onclick="deleteWifi('${sysDevice.id}','${openid }')" src="${ctx }/common/images/deleteWifi.png" /> 
			 <img height="100%"   style="float:right;padding: 0;margin: 0;" onclick="editWifi('${sysDevice.id}','${sysDevice.name }')"  src="${ctx }/common/images/editWifi.png" /> 
		 </div>
	</div>
</div>
</c:forEach>

<!-- 提示框 -->
	<div id="openWinDiv" style="position: fixed;top: 0;z-index: 999;width: 100%;height: 100%;background: rgba(8, 8, 8, 0.7); visibility: hidden;">
		<div style="position: relative;top:30%;height: 60%;text-align: center;">
		      <div class="font-size18" id="popwindows1" style="width: 90%;background: #ffffff no-repeat;border-radius: 25px; position:absolute;margin: 0 auto;left:5%;">
		           <div id="openTitlediv" style="height: 4em;line-height: 4em;font-weight:bold;">编辑</div>
		           <div id="inputdiv" style="border-bottom: 1px solid #EAEAEA;height:5em;">
		           		<input id="dname" type="text" style="width:90%;height:70%;font-size: 100%"/>
		           		<input id="dnameOld" type="hidden"/>
		           		<input id="did"  type="hidden" />
		           	</div>
		           <div style="line-height: 3em;"> 
			           <div id="canclediv"  style="text-align: center;float: left;width: 49%;display: inline;cursor: pointer;border-right: 1px solid #e3e3e3;">取消</div> 
			           <div id="surediv"   style="text-align: center;display: inline;width: 50%;float: right;cursor: pointer;">确定</div>
		           </div>
		      </div>
		 </div>
	</div>

</body>
</html>