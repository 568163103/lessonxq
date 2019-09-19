<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
<title>${global_app_name}</title>

<link rel="stylesheet" href="../js/FlexBox/css/combo.select.css">
<script src="../js/FlexBox/js/jquery.combo.select.js"></script>
<script type="text/javascript">
    var channelArray = ["1","2","3","4","5","6","9","10","11","12","13","14","15","16","17","18","19"];
	$(function(){
		$("#pageForm").validationEngine();
		//alarmTypeChange($("#alarmType"),'${sourceId}');
		
	});
	function edit() {
			if (confirm("确认要更新？")) {
			$("#pageForm").submit();
			}
		}
	function alarmTypeChange(alarmTypeObject,dataValue){
		var sourceIdObject = $("#sourceId");
		var alarmType = $(alarmTypeObject).val();
		sourceIdObject.children().not(":first").remove();
			$.post("${ctx}/bussiness/alarmprescheme!findChannel.do?alarmType="+alarmType,function(data){
				$.each(data,function(key,value){
					sourceIdObject.append($("<option>").val(key).text(value));
				});
				$("#sourceId").comboSelect();
				if(dataValue){
					sourceIdObject.val(dataValue);
				}
			});
	}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 预案关联管理 &gt; 编辑预案关联</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/alarmprescheme!save.do" method="post">
		<s:token/>
		<table width="100%" class="inputTable">
			<tr>
				<td style="text-align: right;"><label class="field-request">* </label>事件类型：</td>
				<td >
				<!--	<select class="validate[required]" name="alarmType" id="alarmType" onchange="alarmTypeChange(this)">  -->
					<select class="validate[required]" name="alarmType" id="alarmType" >
						<option value="">请选择</option>
						<c:forEach var="alarmTypeEntity" items="${alarmTypes}">
							<option value="${alarmTypeEntity.value}" <c:if test="${alarmType eq alarmTypeEntity.value}">selected</c:if>>${alarmTypeEntity.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">事件源：</td>
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
							<option value="${schemeEntity.key}" <c:if test="${sourceId eq schemeEntity.key}">selected</c:if>>${schemeEntity.value}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>添加</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/bussiness/alarmprescheme!findPage.do"><span>返回</span></a></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>