<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormPage.jsp"%>
<script type="text/javascript">
	function exportList(id,type){
		$("#pageForm").attr("action","${ctx}/dmu/dmuEquipment!exportList.do?id="+id+"&type="+type);
		$("#pageForm").submit();
	}
</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">网管设备 &gt; 设备状态 &gt; 磁盘阵列</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm"  method="post">
			<input type="hidden" id="id" name="id" value="${diskStateRecord.diskid}">
			<table width="90%" class="inputTable">
				<tr>
					<td style="text-align: right;">设备资源ID：</td>
					<td><input type="text" value="${diskStateRecord.diskid}" class="validate[required]" name="diskStateRecord.diskid" id="diskid" readonly="true"/></td>
					<td style="text-align: right;">总体容量：</td>
					<td><input type="text" value="${diskStateRecord.totalvolume}" class="validate[required]" name="diskStateRecord.totalvolume" id="totalvolume" readonly="true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">未分配容量：</td>
					<td><input type="text" value="${diskStateRecord.undistributed}" class="validate[required]" name="diskStateRecord.undistributed" id="undistributed" readonly="true"/></td>
					<td style="text-align: right;">存储端口数量：</td>
					<td><input type="text" value="${diskStateRecord.portNum}" class="validate[required]" name="diskStateRecord.portNum" id="portNum" readonly="true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">CPU温度：</td>
					<td><input type="text" value="${diskStateRecord.cpu}" class="validate[required]" name="diskStateRecord.cpu" id="cpu" readonly="true"/></td>
					<td style="text-align: right;">风扇工作状态：</td>
					<td><input type="text" value="${diskStateRecord.fan}" class="validate[required]" name="diskStateRecord.fan" id="fan" readonly="true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">磁盘坏块数量：</td>
					<td><input type="text" value="${diskStateRecord.bad}" class="validate[required]" name="diskStateRecord.bad" id="bad" readonly="true"/></td>
					<td style="text-align: right;">磁盘阵列状态：</td>
					<td><input type="text" value="${diskStateRecord.state}" class="validate[required]" name="diskStateRecord.state" id="state" readonly="true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">状态记录时间：</td>
					<td><input type="text" value="${diskStateRecord.recordTime}" class="validate[required]" name="diskStateRecord.recordTime" id="recordTime" readonly="true"/></td>
					<td </td>
					<td></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0);" onclick="return exportList('${diskStateRecord.diskid}','3');"><span>导出</span></a>
						<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
		
	</div>
</body>
</html>