<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headPage.jsp"%>
	<title>${global_app_name}</title>
</head>
<body class="main_bg">
<div id="channlePageDiv" name="pageDiv" class="pageDiv">
	<table id="channlePageTable" align="center" width="98%" border="0" cellspacing="0" cellpadding="0">
		<thead>
		<tr>
			<th width="15%">视频通道ID</th>
			<th width="15%">视频通道名称</th>
			<th width="10%">编码器名称</th>
			<th width="10%">存储服务器</th>
			<c:if test="${empty param.id}">
			<th width="10%">通道类型</th>
			</c:if>
			<th width="7%">存储方案</th>
			<th width="5%">云台启用</th>
			<th width="5%">音视频启用</th>
			<th width="5%">流媒体个数</th>
			<th width="5%">启用</th>
			<th width="10%">描述</th>
			<c:if test="${not empty param.id}">
				<th width="3%">操作</th>
			</c:if>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="channel" items="${channels}">
			<tr>
				<td>${channel.id}</td>
				<td>${channel.name}</td>
				<td>${encoder.name}</td>
				<td>${msuChannel.serverName}</td>
				<c:if test="${empty param.id}">
				<td>
					<select class="validate[required]" name="channel.cameraType" id="cameraType">
						<option value="">请选择</option>
						<c:forEach var="cameraType" items="${cameraTypes}">
							<option value="${cameraType.value}"  <c:if test="${type eq cameraType.value}">selected</c:if>>${cameraType.label}</option>
						</c:forEach>
					</select>
				</td>
				</c:if>
				<td>${channel.recordPlanName}</td>
				<td>${channel.hasPtzZh}</td>
				<td>${channel.hasAudioZh}</td>
				<td>${channel.streamCount}</td>
				<td>${channel.enabledZh}</td>
				<td>${channel.description}</td>
				<c:if test="${not empty param.id}">
				<td>
					<c:if test="${not empty channel.id}">
					<a href="javascript:editChannel('${channel.id}','${encoder.id}')">编辑</a>
					</c:if>
				</td>
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>
