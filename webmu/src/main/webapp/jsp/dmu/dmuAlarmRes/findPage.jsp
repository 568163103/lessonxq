<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>

</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">网管管理 &gt; 告警资源查询 </span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/dmu/dmuAlarmRes!findPage.do" method="post">
		<div class="win-advsearch">
			<table width="80%">
				<tr>
					<td>设备编号：
						<input type="text" id="pageObject.params.resId" name="pageObject.params.resId" value="${pageObject.params.resId }"/>
					</td>					
					<td>设备名称：
						<input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name }"/>
					</td>
					<td>故障类型：
						<select class="validate[required]" name="pageObject.params.alarmType" id="alarmType">
							<option value="">请选择</option>
							<c:forEach var="type" items="${alarmTypes}">
								<option value="${type.id}" <c:if test="${pageObject.params.alarmType eq type.id}">selected</c:if>>${type.name}</option>
							</c:forEach>
						</select>
					</td>					
					<td align="right">
						<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>查询</span></a>
					</td>
				</tr>
			</table>
		</div>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="80%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th width="10%"><div>设备编号</div></th>
						<th width="10%">设备名称</th>
						<th width="10%">告警类型</th>				
						<th width="10%">描述</th>
						
					</tr>
				</thead>
				<tbody>
				<c:forEach var="alarmInfo" items="${pageObject.resultList}">
			    	<tr>
				    	<td>${alarmInfo.resId}</td>
				    	<td>${alarmInfo.name}</td>
						<td>${alarmInfo.alarmType}</td>
						<td>${alarmInfo.description}</td>
						
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
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