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
		<span class="win-header-title">用户组管理 &gt; 添加用户组</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/userGroup!save.do" method="post">
		<s:token/>
		<table width="90%" class="inputTable">
			
			<tr>
				<td style="text-align: right;"><label class="field-request">* </label>用户组名称：</td>
				<td colspan ="3"><input type="text" value="${userGroup.name}"  name="userGroup.name" id="name" /></td>
				
			</tr>							
			<tr>
				<td style="text-align:right;">备注(最多256字符)：</td>
				<td colspan ="3">
					<textarea name="userGroup.description" id="description" value="${userGroup.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${userGroup.description}</textarea>
				</td>
			
			</tr>
			<tr>
				<td colspan="4" style="text-align:center;">
					<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>保存</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/bussiness/userGroup!findPage.do"><span>返回</span></a>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>