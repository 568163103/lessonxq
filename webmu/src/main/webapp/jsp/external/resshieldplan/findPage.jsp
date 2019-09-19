<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	
	function edit(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/external/shieldplan!update.do");
		$("#pageForm").submit();
	}	
	
	function view(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/external/shieldplan!beforeUpdate.do");
		$("#pageForm").submit();
	}	
	
	function beforeSave() {
		$("#pageForm").attr("action","${ctx}/external/shieldplan!beforeSave.do");
		$("#pageForm").submit();
	}
	
	var isSetFlash = true;
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
			alert("请选择要删除的计划。");
			return false;
		}
		msg = "确认要删除所选计划？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/external/shieldplan!delete.do");
			$("#pageForm").submit();
		}
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">外域设备管理 &gt; 接入网关  &gt; 资源屏蔽计划 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/external/shieldplan!findPage.do" method="post">
		<input type="hidden" name="id">
		<input type="hidden" name="serverId" id="serverId" value="${serverId }">
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th>序号</th>
						<th>管理员ID</th>
						<th>用户级别</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>制定时间</th>
						<th>状态</th>
						<th>操作</th>

					</tr>
				</thead>
				<tbody>
				<c:forEach var="data" items="${pageObject.resultList}">
			    	<tr>
			    		<td class="center">
							<input type="checkbox" name="items" class="items" value="${data.id}"/>
						</td>
						<td>${data.id}</td>
				    	<td>${data.userId}</td>
				    	<td>${data.userLevel}</td>
				    	<td>${data.beginTime}</td>
				    	<td>${data.endTime}</td>
				    	<td>${data.createTime}</td>
				    	<td>${data.statusZh}</td>
						<td>
					    	<a href="javascript:edit('${data.id}')">
							<c:if test="${data.status =='1'}">禁止</c:if>
							<c:if test="${data.status =='0'}">取消禁止</c:if></a>
							<a href="javascript:view('${data.id}')">
							查看详情</a>
				    	</td>
				    	
			    	</tr>
				</c:forEach>
				<c:if test="${not empty shieldId}">
					<tr>
						<td colspan = "2"></td>
						<td colspan = "7"></td>
					</tr>
					<tr>
						<td colspan = "2">序号：</td>
						<td colspan = "7">${shieldId}</td>
					</tr>
				</c:if>
				<c:if test="${not empty usernames}">
					<tr>
						<td colspan = "2">屏蔽计划用户：</td>
						<td colspan = "7">${usernames}</td>
					</tr>
				</c:if>
				<c:if test="${not empty channels}">
					<tr>
						<td colspan = "2">屏蔽计划通道：</td>
						<td colspan = "7">${channels}</td>
					</tr>
				</c:if>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="9">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
					<td colspan="9" style="text-align: center;vertical-align: top;height: 35px">
						<a class="a_button" href="javascript:void(0);" onclick="return beforeSave();"><span>添加</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0);" onclick="return deleteSelect();"><span>删除</span></a></a>&nbsp;&nbsp;
						<a class="a_button" href="${ctx}/device/encoder!findPlatformPage.do"><span>返回</span></a>
					</td>
				</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>