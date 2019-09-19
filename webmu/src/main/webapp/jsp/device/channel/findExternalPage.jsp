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
	
	function deleteAll() {
		if (!checkedItems()) {
			alert("请至少选择要删除通道。");
			return false;
		}
		msg = "确认要删除所选通道？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/device/channel!deleteExternalChannel.do");
		}
		$("#pageForm").submit();
	}
	
	function updateAllPlan() {
		if (!checkedItems()) {
			alert("请至少选择要查询的通道。");
			return false;
		}
		$("#pageForm").attr("action","${ctx}/device/channel!updateCamResState.do");
		$("#pageForm").submit();
	}
	
	function exportList(){
		$("#pageForm").attr("action","${ctx}/device/channel!exportListExternal.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/device/channel!findExternalPage.do");
	}
	
	function editChannel(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/device/channel!beforeUpdate.do");
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
	<form id="pageForm" name="pageForm" action="${ctx}/device/channel!findExternalPage.do" method="post">
		<input type="hidden" name="serverId" value="${serverId }">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">通道ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">通道名称：</td>
					<td><input type="text" id="pageObject.params.channelName" name="pageObject.params.channelName" value="${pageObject.params.channelName}"></td>
					<td style="text-align: right">接入网关：</td>
					<td>
					<select id="pageObject.params.encodeName" name="pageObject.params.encodeName">
							<option value="">全部</option>
							<c:forEach var="mdu" items="${platforms}">
								<option value="${mdu.key}" <c:if test="${pageObject.params.encodeName eq mdu.key}">selected</c:if>>${mdu.value}</option>
							</c:forEach>
					</select>	
					</td>
				 </tr>
				 <tr>
					<td style="text-align: right">位置：</td>
					<td>
						<select name="pageObject.params.position" id="pageObject.params.position">
							<option value="">全部</option>
							<c:forEach var="type" items="${positions}">
								<option value="${type.key}" <c:if test="${pageObject.params.position eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right">是否在线：</td>
					<td>
						<select id="pageObject.params.status" name="pageObject.params.status">
							<option value="">全部</option>
							<option value="true" <c:if test="${pageObject.params.status eq 'true'}">selected</c:if>>是</option>
							<option value="false" <c:if test="${pageObject.params.status eq 'false'}">selected</c:if>>否</option>
						</select>
					</td>
					<td></td>
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
					<th width="15%">安装位置</th>
					<th width="15%">摄像机用途</th>
					<th width="10%">在线</th>
					<th width="19%">描述</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="channel" items="${pageObject.resultList}">
					<tr <c:if test="${!channel.status}">class="font-red" </c:if>>
						<td class="center">
							<input type="checkbox" name="items" class="items" value="${channel.id}"/>
						</td>
						<td>${channel.id}</td>
						<td>${channel.name}</td>
						<td>${channel.encoderName}</td>
						<td>${channel.location}</td>
						<td>${channel.purpose}</td>
						<td>${channel.statusZh}</td>
						<td>${channel.description}</td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr>
					<td colspan="8">
						<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
					</td>
				</tr>
				<tr>
					<td colspan="8" style="text-align: center;vertical-align: top;height: 35px">
						<c:if test="${not empty serverId}">
						<a class="a_button" href="javascript:void(0);" onclick="return updateAllPlan();"><span>状态查询</span></a>&nbsp;&nbsp;
						</c:if>
						<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a></a>&nbsp;&nbsp;
						<c:if test="${empty serverId}">
						<a class="a_button" href="javascript:void(0);" onclick="return exportList();"><span>导出</span></a>
						</c:if>
						<c:if test="${not empty serverId}">
						<a class="a_button" href="${ctx}/device/encoder!findPlatformPage.do"><span>返回</span></a>
						</c:if>
					</td>
				</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>