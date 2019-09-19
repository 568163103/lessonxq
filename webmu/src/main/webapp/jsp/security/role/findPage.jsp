	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	function checkedItems() {
		var flag = false;
		$("#pageForm").find("[name=items]").each(function() {
			if (this.checked) {
				flag = true;
			}
		});
		return flag;
	}
	function deleteAll() {
		if (!checkedItems()) {
			alert("要删除角色，请至少选择其中一个。");
			return false;
		}
		msg = "确认要删除所选角色吗？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/security/role!delete.do");
		}
		$("#pageForm").submit();
	}
	function deleteOne(rid) {
		$("[name=items]").val(rid);
		var msg = "确认要删除所选角色吗？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/security/role!delete.do");
		}
		$("#pageForm").submit();
	}
	function editRole(rid) {
		$("#pageForm").find("[name=rid]").val(rid);
		$("#pageForm").attr("action","${ctx}/security/role!beforeUpdate.do");
		$("#pageForm").submit();
	}
	function beforeAuthForRole(rid,rolename) {
		$("#pageForm").find("[name=rid]").val(rid);
		$("#pageForm").find("[name=rolename]").val(rolename);
		$("#pageForm").attr("action","${ctx}/security/role!beforeAuthForRole.do");
		$("#pageForm").submit();
	}
	function beforeCopyRole(rid,rolename) {
		$("#pageForm").find("[name=rid]").val(rid);
		$("#pageForm").find("[name=rolename]").val(rolename);
		$("#pageForm").attr("action","${ctx}/security/role!beforeCopyRole.do");
		$("#pageForm").submit();
	}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">权限管理 &gt; 角色管理 </span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/security/role!findPage.do" method="post">
			<input type="hidden" name ="rid">
			<input type="hidden" name ="rolename">
			<input type="hidden" name="items">
			<table width="100%">
				<tr align="right">
					<td>
						<a class="a_button" href="javascript:void(0)" onclick="location.href='${ctx}/jsp/security/role/save.do'">
							<span>添加</span>
						</a>&nbsp;&nbsp;
					</td>
					<td style="text-align: right">
						角色名称：<input type="text" id="pageObject.params.roleName" name="pageObject.params.roleName" value="${pageObject.params.roleName}">
						&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();">
							<span>查找</span>
						</a>
					</td>
				</tr>
			</table>
			<br/>
			<div id="pageDiv" name="pageDiv" class="pageDiv">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>角色名称</th>
							<th>角色类型</th>
							<th>是否公共</th>
							<th>操作时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
				    <c:forEach var="role" items="${pageObject.resultList}">
				    	<tr >
					    	<td>${role.rname}</td>
					    	<td>${role.roleTypeName}</td>
					    	<td>${role.isPublicName}</td>
					    	<td><fmt:formatDate value="${role.dmltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					    	<td>
					    		<a href="javascript:editRole('${role.rid}')">编辑</a>&nbsp;&nbsp;
					    		<a href="javascript:beforeAuthForRole('${role.rid}','${role.rname}')">授权</a>&nbsp;&nbsp;
								<a href="javascript:beforeCopyRole('${role.rid}','${role.rname}')">复制角色用户</a>&nbsp;&nbsp;
								<a href="javascript:deleteOne('${role.rid}')">删除</a>
					    	</td>
				    	</tr>
					</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="5">
								<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</form>
	</div>
</body>
</html>