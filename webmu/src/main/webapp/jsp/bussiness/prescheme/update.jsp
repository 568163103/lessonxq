<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormPage.jsp"%>

<link rel="stylesheet" href="../js/FlexBox/css/combo.select.css">
<script src="../js/FlexBox/js/jquery.combo.select.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#actionPageForm").validationEngine();
			changeActionType($("#actionType"));
			changeChannel($("#actionObject\\.target"),'${actionObject.data}');
			$("[name=preScheme\\.name]").change(function(){
				if ($(this).validationEngine("validate")){
					$.post("${ctx}/bussiness/prescheme!savePreScheme.do",[{"name":"id","value":${preScheme.id}},{"name":"preScheme.name","value":$(this).val()}],function(data){
						if(data && data=="ok"){
							alert("保存数据成功！");
						} else {
							alert("保存数据失败！");
						}
					});
				}
			});
			
			$("#select").comboSelect();
			$("#select2").comboSelect();
		});
		function changeActionType(actionTypeObject){
			$("[class=action_tr]").hide().find(":input").attr("disabled","disabled");
			$("[name=action_tr_"+$(actionTypeObject).val()+"]").show().find(":input").removeAttr("disabled");
		}
		function changeChannel(channelObject,dataValue){
			var presetObject = $("#actionObject\\.data_preset");
			presetObject.children().not(":first").remove();
			if("preset" == $("#actionType").val() && $(channelObject).val()!=""){
				$.post("${ctx}/bussiness/prescheme!getPreset.do?channelId="+$(channelObject).val(),function(data){
					$.each(data,function(i,e){
						presetObject.append($("<option>").val(e.num).text(e.name));
					});
					if(dataValue){
						presetObject.val(dataValue);
					}
				});
			}
		}

		function checkedItems() {
			var flag = false;
			$("#pageForm").find("[name=items]").each(function() {
				if (this.checked) {
					flag = true;
				}
			});
			return flag;
		}

		function deleteAll() {
			if (!checkedItems()) {
				alert("请至少选择要删除动作。");
				return false;
			}
			msg = "确认要删除所选动作？";
			if (window.confirm(msg)) {
				$("#pageForm").attr("action","${ctx}/bussiness/prescheme!deleteAction.do");
			}
			$("#pageForm").submit();
		}

		function editAction(id) {
			$("#pageForm").find("[name=actionId]").val(id);
			$("#pageForm").attr("action","${ctx}/bussiness/prescheme!beforeUpdate.do");
			$("#pageForm").submit();
		}
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 预案管理 &gt; 编辑预案</span>
	</div>
	<div class="win-bodyer">
		<fieldset style="width: 98%;text-align: center">
			<legend>&nbsp;&nbsp;预案信息&nbsp;&nbsp;</legend>
			<form id="preschemePageForm" name="pageForm" action="${ctx}/bussiness/prescheme!savePreScheme.do" method="post" >
				<input type="hidden" name="id" value="${preScheme.id}">
				<table class="inputTable" style="width: 100%" border="0">
					<tr>
						<td style="text-align: right;" width="20%"><label class="field-request">* </label>预案名称：</td>
						<td style="text-align:left;" width="80%">
							<input type="text" value="${preScheme.name}" class="validate[required,ajax[checkUnique]]" name="preScheme.name" id="preScheme_preSchemeName_${preScheme.id}" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0)" onclick="$('#preschemePageForm').submit()"><span>保存</span></a>
						</td>
					</tr>
				</table>
			</form>
		</fieldset>
		<fieldset style="width: 98%;text-align: center">
			<legend>&nbsp;&nbsp;动作信息&nbsp;&nbsp;</legend>
			<form id="actionPageForm" name="pageForm" action="${ctx}/bussiness/prescheme!updateAction.do" method="post" >
				<input type="hidden" name="id" value="${preScheme.id}">
				<input type="hidden" name="actionId" value="${actionObject.id}">
				<table class="inputTable" style="width: 100%" border="0">
					<tr>
						<td style="text-align:right;" width="20%"><label class="field-request">* </label>动作类型：</td>
						<td style="text-align:left;">
							<select class="validate[required]" name="actionObject.type" id="actionType" onchange="changeActionType(this);">
								<option value="">请选择</option>
								<c:forEach var="actionType" items="${actionTypes}">
									<option value="${actionType.value}" <c:if test="${actionObject.type eq actionType.value}">selected</c:if>>${actionType.label}</option>
								</c:forEach>
							</select>
						</td>
					</tr>				
					<tr name="action_tr_record" class="action_tr" style="display: none">
					<td style="text-align: right;">通道：</td>
					<td style="text-align:left;">
					<!--	<select name="actionObject.target" id="select2" onchange="changeChannel(this)">
							<option value="">请选择</option>
							<c:forEach var="channel" items="${channels}">
								<option value="${channel.key}" <c:if test="${actionObject.target eq channel.key}">selected</c:if>>${channel.value}</option>
							</c:forEach>
						</select>  -->
						<input type="text" class="validate[required]" name="actionObject.target" value="${actionObject.target}" id="actionObject.target">
					</td>
					<td style="text-align:right;" width="20%"><label class="field-request">* </label>预录时间（秒）：</td>
					<td style="text-align:left;">
						<input type="text" class="validate[required]" name="actionObject.aheadOfTime" value="${actionObject.aheadOfTime}" id="actionObject.aheadOfTime_record">
					</td>
				</tr>
				<tr name="action_tr_preset" class="action_tr" style="display: none">
					<td style="text-align: right;">通道：</td>
					<td style="text-align:left;">
					<!--	<select name="actionObject.target" id="select" onchange="changeChannel(this)">
							<option value="">请选择</option>
							<c:forEach var="channel" items="${channels}">
								<option value="${channel.key}" <c:if test="${actionObject.target eq channel.key}">selected</c:if>>${channel.value}</option>
							</c:forEach>
						</select>  -->
						<input type="text" class="validate[required]" name="actionObject.target" value="${actionObject.target}" id="actionObject.target">
					</td>
					<td style="text-align:right;" width="20%"><label class="field-request">* </label>预置位：</td>
					<td style="text-align:left;" colspan="3">
						<select class="validate[required]" name="actionObject.data" id="actionObject.data_preset">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
				<tr name="action_tr_sound" class="action_tr" style="display: none">
					<td style="text-align: right;">用户：</td>
					<td style="text-align:left;">
						<select name="actionObject.target" id="actionObject.target">
							<option value="">请选择</option>
							<c:forEach var="channel" items="${users}">
								<option value="${channel.key}" <c:if test="${actionObject.target eq channel.key}">selected</c:if>>${channel.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr name="action_tr_email" class="action_tr" style="display: none">
					<td style="text-align: right;">用户：</td>
					<td style="text-align:left;">
						<select name="actionObject.target" id="actionObject.target">
							<option value="">请选择</option>
							<c:forEach var="channel" items="${users}">
								<option value="${channel.key}" <c:if test="${actionObject.target eq channel.key}">selected</c:if>>${channel.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr name="action_tr_message" class="action_tr" style="display: none">
					<td style="text-align: right;">用户：</td>
					<td style="text-align:left;">
						<select name="actionObject.target" id="actionObject.target">
							<option value="">请选择</option>
							<c:forEach var="channel" items="${users}">
								<option value="${channel.key}" <c:if test="${actionObject.target eq channel.key}">selected</c:if>>${channel.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr name="action_tr_popwindow" class="action_tr" style="display: none">
					<td style="text-align: right;">用户：</td>
					<td style="text-align:left;">
						<select name="actionObject.target" id="actionObject.target">
							<option value="">请选择</option>
							<c:forEach var="channel" items="${users}">
								<option value="${channel.key}" <c:if test="${actionObject.target eq channel.key}">selected</c:if>>${channel.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr name="action_tr_prompt" class="action_tr" style="display: none">
					<td style="text-align: right;">用户：</td>
					<td style="text-align:left;">
						<select name="actionObject.target" id="actionObject.target">
							<option value="">请选择</option>
							<c:forEach var="channel" items="${users}">
								<option value="${channel.key}" <c:if test="${actionObject.target eq channel.key}">selected</c:if>>${channel.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
					<tr>
						<td style="text-align:center;" colspan="4">
							<a class="a_button" href="javascript:void(0)" onclick="$('#actionPageForm').submit()"><span>保存</span></a>&nbsp;&nbsp;
							<a class="a_button" href="${ctx}/bussiness/prescheme!findPage.do"><span>返回</span></a>
						</td>
					</tr>
				</table>
			</form>
		</fieldset>
		<fieldset style="width: 98%;text-align: center">
		<legend>&nbsp;&nbsp;动作列表&nbsp;&nbsp;</legend>
		<form id="pageForm" name="pageForm" action="${ctx}/bussiness/prescheme!beforeUpdate.do" method="post" >
			<input type="hidden" name="id" value="${preScheme.id}">
			<input type="hidden" name="actionId" value="${actionObject.id}">
			<div id="pageDiv" name="pageDiv" class="pageDiv">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="10%">动作</th>
						<th width="37%">对象</th>
						<th width="10%">参数名</th>
						<th width="5%">参数</th>
						<th width="10%">参数名</th>
						<th width="5%">参数</th>
						<th width="10%">参数名</th>
						<th width="5%">参数</th>
						<th width="5%">操作</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="act" items="${actionObjects}">
						<tr>
							<td class="center">
								<input type="checkbox" name="items" class="items" value="${act.id}"/>
							</td>
							<td>${act.name}</td>
							<td>${act.targetName}</td>
							<td>${act.param1Name}</td>
							<td>${act.param1}</td>
							<td>${act.param2Name}</td>
							<td>${act.param2}</td>
							<td>${act.param3Name}</td>
							<td>${act.param3}</td>
							<td>
								<a href="javascript:editAction('${act.id}')">编辑</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
					<tfoot>
					<tr>
						<td colspan="10">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="10" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>
						</td>
					</tr>
					</tfoot>
				</table>
			</div>
		</form>
		</fieldset>
	</div>
</body>
</html>