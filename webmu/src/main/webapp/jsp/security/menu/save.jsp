<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headForm.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
$(document).ready(function(){
    $("#pageForm").validationEngine();
});
</script>
</head>
<body class="main_bg">
	<form id="pageForm" name="pageForm" action="${ctx}/security/menu!save.do" method="post"> 
		<label>上级节点ID:</label>
			<input type="text" name="menu.preid" id="preid" class="validate[required]"><br/><br/>
		<label>菜单名称:</label>
			<input type="text" name="menu.name" id="name" class="validate[required]"><br/><br/>
		><label>菜单连接:</label>
			<input type="text" name="menu.url" id="url" value="http://" class="validate[required,custom[url]]"><br/><br/>
		<label>菜单层级:</label>
			<input type="text" name="menu.lel" id="lel" class="validate[required]"><br/><br/>
		<label>操作标识:</label>
			<input type="text" name="menu.dmlflag" id="dmlflag" class="validate[required]"><br/><br/>
		<label>操作时间:</label>
			<input type="text" name="menu.dmltime" id="dmltime" class="validate[custom[date]"><br/><br/>
		<label>是否功能页面:</label>
			<input type="text" name="menu.isFunc" id="isFunc" class="validate[required]"><br/><br/>
		<label> 状态:</label>
			<input type="text" name="menu.status" id="status" class="validate[required]"><br/><br/>
			<input type="submit" value="保存菜单"/>
			<input type="button" value="返回" onclick="location.href='${ctx}/security/menu!findPage.do'"/>
	</form>
</body>
</html>