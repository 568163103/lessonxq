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
	$(function(){
	    $("#pageForm").validationEngine();
		userGroupsTree0 = $("#tree_0");
		userGroupsTree0.dynatree({
			checkbox: true,
			selectMode: 3,
			autoCollapse: false,
			imagePath:"${ctx}/css/dynatree/images/",
			fx: { height: "toggle", duration: 200 },
			children:${treeNode},
			onActivate: function(dtnode) {
			},
			onSelect: function(select, node) {
			}
		});
		$("tr .hint").mouseover(function(){
			$(this).css("cursor", "pointer");
		});
		$("tr .hint :first").click();
		$('input[name="alarmType"]').click(function(){
			var isChecked = $(this).is(':checked');
			var groupRight = $(this).val();
			userGroupsTree0.dynatree("getTree").visit(function(node){
					if (groupRight == node.data.key) {
						node.select(isChecked);
					}
			});
		});
	});
	function getGids() {
		var selKeys="";
		var channel="";
		tree = $("#tree_0").dynatree('getTree');
		$.map(tree.getSelectedNodes(), function(node){
		var key = node.data.key;
		if(key.length>5){
			channel = key;
		}else{
			selKeys += channel+"---"+key+",";
		}
		});
		if(selKeys!=""){
			selKeys = selKeys.substring(0,selKeys.length-1);	
		}
		$("#gids").val(selKeys);
	}
	
	function submitAuthGroupForUser(){
	    var actiontype = $("#actiontype").val();
		if(actiontype==null||actiontype==""){
			alert("请选择订阅动作！");
			return false;
		}
		
		if(actiontype=='2'||actiontype=='3'){
		  getGids();
		  if($("#gids").val()==null||$("#gids").val()==""){
		  		alert("请选择需要订阅的告警资源！");
			    return false;
		  }
		}
		$('#save').attr('onclick','return false;');
		$.post("${ctx}/bussiness/usertree!reportSubExternalAlarmRes.do",$("#pageForm").serializeArray(),function(data){
			if(data && "200"==data.code){
				alert("告警订阅成功！")
				$('#save').attr('onclick','submitAuthGroupForUser()');
			} else {
				alert("告警订阅失败！")
				$('#save').attr('onclick','submitAuthGroupForUser()');
			}
		});
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">外域设备管理 &gt; 接入网关  &gt; 告警资源订阅</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/usertree!reportSubExternalAlarmRes.do" method="post">
		<table width="98%" border="0">
			<tr>
				<td style="width: 40%;">
					<fieldset style="width: 98%;height: 790px;">
						<legend>&nbsp;&nbsp;订阅&nbsp;&nbsp;</legend>
						
							<input type="hidden" id="gids" name="gids" value="">
							<input type="hidden" id="serverId" name="serverId" value="${serverId }">
							<table width="100%" class="inputTable" border="0">
								<tr>
									<td align="center" style="padding-left: 5px" colspan="2">
										<div id="tree_0" style="padding-left: 5px;height: 720px;overflow:auto;"></div>
									</td>
								</tr>
							</table>
						
					</fieldset>
				</td>
				<td>
					<fieldset style="width: 100%;height: 790px;">
						<legend>&nbsp;&nbsp;操作&nbsp;&nbsp;</legend>
							<table width="98%" class="inputTable">
								<tr>
										<td style="text-align:left;padding-left: 50px;vertical-align: middle;width: 30%;">
										告警类型总控：
										<c:forEach var="alarmType" items="${alarmTypes}" >
										<tr>
										<td style="text-align:left;padding-left: 50px;">
										   <input type="checkbox" name="alarmType" value="${alarmType.value}">${alarmType.label}
										</td>
										</tr>
										</c:forEach>
									</td>
								 </tr>
								 <tr>
									<td style="text-align: left;padding-left: 50px;">
										<label class="field-request">* </label>告警订阅动作：
										<select name="actiontype" id="actiontype" class="validate[required]">
										  <option value ="">请选择</option>	
										  <option value ="0">全部上报</option>
										  <option value ="1">全部删除</option>
										  <option value ="2">部分增加</option>
										  <option value ="3">部分删除</option>
										</select>
									</td>
								</tr>
								<tr>
									<td style="text-align:left;padding-top:10px;padding-left: 50px;vertical-align: middle">
										<a class="a_button" href="javascript:void(0)" onclick="submitAuthGroupForUser()" id="save" name="save" ><span>保存</span></a>&nbsp;&nbsp;
										<a class="a_button" href="${ctx}/device/encoder!findPlatformPage.do"><span>返回</span></a>
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