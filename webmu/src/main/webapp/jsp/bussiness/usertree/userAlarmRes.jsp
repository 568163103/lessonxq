<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	function checkedItems() {
		var flag = false;
		$("#pageForm").find("[name=items]").each(function() {
			if (this.checked) {
				flag = true;
			}
		});
		return flag;
	}
	
	
	function updateAllPlan() {
		if (!checkedItems()) {
			alert("请至少选择要查询的通道。");
			return false;
		}
		$("#pageForm").attr("action","${ctx}/bussiness/usertree!updateMenu.do");
		$("#pageForm").submit();
	}
	
	
	
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
	   <c:if test="${not empty serverId}">
	   	   <span class="win-header-title">外域设备管理 &gt; 接入网关  &gt; 目录资源信息</span>
	   </c:if>
	   <c:if test="${empty serverId}">
	   	   <span class="win-header-title">外域设备管理 &gt; 摄像机通道管理 </span>
	   </c:if>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/usertree!findPageForUserAlarmRes.do" method="post">
		<input type="hidden" name="serverId" value="${serverId }">
		<input type="hidden" name="muluid" id="muluid" value="${pageObject.params.codec }">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">通道ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">通道名称：</td>
					<td><input type="text" id="pageObject.params.channelName" name="pageObject.params.channelName" value="${pageObject.params.channelName}"></td>
					<td style="text-align: right"></td>
					<td></td>
				 </tr>
				 <tr>
					<td style="text-align: right">接入网关：</td>
					<td>
					<select id="pageObject.params.encodeName" name="pageObject.params.encodeName">
							<option value="">全部</option>
							<c:forEach var="mdu" items="${platforms}">
								<option value="${mdu.key}" <c:if test="${pageObject.params.encodeName eq mdu.key}">selected</c:if>>${mdu.value}</option>
							</c:forEach>
					</select>	</td>				
					<td style="text-align: right">是否订阅：</td>
					<td>
						<select id="pageObject.params.status" name="pageObject.params.status">
							<option value="">全部</option>
							<option value="1" <c:if test="${pageObject.params.status eq '1'}">selected</c:if>>是</option>
							<option value="0" <c:if test="${pageObject.params.status eq '0'}">selected</c:if>>否</option>
						</select>
					</td>
					<td style="text-align: right">用户：</td>
					<td>
					<select id="userid" name="pageObject.params.userid" onchange="selectSupp()">
							<option value=""></option>
							<c:forEach var="mdu" items="${users}">
								<option value="${mdu.key}" <c:if test="${pageObject.params.userid eq mdu.key}">selected</c:if>>${mdu.value}</option>
							</c:forEach>
					</select>	</td>	
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="channlePageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
				<tr>
					<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
					<th width="9%"><div>通道ID</div></th>
					<th width="16%">通道名称</th>
					<th width="12%">编码器名称</th>
					<th width="10%">在线</th>
					<th width="19%">目录树分组名</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="channel" items="${pageObject.resultList}">
					<tr <c:if test="${channel.status  eq '0'}">class="font-red" </c:if>>
						<td class="center">
							<input type="checkbox" name="items" class="items" value="${channel.id}|${channel.name}"/>
						</td>
						<td>${channel.id}</td>
						<td>${channel.name}</td>
						<td>${channel.encoderName}</td>
						<td><c:if test="${channel.status  eq '1'}">已订阅</c:if><c:if test="${channel.status  eq '0'}">未订阅</c:if></td>
						<td>${channel.description}</td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr>
					<td colspan="6">
						<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
					</td>
				</tr>
				
				</tfoot>
			</table>
		</div>
		
	</form>
	</div>
</body>
</html>