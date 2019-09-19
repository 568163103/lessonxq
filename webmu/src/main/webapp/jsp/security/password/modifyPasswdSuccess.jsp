<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headForm.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common/style.css"/>
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
<div class="list_table">
	<div class="win-header">
		<span class="win-header-title">修改密码 </span>
	</div>
	<div class="win-bodyer">
		<br/><br/>
		<div align="center">修改密码成功，三秒钟后自动跳转到首页。</div>
	</div>
</div>
</body>
</html>