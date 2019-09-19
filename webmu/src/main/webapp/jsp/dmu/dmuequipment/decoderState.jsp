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
		<span class="win-header-title">网管设备 &gt; 设备状态 &gt; 编码器</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm"  method="post">
			<input type="hidden" id="id" name="id" value="${diskStateRecord.diskid}">
			<table width="90%" class="inputTable">
				<tr>
					<td style="text-align: right;">设备资源ID：</td>
					<td><input type="text" value="${diskStateRecord.diskid}" class="validate[required]" name="diskStateRecord.diskid" id="diskid" readonly="true"/></td>
					<td style="text-align: right;">CPU使用率：</td>
					<td><input type="text" value="${diskStateRecord.totalvolume}" class="validate[required]" name="diskStateRecord.totalvolume" id="totalvolume" readonly="true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">内存使用率：</td>
					<td><input type="text" value="${diskStateRecord.undistributed}" class="validate[required]" name="diskStateRecord.undistributed" id="undistributed" readonly="true"/></td>
					<td style="text-align: right;">状态：</td>
					<td><input type="text" value="${diskStateRecord.fan}" class="validate[required]" name="diskStateRecord.fan" id="fan" readonly="true"/></td>
				</tr>
				
				<tr>
					<td style="text-align: right;">状态记录时间：</td>
					<td><input type="text" value="${diskStateRecord.recordTime}" class="validate[required]" name="diskStateRecord.recordTime" id="recordTime" readonly="true"/></td>
					<td </td>
					<td></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
		
	</div>
</body>
</html>