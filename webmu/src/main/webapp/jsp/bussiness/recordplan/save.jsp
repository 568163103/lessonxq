<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headForm.jsp"%>
<script src="${ctx}/js/jquery/jquery.ui.selectable.min.js"></script>
<style type="text/css">
	#selectable .ui-selecting { background: #b6daff; }
	#selectable .ui-selected { background: #379be9; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 744px; }

	#selectable li {
		margin: 1px;
		padding: 1px;
		float: left;
		width: 25px;
		height: 20px;
		font-size: 1.4em;
		text-align: center;
		background: #e6e6e6 url("${ctx}/css/jquery/images/ui-bg_glass_75_e6e6e6_1x400.png") repeat-x scroll 50% 50%;
		border: 1px solid #d3d3d3;
		color: #555555;
		font-weight: normal;
	}
</style>
<script type="text/javascript">
	$(function() {
		$("#pageForm").validationEngine();
		$( "#selectable" ).selectable({
			selected : function( event, ui ) {
				$(ui.selected).attr("value",'1');
			},
			unselected : function( event, ui ) {
				$(ui.unselected).attr("value",'0');
			}
		});
	});
	function edit() {
			if (confirm("确认要更新？")) {
			$("#pageForm").submit();
			}
		}
	function getTimeSpan(){
		var result = "";
		$("#selectable li").each(function(i,e){
			result = result + $(e).val();
		})
		$("#recordPlan\\.timespan").val(result)
	}
</script>
	<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 存储方案管理 &gt; 编辑存储方案</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/recordplan!save.do" method="post" onsubmit="getTimeSpan();">
		<s:token/>
		<input type="hidden" id="recordPlan.timespan" name="recordPlan.timespan" value="${recordPlan.timespan}">
		<table width="100%" class="inputTable" border="0">
			<tr>
				<td style="text-align: right;" width="20%"><label class="field-request">* </label>方案名：</td>
				<td width="30%">
					<input type="text" value="${recordPlan.name}" class="validate[required,ajax[checkUnique]]" name="recordPlan.name" id="recordPlan_name_${recordPlan.name}" />
				</td>
				<td style="text-align: right;" width="20%"><label class="field-request">* </label>分辨率：</td>
				<td>
					<select class="validate[required]" name="recordPlan.resolution" id="recordPlan.resolution">
						<option value="">请选择</option>
						<c:forEach var="resolution" items="${resolutions}">
							<option value="${resolution.key}" <c:if test="${recordPlan.resolution eq resolution.key}">selected</c:if>>${resolution.value}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label class="field-request">* </label>帧率：</td>
				<td>
					<input type="text" value="${recordPlan.frametype}" class="validate[required,integer]" name="recordPlan.frametype" id="recordPlan.frametype" />
				</td>
				<td style="text-align: right;"><label class="field-request">* </label>覆盖天数：</td>
				<td>
					<input type="text" value="${recordPlan.cycleDate}" class="validate[required,integer]" name="recordPlan.cycleDate" id="recordPlan.cycleDate" />
				</td>
			</tr>
			<tr>
				<td style="text-align:right;">备注(最多256字符)：</td>
				<td colspan="3" style="text-align: left">
					<textarea name="recordPlan.description" id="description" value="${server.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${recordPlan.description}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="padding: 10px 10% 0 20%;text-align: center;">
					<fieldset style="text-align: center;padding: 10px 5px 5px 10px;width: 800px">
						<legend>&nbsp;&nbsp;时间段&nbsp;&nbsp;</legend>
						<table border="0">
							<tr style="height: 22px">
								<td style="vertical-align: middle">星期日</td>
								<td style="text-align: center;" rowspan="7">
									<ol id="selectable">
										<c:forEach var="i" begin="1" end="7" step="1">
											<c:forEach var="i" begin="0" end="23" step="1">
												<li class="ui-state-default" name="select_li" value="0">${i}</li>
											</c:forEach>
										</c:forEach>
									</ol>
								</td>
							</tr>
							<tr style="height: 22px">
								<td style="vertical-align: middle">星期一</td>
							</tr>
							<tr style="height: 22px">
								<td style="vertical-align: middle">星期二</td>
							</tr>
							<tr style="height: 22px">
								<td style="vertical-align: middle">星期三</td>
							</tr>
							<tr style="height: 22px">
								<td style="vertical-align: middle">星期四</td>
							</tr>
							<tr style="height: 22px">
								<td style="vertical-align: middle">星期五</td>
							</tr>
							<tr style="height: 22px">
								<td style="vertical-align: middle">星期六</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center;">
					<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>添加</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/bussiness/recordplan!findPage.do"><span>返回</span></a></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>