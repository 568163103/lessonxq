<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	function edit() {
		if (confirm("确认要新增？")) {
			$("#pageForm").submit();
		}
	}
	$(function(){
	    $("#pageForm").validationEngine();
	});
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 通道管理 &gt; 添加通道</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/channel!save.do" method="post">
		<s:token/>
		<table width="90%" class="inputTable">
			<tr>
				<td style="text-align: right;" width="10%"><label class="field-request">* </label>通道名称：</td>
				<td width="20%"><input type="text" value="${channel.name}" class="validate[required]" name="channel.name" id="name" /></td>
				<td style="text-align: right;"  width="10%">存储服务器：</td>
				<td width="20%">
					<select name="msuChannel.serverId" id="serverId">
						<option value="">请选择</option>
						<c:forEach var="msu" items="${msus}">
							<option value="${msu.key}" <c:if test="${msuChannel.serverId eq msu.key}">selected</c:if>>${msu.value}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">语音是否启用：</td>
				<td><input type="radio" name="channel.hasAudio" id="hasAudio1" value="1"/> 是 <input type="radio" name="channel.hasAudio" id="hasAudio2" value="0"/> 否 </td>
				<td style="text-align: right;">云台是否启用：</td>
				<td><input type="radio" name="channel.hasPtz" id="hasPtz1" value="1"/> 是 <input type="radio" name="channel.hasPtz" id="hasPtz2" value="0"/> 否</td>
			</tr>
			<tr>
				<td style="text-align: right;">是否启用：</td>
				<td><input type="radio" name="channel.enabled" id="enabled1" value="true"/> 是 <input type="radio" name="channel.enabled" id="enabled2" value="false"/> 否</td>
				<td style="text-align:right;">备注(最多256字符)：</td>
				<td colspan="3">
					<textarea name="channel.description" id="description" value="${channel.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${channel.description}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center;">
					<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/device/channel!findPage.do"><span>返回</span></a></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>