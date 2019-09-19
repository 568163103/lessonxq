<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
	<script type="text/javascript">
		$(function(){
			var enables = $("[name='terminal.enabled']");
			enables.each(function(){
				if($(this).val()=='${terminal.enabled}') {
					$(this).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}
			});
			$("#pageForm").validationEngine();
		});
		
		function check(){
		msg = "该操作会导致资源编码发生更改，请删除旧资源后再进行操作，是否确定更改?";
		if (window.confirm(msg)) {
			$("#pageForm").submit();
		}
		
		}
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">系统管理 &gt; 位置参数 &gt; 编辑参数</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/baseinfo/position!update.do" method="post">
			<input type="hidden" id="oldcode" name="position.oldcode" value="${position.code}">
			<input type="hidden" id="oldname" name="position.oldname" value="${position.name}">
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>位置id：</td>
					<td><input type="text" value="${position.code}" name="position.code" id="code" class="validate[required,custom[eonRange],maxSize[6]]"  maxlength="6"/></td>
					<td style="text-align: right;"><label class="field-request">* </label>位置名称：</td>
					<td><input type="text" value="${position.name}" name="position.name" id="name" class="validate[required,maxSize[32]]"  maxlength="32"/></td>
				</tr>
				
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0)" onclick="check()"><span>更新</span></a>&nbsp;&nbsp;
						<a class="a_button" href="${ctx}/baseinfo/position!findPage.do"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>