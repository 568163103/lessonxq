<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
		"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${global_app_name}</title>
	<script type="text/javascript">
		top.location.href = '${"/" eq pageContext.request.contextPath ? "" : pageContext.request.contextPath}${param.url}';
	</script>
</head>
<body>
</body>
</html>