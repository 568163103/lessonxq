<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<style>
		#rightArrow{width:15px;height:90px;background:url(${ctx}/images/frame_arrow.jpg) no-repeat;position:fixed;top:40%;z-index:999;cursor: pointer;}
	</style>
	<script type="text/javascript">
		$(function () {
			var hiddenLeft = false;
			$("#rightArrow").click(function (){
				if(hiddenLeft){
					top.mainFrameSet.cols="149,10,*";
					$(this).css('background-position','0 0');
				} else {
					top.mainFrameSet.cols="0,10,*";
					$(this).css('background-position','-15px 0');
				}
				hiddenLeft = !hiddenLeft;
			});
		})
	</script>
	<title>${global_app_name}</title>
</head>
<body class="body_bg">
	<div id="rightArrow"/>
</body>
</html>
