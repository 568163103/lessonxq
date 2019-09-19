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
			$("[name=pageForm]").validationEngine();
		});
		

	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 摄像机通道管理 &gt; 批量编辑摄像机通道</span>
	</div>
	<div class="win-bodyer" align="center">
		<form id="pageForm" name="pageForm" action="${ctx}/device/channel!updatePlan.do" method="post">
			<input type="hidden" id="ids" name="ids" value="${ids}">
			<fieldset style="width: 80%;text-align: center">
				<legend>&nbsp;&nbsp;基础信息&nbsp;&nbsp;</legend>
				<table width="100%" class="inputTable">
					<tr>
						<td style="text-align: right;">存储方案：</td>
						<td>
							<input type="hidden" name="channelRecordPlan.channelId" value="${channel.id}">
							<select name="channelRecordPlan.planName" id="planName">
								<option value="">请选择</option>
								<c:forEach var="recordPlanName" items="${recordPlanNames}">
									<option value="${recordPlanName}" <c:if test="${channelRecordPlan.planName eq recordPlanName}">selected</c:if>>${recordPlanName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<table width="100%" class="inputTable">
					<tr>
						<td colspan="4" style="text-align: center;">
							<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
							<c:if test="${empty param.encoderId}">
								<a class="a_button" href="${ctx}/device/channel!findPage.do"><span>返回</span></a>
							</c:if>
							<c:if test="${not empty param.encoderId}">
								<a class="a_button" href="${ctx}/device/encoder!beforeUpdate.do?id=${channel.encoderId}"><span>返回</span></a>
							</c:if>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>