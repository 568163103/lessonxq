<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headFormPage.jsp"%>
	<title>${global_app_name}</title>
</head>
<body style="height: 600px">
<div class="win-header" id="a" style="min-width: 800px;">
	<span class="win-header-title">性能管理 &gt; 请求用户 </span>
</div>
</div>
<div class="win-bodyer" style="min-width: 800px;">
	<form id="pageForm" name="pageForm" method="post">
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
				<tr>
					<th>用户ID</th>
					<th>用户名</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="reqResUse" items="${users}">
					<tr>
						<td>${reqResUse.id}</td>
						<td>${reqResUse.name}</td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr>
					<td colspan="2">
						总共查看用户数：${fn:length(users)}
					</td>
				</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
</body>
</html>