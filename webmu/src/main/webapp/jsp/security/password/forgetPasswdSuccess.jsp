<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headForm.jsp"%>
<title>${global_app_name}</title>
	<script type="text/javascript">
		$(document).ready(function(){
		    setTimeout(function () {
				window.location.href="../../../";
			}, 3000);
		});
	</script>
</head>
<body class="main_bg">
	<div align="center">信息已发送到邮箱中，请注意查收邮件，三秒钟后自动跳转到首页。</div>
</body>
</html>