<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${global_app_name}</title>
<%@ include file="/jsp/common/head/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common/style.css"/>
</head>
<body>
	<div class="welcome">
		<div class="wel_words">
			<div class="smile"><img src="${ctx}/css/common/images/smile2.png" /></div>
			<ul class="out_line">
		    	<li>很遗憾，您没有该功能的权限哦，</li>
		    	<li>请联系管理员吧！</li>
	        	<span class="in_line">Study well and make progress everyday!</span>
			</ul>
	    </div>
	</div>
</body>
</html>