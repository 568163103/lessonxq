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
		$('input[name="groupRight"]').click(function(){
			var isChecked = $(this).is(':checked');
			var groupRight = $(this).val();
			userGroupsTree0.dynatree("getTree").visit(function(node){
				if (node.data.key.indexOf("---") > 0) {
					if (groupRight == node.data.key.split("---")[1]) {
						node.select(isChecked);
					}
				}
			});
		});
	});
	function getGids() {
		var selKeys={};
		tree = $("#tree_0").dynatree('getTree');
		$.map(tree.getSelectedNodes(), function(node){
			if(node.data.key.indexOf("---")>0){
				var userGroupRight = selKeys[node.data.key.split("---")[0]];
				if(null == userGroupRight){
					selKeys[node.data.key.split("---")[0]]=node.data.key.split("---")[1];
				} else {
					selKeys[node.data.key.split("---")[0]]=(userGroupRight|node.data.key.split("---")[1]);
				}
			}
		});
		$("#gids").val($.map(selKeys,function(value,key) {
			return key+"---"+value;
		}).join(','));
	}
	function getUserGroups(currTr,uid){
		$("#pageDiv").find(".font-red").removeClass("font-red");
		$(currTr).addClass('font-red');
		$('#uids').val(uid);
		$.post("${ctx}/bussiness/usertree!beforeAuthGroupsForUserGroup.do?uid="+uid,function(data){
			userGroupsTree0.dynatree("getRoot").removeChildren();
			userGroupsTree0.dynatree("getRoot").addChild(data);
		});
	}
	function submitAuthGroupForUser(){
		getGids();
		$.post("${ctx}/bussiness/usertree!authGroupsForUserGroup.do",$("#groupsPageForm").serializeArray(),function(data){
			if(data && "ok"==data){
				alert("保存成功！")
			} else {
				alert("保存失败！")
			}
		});
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 用户资源组</span>
	</div>
	<div class="win-bodyer">
		<table width="98%" border="0">
			<tr>
				<td style="width: 40%;">
					<fieldset style="width: 98%;height: 790px;">
						<legend>&nbsp;&nbsp;用户组&nbsp;&nbsp;</legend>
						<form id="userPageForm" name="pageForm" action="${ctx}/bussiness/usertree!findPageUserGroupForAuthGroups.do" method="post">
							<table width="98%" class="inputTable">
								<tr>
									<td style="text-align: right">
										用户组名：<input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}">
										&nbsp;&nbsp;
										<a class="a_button" href="javascript:void(0)" onclick="$('#userPageForm').submit();">
											<span>查找</span>
										</a>
									</td>
								</tr>
							</table>
							<div id="pageDiv" name="pageDiv" class="pageDiv">
								<table width="98%" border="0" cellspacing="0" cellpadding="0">
									<thead>
									<tr>
										<th >用户组名</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach var="user" items="${pageObject.resultList}">
										<tr class="hint" onclick="getUserGroups(this,'${user.id}');">
											<td>${user.name}</td>
										</tr>
									</c:forEach>
									</tbody>
									<tfoot>
									<tr>
										<td >
											<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
										</td>
									</tr>
									</tfoot>
								</table>
							</div>
						</form>
					</fieldset>
				</td>
				<td>
					<fieldset style="width: 100%;height: 790px;">
						<legend>&nbsp;&nbsp;点击左侧用户分配资源组&nbsp;&nbsp;</legend>
						<form id="groupsPageForm" name="pageForm" action="${ctx}/bussiness/usertree!authAlarmResForUser.do" method="post">
							<input type="hidden" id="uids" name="uids" value="">
							<input type="hidden" id="gids" name="gids" value="">
							<table width="100%" class="inputTable" border="0">
								<tr>
									<td align="center" style="padding-left: 5px" colspan="2">
										<div id="tree_0" style="padding-left: 5px;height: 720px;overflow:auto;"></div>
									</td>
								</tr>
								<tr>
									<td style="text-align:left;padding-left: 50px;vertical-align: middle;width: 50%;">
										权限总控：
										<c:forEach var="groupRight" items="${groupRights}">
											&nbsp;&nbsp;<input type="checkbox" name="groupRight" value="${groupRight.value}">${groupRight.label}
										</c:forEach>
									</td>
									<td style="text-align:left;padding-top:10px;padding-left: 50px;vertical-align: middle">
										<a class="a_button" href="javascript:void(0)" onclick="submitAuthGroupForUser()"><span>保存</span></a>&nbsp;&nbsp;
										<a class="a_button" href="${ctx}/bussiness/userGroup!findPage.do"><span>返回</span></a>
									</td>
								</tr>
							</table>
						</form>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>