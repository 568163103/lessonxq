<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>

<link rel="stylesheet" href="../js/FlexBox/css/combo.select.css">
<script src="../js/FlexBox/js/jquery.combo.select.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#pageForm").validationEngine();
			//alarmTypeChange($("#alarmType"),'${sourceId}');
			
		});
		function alarmTypeChange(alarmTypeObject,dataValue){
			var sourceIdObject = $("#sourceId");
			sourceIdObject.children().not(":first").remove();
				$.post("${ctx}/bussiness/alarmprescheme!findChannel.do",function(data){
					$.each(data,function(key,value){
						sourceIdObject.append($("<option>").val(key).text(value));
					});
					$("#sourceId").comboSelect();
					if(dataValue){
						sourceIdObject.val(dataValue);
					}
				});
		}
		function edit() {
			if (confirm("确认要更新？")) {
			$("#pageForm").submit();
			}
		}
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 预案关联管理 &gt; 编辑预案关联</span>
	</div>
	<div class="win-bodyer" align="center">
		<form id="pageForm" name="pageForm" action="${ctx}/bussiness/alarmprescheme!update.do" method="post">
			<input type="hidden" id="oldAlarmPreScheme.alarmType" name="oldAlarmPreScheme.alarmType" value="${alarmType}">
			<input type="hidden" id="oldAlarmPreScheme.sourceId" name="oldAlarmPreScheme.sourceId" value="${sourceId}">
			<input type="hidden" id="oldAlarmPreScheme.schemeId" name="oldAlarmPreScheme.schemeId" value="${schemeId}">
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right;" ><label class="field-request">* </label>事件类型：</td>
					<td >
					<!--	<select name="alarmType" id="alarmType" onchange="alarmTypeChange(this)">   -->
						<select name="alarmType" id="alarmType" >
							<option value="">请选择</option>
							<c:forEach var="alarmTypeEntity" items="${alarmTypes}">
								<option value="${alarmTypeEntity.value}" <c:if test="${alarmType eq alarmTypeEntity.value}">selected</c:if>>${alarmTypeEntity.label}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>事件源：</td>
					<td>
						<input type="text" class="validate[required]" name="sourceId" value="${sourceId}" id="sourceId">
					</td>
				</tr>
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>预案：</td>
					<td>
						<select class="validate[required]" name="schemeId" id="schemeId">
							<option value="">请选择</option>
							<c:forEach var="schemeEntity" items="${preSchemes}">
								<option value="${schemeEntity.key}" <c:if test="${schemeId eq schemeEntity.key}">selected</c:if>>${schemeEntity.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;">
						<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
						<a class="a_button" href="${ctx}/bussiness/alarmprescheme!findPage.do"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>