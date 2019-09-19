<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#pageForm").validationEngine();
	});
</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">系统管理 &gt; 位置参数 &gt; 添加参数</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/baseinfo/position!save.do" method="post">
		<s:token/>
		<table width="100%" class="inputTable">
			<tr>
				<td style="text-align: right;"><label class="field-request">* </label>位置id：</td>
				<td><input type="text" value="${position.code}" class="validate[required,custom[eonRange],maxSize[6]]" name="position.code" id="code" maxlength="6"/></td>
				<td style="text-align: right;" width="18%"><label class="field-request">* </label>位置名称：</td>
				<td width="30%"><input type="text" value="${position.name}" class="validate[required]" name="position.name" id="name" /></td>
			</tr>
			
			<tr>
				<td colspan="4" style="text-align: center;">
					<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>添加</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/baseinfo/position!findPage.do"><span>返回</span></a></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>