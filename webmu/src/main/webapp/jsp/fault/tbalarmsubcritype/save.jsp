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
		<span class="win-header-title">业务管理 &gt; 新增告警配置</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/fault/tbAlarmsubcriType!save.do" method="post">			
			<table width="90%" class="inputTable">
				<tr>
					<td style="text-align:right;" width="10%"><label class="field-request">* </label>告警类型：</td>
					<td width="20%" colspan = "3">
					<select class="validate[required]" name="tbAlarmsubcriType.alarmType" id="alarmType" >
						<option value="">请选择</option>
						<c:forEach var="type" items="${alarmTypes}">
							<option value="${type.key}" <c:if test="${tbAlarmsubcriType.type eq type.key}">selected</c:if>>${type.value}</option>
						</c:forEach>
					</select>
				</td>
				</tr>		
				<tr>
					<td style="text-align:right;">布防时间：</td>
					<td>
						<input type="text" value="${tbAlarmsubcriType.beginTime}" name="tbAlarmsubcriType.beginTime" id="beginTime" class="Wdate" onclick="WdatePicker({readOnly:'true',dateFmt:'HH:mm:ss' ,maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
					</td>
					<td style="text-align:right;">撤防时间：</td>
					<td>
						<input type="text" value="${tbAlarmsubcriType.endTime}" name="tbAlarmsubcriType.endTime" id="endTime" class="Wdate" onclick="WdatePicker({readOnly:'true',dateFmt:'HH:mm:ss' ,minDate:'#F{$dp.$D(\'beginTime\')}' })"/>
					</td>
				</tr>	
				<tr>
					
					<td style="text-align:right;">备注(最多256字符)：</td>
					<td colspan ="3">
						<textarea name="tbAlarmsubcriType.description" id="description" value="${tbAlarmsubcriType.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${tbAlarmsubcriType.description}</textarea>
					</td>
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