<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
	<script type="text/javascript">
		function edit() {
			if (confirm("确认要更新？")) {
			$("#pageForm").submit();
			}
		}
		$(function(){
			$("#pageForm").validationEngine();
		});
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt;告警资源 &gt; 编辑告警资源</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/fault/alarmRes!update.do" method="post">
			<input type="hidden" id="id" name="alarmRes.id" value="${alarmRes.id}">
			<input type="hidden" name="alarmRes.resId" id="resId" value="${alarmRes.resId }">
			<input type="hidden" name="alarmRes.alarmType" id="alarmType" value="${alarmRes.alarmType }">
			<input type="hidden" name="alarmRes.sid" id="sid" value="${alarmRes.sid }">
			<input type="hidden" name="alarmRes.description" id="description" value="${alarmRes.description}">
			<table width="90%" class="inputTable">
				<tr>
					<td style="text-align:right;" width="10%"><label class="field-request">* </label>告警资源名称：</td>					
					<td width="80%" colspan="3"><input type="text" value="${alarmRes.name}"  name="alarmRes.name" id="name" maxlength="64"/></td>
				</tr>				
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>