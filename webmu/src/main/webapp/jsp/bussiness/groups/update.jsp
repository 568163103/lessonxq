<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/dynatree/ui.dynatree.css" />
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.dynatree.js"></script>
	<style type="text/css">
		.el_highlight{
			background-color:#95cffc;
		}
	</style>
	<script type="text/javascript">
		function edit() {
			if (confirm("确认要更新？")) {
			$("#pageForm").submit();
			}
		}
		$(function(){
			$("#pageForm").validationEngine();
			$("#tree_0").dynatree({
				checkbox: true,
				selectMode: 3,
				autoCollapse: false,
				fx: { height: "toggle", duration: 200 },
				children:${treeNode},
				onActivate: function(dtnode) {
				},
				onClick: function(node) {
					if($("[name=groups\\.type]").val()=="14"){
						return false;
					}
				},
				onSelect: function(select, node) {
				}
			});
			$("[name=groups\\.type]").change(function(e){
				if($(this).val()=="14"){
					$("#rids").val("");
					var tree = $("#tree_0").dynatree('getTree');
					$.map(tree.getSelectedNodes(), function(node){
						node.select(false);
					});
				}
			});
		});
		function getRids() {
			var selKeys=[];
			tree = $("#tree_0").dynatree('getTree');
			$.map(tree.getSelectedNodes(), function(node){
				if(node.data.key.substring(6,9)=="${channelValue}")
					selKeys[selKeys.length]=node.data.key;
			});
			$("#rids").val(selKeys.join(","));
		}

		var targetObjects = null;var nextTargetObjects = [];var currIndex = 0;
		function nextTarget(){
			if(0 == nextTargetObjects.length){
				queryResource ();
			}
			if(nextTargetObjects.length > currIndex){
				$(nextTargetObjects[currIndex]).click();
				currIndex++;
			} else if(nextTargetObjects.length > 0){
				currIndex = 0;
				nextTarget();
			}
		}
		function queryResource () {
			if(null != targetObjects){
				targetObjects.removeClass("el_highlight");
			}
			nextTargetObjects = [];
			currIndex = 0;
			var queryValue = $("#queryObject").val();
			targetObjects = $("#tree_0").find("a:contains('"+queryValue+"')");
			targetObjects.addClass("el_highlight");
			targetObjects.each(function(){
				if(nextTargetObjects.length > 0 && ($(this).position().top-$(nextTargetObjects[nextTargetObjects.length-1]).position().top)<300){
					return true;
				}
				nextTargetObjects[nextTargetObjects.length] = $(this);
			});
			if(nextTargetObjects.length != 0){
				nextTarget();
			}
		}
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 资源组管理 &gt; 编辑资源组</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/bussiness/groups!update.do" method="post" onsubmit="getRids()">
			<input type="hidden" id="id" name="id" value="${groups.id}">
			<input type="hidden" id="rids" name="rids" value="">
			<table width="100%" class="inputTable" border="0">
				<tr>
					<td>
						<fieldset title="资源组" style="width: 95%;height: 790px;">
							<legend>&nbsp;&nbsp;资源组&nbsp;&nbsp;</legend>
							<table class="inputTable" style="width: 100%" border="0">
								<tr>
									<td style="text-align:left;"><label class="field-request">* </label>资源组类型：</td>
									<td style="text-align:left;">
										<select class="validate[required]" name="groups.type" id="type">
											<option value="">请选择</option>
											<c:forEach var="type" items="${types}">
												<option value="${type.value}" <c:if test="${groups.type eq type.value}">selected</c:if>>${type.label}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td style="text-align: left;"><label class="field-request">* </label>资源组名称：</td>
									<td style="text-align:left;"><input type="text" value="${groups.name}" class="validate[required,ajax[checkUnique]]" name="groups.name" id="groups_name_${groups.id}" /></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:left;">&nbsp;&nbsp;备注(最多256字符)：</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:right;width: 98%;vertical-align: top" height="615px">
										<textarea name="groups.description" id="description" value="${groups.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${groups.description}</textarea>
									</td>
								</tr>
								<tr>
									<td style="text-align:center;" colspan="2">
										<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
										<a class="a_button" href="${ctx}/bussiness/groups!findPage.do"><span>返回</span></a>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
					<td>
						<fieldset title="分配资源" style="width: 96%;height: 790px;">
							<legend>&nbsp;&nbsp;分配资源&nbsp;&nbsp;</legend>
							<table width="100%" class="inputTable" border="0">
								<tr>
									<td align="center" style="padding-left: 5px">
										<div id="tree_0" style="padding-left: 5px;height: 720px;overflow:auto;"></div>
									</td>
								</tr>
								<tr>
									<td style="text-align:left;padding-left: 50px;vertical-align: middle">
										<input type="text" name="queryObject" id="queryObject" value="">&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="a_button" href="javascript:void(0)" onclick="queryResource()"><span>查询</span></a>&nbsp;&nbsp;
										<a class="a_button" href="javascript:void(0)" onclick="nextTarget()"><span>下一个</span></a>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>