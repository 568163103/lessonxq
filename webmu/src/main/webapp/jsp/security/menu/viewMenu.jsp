<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headFormTree.jsp"%>
	<title>${global_app_name}</title>
	<script type="text/javascript">
		$(function() {
			$("#tree").dynatree({
				autoCollapse: true,
				fx: { height: "toggle" },
				children:${json}
			});
		});
	</script>
</head>
<body class="main_bg">
	<div id="tree" style="height: 500" />
</body>
</html>