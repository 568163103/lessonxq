<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
	<script type="text/javascript">
		$(function(){
			$("#pageForm").validationEngine();
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
		<span class="win-header-title">数据维护 &gt; 单位管理 &gt; 编辑单位</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/baseinfo/corp!update.do" method="post">
			<input type="hidden" id="id" name="id" value="${corp.cid}">

			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right;" width="18%"><label class="field-request">* </label>单位名称：</td>
					<td width="30%"><input type="text" value="${corp.cname}" class="validate[required,ajax[checkUnique]]" name="corp.cname" id="corp_cname_${corp.cid}" /></td>
					<td style="text-align: right;"><label class="field-request">* </label>负责人：</td>
					<td><input type="text" value="${corp.director}" class="validate[required]" name="corp.director" id="director" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">手机号：</td>
					<td><input type="text" name="corp.mobile" id="mobile" value="${corp.mobile}" class="validate[required]"></td>
					<td style="text-align:right;">单位地址：</td>
					<td>
						<input type="text" name="corp.address" id="address" value="${corp.address}">
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
						<a class="a_button" href="${ctx}/baseinfo/corp!findPage.do"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>