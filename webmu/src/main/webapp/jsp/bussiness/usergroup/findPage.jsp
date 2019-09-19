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
			alert("请至少选择要删除用户组。");
			return false;
		}
		msg = "确认要删除所选用户组？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/bussiness/userGroup!delete.do");
		}
		$("#pageForm").submit();
	}
	
	function editServer(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/bussiness/userGroup!beforeUpdate.do");
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 用户组管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/userGroup!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>					
					<td style="text-align: right">用户组名：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>					
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>				
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="2%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>						
						<th width="10%">用户组名</th>
						<th width="30%">描述</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="userGroup" items="${pageObject.resultList}">
			    	<tr >
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${userGroup.name}"/>
				    	</td>
				    	<td>${userGroup.name}</td>
						<td>${userGroup.description}</td>
				    	<td>
					    	<a href="javascript:editServer('${userGroup.id}')">编辑</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="4" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:location.href='${ctx}/bussiness/userGroup!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>&nbsp;&nbsp;
							<a class="a_button" href="${ctx}/bussiness/usertree!findPageUserGroupForAuthGroups.do"><span>用户组权限</span></a>&nbsp;&nbsp;
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>