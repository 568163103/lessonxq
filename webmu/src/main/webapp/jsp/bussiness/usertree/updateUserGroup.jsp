<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
<link rel="stylesheet" href="${ctx}/css/dynatree/ui.dynatree.css" />
<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.dynatree.js"></script>
<style type="text/css">
textarea {
	width: 203px;
}
</style>
<title>${global_app_name}</title>
<script type="text/javascript">
var isOpen = false;

	function edit() {
		var amid = $("#amid").val();
		var groupIdd = $("#groupIdd").val();
		var groupId = $("#groupId").val();
		
			if (confirm("确认要更新？")) {
			  $.post("${ctx}/bussiness/usertree!updateUserGroup.do?amid="+amid+"&groupId="+groupId+"&groupIdd="+groupIdd,null,function(data){
					if("true" == data){
						$("#groupId").val(groupIdd);
						alert("更新成功");
					} else {
						alert("更新失败");
					}
				});
			}
	}
	function selectSupp(){
		
		var objS = document.getElementById("group");//获取配送员的信息
		var userid = objS.options[objS.selectedIndex].value;//获取配送员下拉选定的数据
		$("#groupIdd").val(userid);//获取配送员下拉选定的数据
		
	 } 
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">权限管理 &gt; 用户管理 &gt; 用户分组</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/bussiness/usertree!updateUserGroup.do" method="post">
			<input type="hidden" id="amid" name="amid" value="${amid}">
			<input type="hidden" id="groupId" name="groupId" value="${groupId}">
			<input type="hidden" id="groupIdd" name="groupIdd" value="${groupIdd}">
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right;" width="22%"><label class="field-request">* </label>用户ID：</td>
					<td width="30%"><input type="text" value="${amid}" readonly="readonly" name="userInfo.name" id="user" /></td>
					<td style="text-align:right;">用户组：</td>
					<td>
						<select name="group" id="group" onchange="selectSupp()">
							<option value="">请选择</option>
							<c:forEach var="avLevel" items="${groupList}">
								<option value="${avLevel.id}" <c:if test="${groupId eq avLevel.id}">selected</c:if>>${avLevel.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0)" onclick="javascript:edit()"><span>更新</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="viewMenu">
		<iframe id="main-iframe" width="100%" frameBorder="0" scrolling="auto" allowtransparency=yes src=""></iframe>
	</div>
</body>
</html>