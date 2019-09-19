<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormPage.jsp"%>
	<script type="text/javascript">
		function submit(){
			if ($("#pageForm").validationEngine("validate")) {
				$("body").loading("正在初始化，请稍后");
				$('#pageForm').submit();
			}
		}
		$(function(){
			$("#pageForm").validationEngine();
			$("#ver").change(function(){
				if("32" == $(this).val()){
					$("[name='model']").val("nvs");
					$("#outputCount").val(6);
				} else {
					$("[name='model']").val("tm2");
					$("#outputCount").val(4);
				}
			});
			var emodels = {"hik":"8000-admin-12345","tvt":"8000-admin-admin","dahua":"37777-admin-admin","lc":"3000-888888-888888","onvif":"80--"}
			$("[name='model']").change(function(){
				var emodel = emodels[($(this).val())];
				if(emodel){
					$("[name='port']").val(emodel.split("-")[0]);
					$("[name='port']").removeClass("validate[required,custom[integer]]");$("[title=port]").html("");
					if (emodel.split("-")[0] && ""!=emodel.split("-")[0]){
						$("[name='port']").addClass("validate[required,custom[integer]]");$("[title=port]").html("* ");
					}
					$("[name='username']").val(emodel.split("-")[1]);
					$("[name='username']").removeClass("validate[required]");$("[title=username]").html("");
					if (emodel.split("-")[1] && ""!=emodel.split("-")[1]){
						$("[name='username']").addClass("validate[required]");$("[title=username]").html("* ");
					}
					$("[name='password']").val(emodel.split("-")[2]);
					$("[name='password']").removeClass("validate[required]");$("[title=password]").html("");
					if (emodel.split("-")[2] && ""!=emodel.split("-")[2]){
						$("[name='password']").addClass("validate[required]");$("[title=password]").html("* ");
					}
				} else {
					$("[name='port']").removeClass("validate[required,custom[integer]]").val("");$("[title=port]").html("");
					$("[name='username']").removeClass("validate[required]").val("");$("[title=username]").html("");
					$("[name='password']").removeClass("validate[required]").val("");$("[title=password]").html("");
				}
			});
		});
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 编码器管理 &gt; 初始化编码器</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/device/encoder!initUserResourceTree.do" method="post">
			<table width="100%" class="inputTable">
				<tr>
				</tr>
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>组织（单位）名称：</td>
					<td><input type="text" value="${rootName}" name="rootName" id="rootName" /></td>
					
				</tr>
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>版本号：</td>
					<td>
						<select class="validate[required]" name="ver" id="ver">
							<option value="32" selected>TM8731</option>
							<option value="1">TM8707</option>
							<option value="2">TM8709</option>
							<option value="3">TM8717</option>
							<option value="4">TM9001</option>
							<option value="5">TM8706</option>
							<option value="6">TM8710</option>
							<option value="7">TM8712</option>
							<option value="8">TM8716</option>
							<option value="9">TM8720</option>
							<option value="10">TM8723</option>
							<option value="11">TM8722</option>
							<option value="12">TM8726</option>
							<option value="13">TM8727</option>
							<option value="0">TM8701、TM8604等2G车载机</option>
						</select>
					</td>
					<td style="text-align:right;" width="20%"><label class="field-request">* </label>编码器类型：</td>
					<td width="30%">
						<select class="validate[required]" name="model" id="model">
							<option value="">请选择</option>
							<c:forEach var="encoderModel" items="${encoderModels}">
								<option value="${encoderModel.key}" <c:if test="${'nvs' eq encoderModel.key}">selected</c:if>>${encoderModel.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;"><label title="username" class="field-request"> </label>用户名：</td>
					<td><input type="text" value="" name="username" id="username" /></td>
					<td style="text-align: right;"><label title="password" class="field-request"> </label>密码：</td>
					<td><input type="text" value="" name="password" id="password" /></td>
				</tr>
				<tr>
					<td style="text-align: right;"><label title="port" class="field-request"> </label>端口：</td>
					<td><input type="text" value="" name="port" id="port" /></td>
					<td style="text-align: right;"><label class="field-request">* </label>视频通道数：</td>
					<td><input type="text" value="6" class="validate[required,custom[integer]]" name="outputCount" id="outputCount" /></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0)" onclick="submit()"><span>初始化</span></a>&nbsp;&nbsp;
						<a class="a_button" href="${ctx}/device/encoder!findPage.do"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>