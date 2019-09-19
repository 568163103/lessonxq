<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headFormTree.jsp"%>
	<title>${global_app_name}</title>
	<script type="text/javascript">
		$(function() {
			<c:forEach var="menuTreeJson" items="${menuTreeJsons}" varStatus="status">
				$("#tree_${status.index}").dynatree({
					checkbox: true,
					selectMode: 3, 
					autoCollapse: false,
					fx: { height: "toggle", duration: 200 },
					children:${menuTreeJson},
					onActivate: function(dtnode) {
				    },
				    onSelect: function(select, node) {
					}
				});
			</c:forEach>
		});
		function getMids() {
			var selKeys=[];
			<c:forEach var="menuTreeJson" items="${menuTreeJsons}" varStatus="status">
				tree = $("#tree_${status.index}").dynatree('getTree');
				$.map(tree.getSelectedNodes(), function(node){
					selKeys[selKeys.length]=node.data.key;
		        });
			</c:forEach>
			$("#mids").val(selKeys.join(","));
		}
	</script>
</head>
<body>
	<div class="win-header" id="a">
		<span class="win-header-title">权限管理 &gt; 角色管理 &gt; 角色授权 --- ${param.rolename}</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/security/role!authForRoleUP.do" method="post" onsubmit="getMids()">
		<input type="hidden" id="rid" name="rid" value="${param.rid}">
		<input type="hidden" id="mids" name="mids" value="">
		<table cellspacing="0" cellpadding="0" width="100%">
			<tr>			
			    <c:forEach var="menuTreeJson" items="${menuTreeJsons}" varStatus="status">
					<td valign="top"><div id="tree_${status.index}"></div></td>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="${fn:length(menuTreeJsons)}" style="text-align:left;">
					<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>提交</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:location.href='${ctx}/security/role!findPage.do'"><span>返回</span></a>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>