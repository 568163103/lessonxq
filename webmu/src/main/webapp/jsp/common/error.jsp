<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@ include file="/jsp/common/head/head.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${global_app_name}－出错了</title>
</head>
<body class="main_bg">
	<div class="win-header" id="error_title_div">
		<span class="win-header-title">出错了</span>
	</div>
	<div class="win-bodyer">
	<%
		if(null != exception){
			
			out.print(exception.getMessage());
		} else {
	%>
		<center>
		<c:choose>
			<c:when test="${empty ACTIONMESSAGE}">
				页面不存在
			</c:when>
			<c:otherwise>
				${ACTIONMESSAGE}
			</c:otherwise>
		</c:choose>
		</center>
	<%
		}
	%>
	</div>
</body>
</html>