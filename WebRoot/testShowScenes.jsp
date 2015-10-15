<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../common/jsp/include_tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx  }/common/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	function showSceneDetail(sceneId) {
		location.href = "${ctx}/showSceneDetail.htm?sceneId=" + sceneId;
	}
</script>

<!-- 基本样式 -->
<style type="text/css">
	html {
		height:100%;
	}
</style>
<title>情景</title>
</head>
<body style="margin: 0; padding: 0; width: 100%; height: 100%;">
	<!-- 这里会传 -->
	<div id="divMain" style="width: 100%; height:100%;margin-top: 2%; text-align: center;">

			<div id="div1" style="height: 20%; widht:background-color: red">
				<div id="div2" style="height: 50%; background-color: green;">
					<div id="div3" style="height: 40%; background-color: blue;">
					</div>
				</div>
			</div>


		<div
			style="width: 48%; height: 10em; text-align: center; line-height: 10em; margin: 1%; position: relative; float: left;">
			<div
				style="float:left;line-height:10em;width:48%;height:123%;background-image: url('${ctx}/common/images/scene/btn2.png');background-repeat:no-repeat;background-size:100% 100%;">
				<img style="padding: 42%;" src="${ctx }/common/images/scene/j.png">
			</div>
			<div
				style="float:right;line-height:10em;width:48%;height:123%;background-image: url('${ctx}/common/images/scene/btn2.png');background-repeat:no-repeat;background-size:100% 100%;">
				<img style="padding: 42%;"
					src="${ctx }/common/images/scene/edit.png">
			</div>
		</div>
	</div>


</body>
</html>