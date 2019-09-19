<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	
	function edit(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/baseinfo/position!beforeUpdate.do");
		$("#pageForm").submit();
	}
	
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
			alert("请至少选择要删除位置。");
			return false;
		}
		msg = "确认要删除所选位置？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/baseinfo/position!delete.do");
		}
		$("#pageForm").submit();
	}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">系统管理 &gt; 位置参数 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/baseinfo/position!findPage.do" method="post">
		<input type="hidden" name="id">
		
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="60%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th>位置ID</th>
						<th>位置名称</th>
						<th width="30%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="position" items="${pageObject.resultList}">
			    	<tr>
						<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${position.code}"/>
				    	</td>
				    	<td>${position.code}</td>
						<td>${position.name}</td>
				    	<td>
					    	<a href="javascript:edit('${position.code}')">编辑</a>
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
							<a class="a_button" href="javascript:location.href='${ctx}/baseinfo/position!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>&nbsp;&nbsp;
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>