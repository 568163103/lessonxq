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
			alert("请至少选择要删除资源组。");
			return false;
		}
		msg = "确认要删除所选资源组？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/bussiness/groups!delete.do");
		}
		$("#pageForm").submit();
	}
	
	function editServer(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/bussiness/groups!beforeUpdate.do");
		$("#pageForm").submit();
	}
	function beforeAuthForGroups(id,name) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").find("[name=name]").val(name);
		$("#pageForm").attr("action","${ctx}/bussiness/groups!beforeAuthForGroups.do");
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 资源组管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/groups!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">资源组ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">资源组名称：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>
					<td style="text-align: right">位置：</td>
					<td>
						<select name="pageObject.params.position" id="pageObject.params.position">
							<option value="">全部</option>
							<c:forEach var="type" items="${positions}">
								<option value="${type.value}" <c:if test="${pageObject.params.position eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right">资源组类型：</td>
					<td>
						<select name="pageObject.params.type" id="pageObject.params.type">
							<option value="">全部</option>
							<c:forEach var="type" items="${types}">
								<option value="${type.value}" <c:if test="${pageObject.params.type eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="15%">资源组ID</th>
						<th width="15%">资源组名称</th>
						<th width="5%">资源组类别</th>
						<th width="57%">描述</th>
						<th width="5%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="groups" items="${pageObject.resultList}">
			    	<tr>
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${groups.id}"/>
				    	</td>
				    	<td>${groups.id}</td>
				    	<td>${groups.name}</td>
						<td>${groups.typeName}</td>
						<td>${groups.description}</td>
				    	<td>
					    	<a href="javascript:editServer('${groups.id}')">编辑</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="6">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="6" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:location.href='${ctx}/bussiness/groups!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>