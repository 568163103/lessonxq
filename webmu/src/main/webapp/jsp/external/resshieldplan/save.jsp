<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormPage.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/dynatree/ui.dynatree.css" />
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.dynatree.js"></script>
	<style type="text/css">
		.el_highlight{
			background-color:#95cffc;
		}
	</style>
<title>${global_app_name}</title>
<script type="text/javascript">
	var userGroupsTree0 = null;
	var userGroupsTree1 = null;
	$(function(){
	    $("#pageForm").validationEngine();
		userGroupsTree0 = $("#tree_0");
		userGroupsTree0.dynatree({
			checkbox: true,
			selectMode: 3,
			autoCollapse: false,
			imagePath:"${ctx}/css/dynatree/images/",
			fx: { height: "toggle", duration: 200 },
			children:${resTreeNode},
			onActivate: function(dtnode) {
			},
			onSelect: function(select, node) {
			}
		});
		userGroupsTree1 = $("#tree_1");
		userGroupsTree1.dynatree({
			checkbox: true,
			selectMode: 3,
			autoCollapse: false,
			fx: { height: "toggle", duration: 200 },
			children:${userTreeNode}
		});
		$("tr .hint").mouseover(function(){
			$(this).css("cursor", "pointer");
		});
		$("tr .hint :first").click();
		var enables = $("[name='tbResShieldPlan.status']");
			enables.each(function(){
				if($(this).val()=='0') {
					$(this).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}
			});
	});
	function getresids() {
		var selKeys="";
		tree = $("#tree_0").dynatree('getTree');
		$.map(tree.getSelectedNodes(), function(node){
		var key = node.data.key;
		selKeys += key+",";
		});
		if(selKeys!=""){
			selKeys = selKeys.substring(0,selKeys.length-1);	
		}
		$("#resids").val(selKeys);
	}
	
	function getuserids() {
		var selKeys="";
		tree = $("#tree_1").dynatree('getTree');
		$.map(tree.getSelectedNodes(), function(node){
		var key = node.data.key;
		selKeys += key+",";
		});
		if(selKeys!=""){
			selKeys = selKeys.substring(0,selKeys.length-1);	
		}
		$("#userids").val(selKeys);
	}
	
	function submitAuthGroupForUser(){
		var beginTime = $("#beginTime").val();
		if(beginTime==null||beginTime==""){
			alert("请选择屏蔽开始时间！");
			return false;
		}
		var endTime = $("#endTime").val();
		if(endTime==null||endTime==""){
			alert("请选择屏蔽结束时间！");
			return false;
		}
		if(beginTime>endTime){
			alert("结束时间不能小于开始时间！");
			return false;
		}
		getresids();
		getuserids();
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">外域设备管理 &gt; 接入网关  &gt; 告警资源订阅</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/external/shieldplan!save.do" method="post">
		<table width="98%" border="0">
			<tr>
				<td style="text-align:left;">
				<label class="field-request">* </label>
				屏蔽开始时间：
					<input type="text" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" name="tbResShieldPlan.beginTime" id="beginTime"/>
				<label class="field-request">* </label>
				屏蔽结束时间：
					<input type="text" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" name="tbResShieldPlan.endTime" id="endTime"/>
				</td>
				<input type = "hidden" id = "status" name = "tbResShieldPlan.status" value = "0">				
			</tr>
			<tr>
				<td style="width: 40%;">
					<fieldset style="width: 98%;height: 490px;">
						<legend>&nbsp;&nbsp;下级设备信息&nbsp;&nbsp;</legend>
						
							<input type="hidden" id="resids" name="resids" value="">
							<input type="hidden" id="sid" name="tbResShieldPlan.sid" value="${serverId }">
							<table width="100%" class="inputTable" border="0">
								<tr>
									<td align="center" style="padding-left: 5px" colspan="2">
										<div id="tree_0" style="padding-left: 5px;height: 720px;overflow:auto;"></div>
									</td>
								</tr>
							</table>
						
					</fieldset>
				</td>
				<td style="width: 40%;">
					<fieldset style="width: 98%;height: 490px;">
						<legend>&nbsp;&nbsp;下级用户(白名单)&nbsp;&nbsp;</legend>
						
							<input type="hidden" id="userids" name="userids" value="">
							<table width="100%" class="inputTable" border="0">
								<tr>
									<td align="center" style="padding-left: 5px" colspan="2">
										<div id="tree_1" style="padding-left: 5px;height: 720px;overflow:auto;"></div>
									</td>
								</tr>
							</table>
						
					</fieldset>
				</td>
			</tr>
			
		</table>
				<table width="100%" class="inputTable">
					<tr>
						<td colspan="7" style="text-align: center;height: 35px">
							<a class="a_button" href="javascript:void(0)" onclick="submitAuthGroupForUser()" id="save" name="save"><span>保存</span></a>&nbsp;&nbsp;
							<a class="a_button" href="${ctx}/external/shieldplan!findPage.do?items=${serverId }"><span>返回</span></a>
						</td>
					</tr>
				</table>
		</form>
	</div>
</body>
</html>