<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common/style.css"/>
<script type="text/javascript">
</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
<div style="max-width: 1045px; margin: 0 auto;">
	<div class="welcome">
        <div class="wel_words">
            <ul class="out_line">
                <span class="in_line">欢迎您 ${SPRING_SECURITY_CONTEXT.authentication.principal.username} ，祝您开心快乐办公每一天</span>
            </ul>
        </div>
    </div>
</div>
</body>
</html>