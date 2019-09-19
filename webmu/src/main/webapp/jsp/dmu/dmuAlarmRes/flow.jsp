<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headFormTree.jsp"%>
	<title>${global_app_name}</title>
	<script type="text/javascript">
		var url = '${url}';		
		$(function() {
			window.location.href = url;
		})
	</script>
</head>
<body class="main_bg">
	
</body>
</html>