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
			alert("请至少选择要删除单位。");
			return false;
		}
		msg = "确认要删除所选单位？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/baseinfo/corp!delete.do");
		}
		$("#pageForm").submit();
	}
	
	function editCorp(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/baseinfo/corp!beforeUpdate.do");
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">数据维护 &gt; 单位管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/baseinfo/corp!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">单位名称：</td>
					<td><input type="text" id="pageObject.params.cname" name="pageObject.params.cname" value="${pageObject.params.cname}"></td>
					<td style="text-align: right">单位地址：</td>
					<td><input type="text" id="pageObject.params.address" name="pageObject.params.address" value="${pageObject.params.address}"></td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th>单位ID</th>
						<th>单位名称</th>
						<th>负责人</th>
						<th width="10%">电话</th>
						<th>地址</th>
						<th width="5%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="corp" items="${pageObject.resultList}">
			    	<tr>
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${corp.cid}"/>
				    	</td>
				    	<td>${corp.cid}</td>
				    	<td>${corp.cname}</td>
						<td>${corp.director}</td>
						<td>${corp.mobile}</td>
						<td>${corp.address}</td>
				    	<td>
					    	<a href="javascript:editCorp('${corp.cid}')">编辑</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="7" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:location.href='${ctx}/baseinfo/corp!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
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