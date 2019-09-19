<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.scrollLoading.js"></script>
	<title>${global_app_name}</title>
</head>
<body style="min-width: auto">
<form id="pageForm" name="pageForm" action="${ctx}/thirdparty/formInterface.do?cmd=110101&u=${param.u}" method="post">
	<div id="pageDiv" name="pageDiv">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tbody>
			<c:forEach var="channelSnapshot" items="${channelSnapshots}">
				<tr>
					<td style="text-align:left;font-size: 200%;font-weight:bold;padding: 10px 0 10px 5%">${channelSnapshot.name}</td>
					<td style="text-align:right;font-size: 200%;font-weight:bold;padding: 10px 5% 10px 0">${channelSnapshot.statusZh}</td>
				</tr>
				<tr>
					<td style="text-align:center;padding: 0 0 40px 0" colspan="2">
						<img width="90%" src="${ctx}/thirdparty/formInterface.do?cmd=110102&url=${channelSnapshot.url}" onclick="javascript:location.href='${ctx}/thirdparty/formInterface.do?cmd=120101&id=${channelSnapshot.resId}'">
					</td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
			<tr>
				<td colspan="2">
					<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
				</td>
			</tr>
			</tfoot>
		</table>
	</div>
</form>
</body>
</html>