<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
	<script type="text/javascript">
		$(function(){
			var hasPtzs = $("[name='channel.hasPtz']");
			hasPtzs.each(function(){
				if($(this).val()=='${channel.hasPtz}') {
					$(this).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}
			});
			var hasAudios = $("[name='channel.hasAudio']");
			hasAudios.each(function(){
				if($(this).val()=='${channel.hasAudio}') {
					$(this).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}
			});
			var enables = $("[name='channel.enabled']");
			enables.each(function(){
				if($(this).val()=='${channel.enabled}') {
					$(this).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}
			});
			$("[name=pageForm]").validationEngine();
		});

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
		<span class="win-header-title">故障管理 &gt; 告警级别 &gt; 编辑告警级别</span>
	</div>
	<div class="win-bodyer" align="center">
		<form id="pageForm" name="pageForm" action="${ctx}/fault/alarmLevel!update.do" method="post">
			<input type="hidden" id="id" name="id" value="${alarmType.id}">
				<table width="100%" class="inputTable">
					<tr>
						<td style="text-align: right;" width="18%">告警类型ID：</td>
						<td width="30%"><input type="text" value="${alarmType.typeId}" name="alarmType.typeId" id="typeId" maxlength="64" disabled="disabled"/></td>
						<td style="text-align: right;">告警类型名称：</td>
						<td width="30%"><input type="text" value="${alarmType.alarmTypeZh}" name="alarmType.alarmTypeZh" id="alarmTypeZh" maxlength="64" disabled="disabled"/></td>
					</tr>
					<tr>
						<td style="text-align: right;"> <label class="field-request">* </label>告警等级：</td>
						<td>
							<select name="alarmType.level" id="level" class="validate[required]">
								<option value="">请选择</option>
								<c:forEach var="alarmLevel" items="${alarmLevels}">
									<option value="${alarmLevel.value}" <c:if test="${alarmType.level eq alarmLevel.value}">selected</c:if>>${alarmLevel.label}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr>
						<td style="text-align:right;">备注(最多256字符)：</td>
						<td >
							<textarea name="alarmType.description" id="description" value="${alarmType.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${alarmType.description}</textarea>
						</td>
					</tr>
				</table>
				<table width="100%" class="inputTable">
					<tr>
						<td colspan="4" style="text-align: center;">
							<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
							<a class="a_button" href="${ctx}/fault/alarmLevel!findPage.do"><span>返回</span></a>
						</td>
					</tr>
				</table>
		</form>
	</div>
</body>
</html>