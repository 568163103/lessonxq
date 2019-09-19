<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headFormTree.jsp"%>
	<style type="text/css">
		.el_highlight{
			background-color:#95cffc;
		}
	</style>
	<title>${global_app_name}</title>
	<script type="text/javascript">
		function copyUserTreeToUser() {
			var keys = [];
			var tree = $("#tree_0").dynatree('getTree');
			$.map(tree.getSelectedNodes(), function(node){
				var parentNode = node.getParent();var parentId = null;
				if(parentNode && parentNode.data.title){
					var parentIds = parentNode.data.key.split("---");
					parentId = parentIds[parentIds.length-1];
				} else {
					parentId = '${cmuId}';
				}
				var key = node.data.key.split("---")
				keys[keys.length] = parentId + "---" +key[key.length-1]+"---"+node.data.title;
			});
			if(keys.length == 0){
				alert("请选择右侧节点！");
				return;
			}
			$("#resIds").val(keys.join(";"))
			$("body").loading("正在复制，请稍等！");
			$("#userForm").submit();
		}
		$(function(){
			$("#tree_0").dynatree({
				checkbox: true,
				selectMode: 3,
				autoCollapse: false,
				fx: { height: "toggle", duration: 200 },
				imagePath:"${ctx}/css/dynatree/images/",
				children:${userResourceTree}
			});
		});
	</script>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">业务管理 &gt; 用户管理 &gt; 复制目录树</span>
</div>
<div class="win-bodyer">
	<form id="userForm" name="userForm" action="${ctx}/bussiness/usertree!copyUserTreeToUser.do" method="post">
		<input type="hidden" id="uid" name="uid" value="${param.uid}">
		<input type="hidden" id="resIds" name="resIds" value="">
		<table width="100%" class="inputTable" border="0">
			<tr>
				<td align="center">
					<div id="targetUserDiv" align="center">
						<fieldset style="width: 96%;height: 790px;">
							<legend>&nbsp;&nbsp;目标用户&nbsp;&nbsp;</legend>
							<table width="98%" border="0" cellspacing="0" cellpadding="0" class="inputTable">
								<c:forEach var="user" items="${users}" varStatus="status">
									<c:if test="${(status.count % 5) == 1}">
										<tr>
									</c:if>
									<td style="text-align:right;">
											${user.name}：
									</td>
									<td style="text-align:left;">
										<input type="checkbox" name="uids" value="${user.id}" />
									</td>
									<c:if test="${status.last && ((status.count%5) != 0)}">
										<td colspan="${(5-(status.count%5))*2}">&nbsp;</td>
									</c:if>
									<c:if test="${(status.count % 5) == 0 || status.last}">
										</tr>
									</c:if>
								</c:forEach>
								<tr>
									<td colspan="10" style="text-align: center">
										<br><br>
										<a class="a_button" id="targetUserButton" name="targetUserButton" href="javascript:copyUserTreeToUser()"><span>复制</span></a>&nbsp;&nbsp;
										<a class="a_button" href="${ctx}/security/user!findPage.do"><span>返回</span></a>
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				</td>
				<td align="center">
					<fieldset style="width: 90%;height: 790px;">
						<legend>&nbsp;&nbsp;自定义目录树&nbsp;&nbsp;</legend>
						<table class="inputTable" style="width: 100%" border="0">
							<tr>
								<td align="center" style="padding-left: 5px">
									<div id="tree_0" style="padding-left: 5px;height: 720px;overflow:auto;"></div>
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