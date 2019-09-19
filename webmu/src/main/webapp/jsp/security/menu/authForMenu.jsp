	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
var isOpen = false;
function openViewMenu(rid){
	isOpen = true;
	$("#main-iframe").attr("src","${ctx}/security/menu!viewMenu.do?rid="+rid);
}
function initRole(){
	var menuRoleIds = ${menuRoleIds};
	var ridAllFlag=true;
	$("[name='rids']").each(function(){
		if($.inArray($(this).val(),menuRoleIds)!=-1) {
			$(this).attr("checked",true);
		} else { 
			$(this).attr("checked",false);
			ridAllFlag=false;
		}
		if(ridAllFlag){
			$("#ridAll").attr("checked",true);
		}
	}).click(function (){
		var flag=true;
		$("[name=rids]").each(function(){
			if(!this.checked){
				flag = false;
			}
		});
		if( flag ){
			$("#ridAll").attr("checked",true);
		} else {
			$("#ridAll").attr("checked",false);
		}
	});
	$("#ridAll").click(function (){
		if(this.checked){				 //如果当前点击的多选框被选中
			$("[name=rids]").attr("checked", true );
		}else{								
			$("[name=rids]").attr("checked", false );
		}
	});
}
$(function(){
	initRole();
	$("#viewMenu").dialog({
		title: "包含功能信息",
		autoOpen: false,
		width : 500,
		modal: true
	});
	$("#main-iframe").load(function(){
		if(isOpen){
			$("#viewMenu").dialog("open");
		}
	});
});
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">权限管理 &gt; 菜单管理 &gt; 菜单授权 --- ${menu.name}</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/security/menu!authForMenu.do" method="post">
		<input type="hidden" id="mid" name="mid" value="${menu.mid}">
		<fieldset title="分配角色" style="width: 98%">
			<legend>&nbsp;&nbsp;分配角色&nbsp;&nbsp;<input type="checkbox" id="ridAll" value="" /></legend>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			    <c:forEach var="role" items="${roles}" varStatus="status">
					<c:if test="${(status.count % 8) == 1}">
					<tr>
					</c:if>
						<td style="text-align:right;">
							<a href="javascript:void(0)" onclick="openViewMenu(${role.rid});" >${role.rname}</a>：
						</td>
						<td style="text-align:left;">
						<input type="checkbox" name="rids" value="${role.rid}" />
						</td>
					<c:if test="${status.last && ((status.count%8) != 0)}">
						<td colspan="${(8-(status.count%8))*2}">&nbsp;</td>
					</c:if>
					<c:if test="${(status.count % 8) == 0 || status.last}">
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</fieldset>
		<table width="100%">
			<tr border="0" cellspacing="0" cellpadding="0">
				<td style="text-align:center;">
				<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>提交</span></a>&nbsp;&nbsp;
				<a class="a_button" href="javascript:location.href='${ctx}/security/menu!findPage.do'"><span>返回</span></a>
				</td>
			</tr>
		</table>
	</form>
	</div>
	<div id="viewMenu">
		<iframe id="main-iframe" width="100%" frameBorder="0" scrolling ="auto"
					allowtransparency=yes src=""></iframe>
	</div>
</body>
</html>