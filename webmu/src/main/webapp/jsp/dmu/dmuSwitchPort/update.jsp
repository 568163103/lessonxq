<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>

<title>${global_app_name}</title>
<script type="text/javascript">
	function edit() {
		if (confirm("确认要保存编辑？")) {
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
		<span class="win-header-title">交换机端口 &gt; 编辑交换机端口</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/dmu/dmuSwitchPort!update.do" method="post">
		<s:token/>
		<input type="hidden" name="id" id="id" value="${id }">
		<input type="hidden" name="serverId" id="serverId" value="${serverId }">
		<table width="90%" class="inputTable">
			<tr>
				<td style="text-align:right;" width="10%"><label class="field-request">* </label>交换机ID：</td>
				<td width="20%">
					<input type="text" value="${serverId }"  name="switchid" id="switchid" maxlength="32" readonly ="true"/></td>
				</td>
				<td style="text-align:right;" width="10%"></td>
				<td width="20%"></td>
			</tr>
			<tr>
				<td style="text-align: right;" width="10%"><label class="field-request">* </label>端口：</td>
				<td width="20%"><input type="text" value="${port}" class="validate[required,custom[integer]]" name="port" id="port" maxlength="32"/></td>
				<td style="text-align: right;"><label class="field-request">* </label>远端IP地址：</td>
				<td><input type="text" value="${ip}" class="validate[required,custom[ipv4]]" name="ip" id="ip" /></td>				
			</tr>
			<tr>
				<td style="text-align:right;" width="10%">注：</td>
				<td width="20%" style="color : red;">华为交换机端口为机壳上的序号加5</td>
				<td style="text-align:right;" width="10%"></td>
				<td width="20%"></td>
			</tr>
			<tr>
				<td colspan="4" style="text-align:center;">
					<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>保存</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/dmu/dmuSwitchPort!findPage.do?items=${serverId }"><span>返回</span></a>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>