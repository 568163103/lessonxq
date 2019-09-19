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
	
	function deleteSelect() {
		if (!checkedItems()) {
			alert("请选择要删除案件。");
			return false;
		}
		msg = "确认要删除所选案件？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/ext/caseinfo!delete.do");
			$("#pageForm").submit();
		}
	}
	
	function viewCaseInfo(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/ext/caseinfo!view.do");
		$("#pageForm").submit();
	}

	$(function(){
	});
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">数据维护 &gt; 案件管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/ext/caseinfo!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">案件ID：</td>
					<td><input type="text" id="pageObject.params.cid" name="pageObject.params.cid" value="${pageObject.params.id}"></td>
					<td style="text-align: right">案件名称：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="18%"><div>ID</div></th>
						<th width="20%">名称</th>
						<th width="5%">类型</th>
						<th width="5%">案件状态</th>
						<th width="5%">创建者</th>
						<th width="8%">编号</th>
						<th width="10%">生成时间</th>
						<th width="14%">发生地</th>
						<th width="10%">描述</th>
						<th width="2%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="caseinfo" items="${pageObject.resultList}">
			    	<tr>
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${caseinfo.cid}"/>
				    	</td>
				    	<td>${caseinfo.cid}</td>
				    	<td>${caseinfo.name}</td>
						<td>${caseinfo.caseTypeName}</td>
						<td>${caseinfo.detectStateZh}</td>
				    	<td>${caseinfo.creator}</td>
				    	<td>${caseinfo.codenum}</td>
						<td>${caseinfo.createTimeStr}</td>
						<td>${caseinfo.happeningplace}</td>
						<td title="${caseinfo.description}">
							<c:if test="${fn:length(caseinfo.description)>12}">${fn:substring(caseinfo.description, 0, 12)}...</c:if>
							<c:if test="${fn:length(caseinfo.description)<=18}">${caseinfo.description}</c:if>
						</td>
				    	<td>
					    	<a href="javascript:viewCaseInfo('${caseinfo.cid}')">查看</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="11">
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