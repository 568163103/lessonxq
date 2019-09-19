<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/jquery/jquery.ui.accordion.css" />
	<link rel="stylesheet" href="${ctx}/css/jquery/jquery.ui.menu.css" />
	<link rel="stylesheet" href="${ctx}/css/common/style.css"/>
	<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.accordion.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.menu.min.js"></script>
	<script type="text/javascript">
		var rightFrame = null;
		function openPageInIframe(url) {
			if (null == rightFrame){
				rightFrame = window.parent.frames["rightFrame"];
			}
			rightFrame.location.href = url;
		}

		$(function() {
			$( "#left-menu-div" ).accordion({
				heightStyle : "content",
				icons : null
			});
			$( ".menu" ).menu();
		});
	</script>
	<title>${global_app_name}</title>
</head>
<body class="body_bg" style="-webkit-overflow-scrolling:touch;min-width: 0;">
<div id="left-menu-div" style="-webkit-overflow-scrolling:touch;z-index: 100;height: 99%">
	${menuTreeNodes}
</div>
</body>
</html>