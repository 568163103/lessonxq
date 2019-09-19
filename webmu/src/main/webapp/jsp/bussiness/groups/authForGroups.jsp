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
		$(function() {
			$("#tree_0").dynatree({
				checkbox: true,
				selectMode: 3,
				autoCollapse: false,
				fx: { height: "toggle", duration: 200 },
				children:${menuTreeJsons},
				onActivate: function(dtnode) {
				},
				onSelect: function(select, node) {
				}
			});
			$("[name=buttonDiv]").autoPos();
		});
		function getRids() {
			var selKeys=[];
			<c:forEach var="menuTreeJson" items="${menuTreeJsons}" varStatus="status">
				tree = $("#tree_${status.index}").dynatree('getTree');
				$.map(tree.getSelectedNodes(), function(node){
					if(node.data.key.substring(6,9)=="${channelValue}")
						selKeys[selKeys.length]=node.data.key;
		        });
			</c:forEach>
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
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">业务管理 &gt; 资源组管理 &gt; 资源组授权 --- ${param.name}</span>
	</div>
	<div class="win-bodyer">
		<div name="buttonDiv" style="background-color:White" width="100%">
			<table cellspacing="0" cellpadding="0">
				<tr>
					<td style="text-align:left;">
						<input type="text" name="queryObject" id="queryObject" value="">&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0)" onclick="queryResource()"><span>查询</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0)" onclick="nextTarget()"><span>下一个</span></a>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<form id="pageForm" name="pageForm" action="${ctx}/bussiness/groups!authForGroupsUP.do" method="post" onsubmit="getRids()">
			<input type="hidden" id="id" name="id" value="${param.id}">
			<input type="hidden" id="rids" name="rids" value="">
			<table cellspacing="0" cellpadding="0">
				<tr>
						<td valign="top"><div id="tree_0"></div></td>
				</tr>
			</table>
		</form>
		<br>
		<div>
			<table cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td style="text-align:left;">
						<br>
						<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>提交</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:location.href='${ctx}/bussiness/groups!findPage.do'"><span>返回</span></a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>