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
		<span class="win-header-title">网管设备 &gt; 设备状态 &gt; 交换机</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm"  method="post">
			<table width="90%" border="1" cellspacing="0" cellpadding="0">
				<thead>
					<tr>						
						<th width="90%" height = "30px" style="text-align: center;" colspan = "9">总览</th>
					</tr>
					<tr>						
						<th width="10%" height = "30px" style="text-align: center; ">设备资源ID</th>
						<th width="10%" style="text-align: center;">内存使用百分比</th>
						<th width="10%" style="text-align: center;">CPU占用率</th>
						<th width="10%" style="text-align: center;">端口个数</th>			
						<th width="10%" style="text-align: center;">状态记录时间</th>								
					</tr>
				</thead>
				<tbody>
				    	<td height = "25px" style="text-align: center;">${switchStateRecord.switchStateRecord.switchid}</td>
				    	<td style="text-align: center;">${switchStateRecord.switchStateRecord.memory}</td>
						<td style="text-align: center;">${switchStateRecord.switchStateRecord.cpu}</td>
						<td style="text-align: center;">${switchStateRecord.switchStateRecord.portNum}</td>
						<td style="text-align: center;">${switchStateRecord.switchStateRecord.recordTime}</td>
			    	</tr>
				
				</tbody>				
			</table>
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				
				<tr>
					
					 <td height="12"></td>	
				</td>
				</tr>			
			</table>
			
			
			<table width="90%" border="1" cellspacing="0" cellpadding="0">
				<thead>
					<tr>						
						<th width="90%" height = "30px" style="text-align: center;" colspan = "6">端口信息</th>
					</tr>
					<tr>						
						<th width="15%" height = "30px" style="text-align: center;">端口</th>
						<th width="15%" style="text-align: center;">端口类型</th>				
						<th width="15%" style="text-align: center;">端口速率</th>				
						<th width="15%" style="text-align: center;">端口状态</th>				
						<th width="15%" style="text-align: center;">端口远程IP</th>				
						<th width="15%" style="text-align: center;">端口丢包数</th>				
					</tr>
				</thead>
				<tbody>
				<c:forEach var="switchStateRecordPort" items="${switchStateRecord.switchStateRecordPort}">		
				    	<td height = "25px" style="text-align: center;">${switchStateRecordPort.port}</td>
				    	<td style="text-align: center;">${switchStateRecordPort.type}</td>
						<td style="text-align: center;">${switchStateRecordPort.rate}</td>
						<td style="text-align: center;">${switchStateRecordPort.state}</td>
						<td style="text-align: center;">${switchStateRecordPort.ip}</td>
						<td style="text-align: center;">${switchStateRecordPort.loss}</td>
			    	</tr>
				</c:forEach>
				</tbody>	
							
			</table>
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				
				<tr>
					 <td height="12"></td>					
				</td>
				</tr>			
			</table>
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				
				<tr>
					<td  style="text-align: center;">			
						<a class="a_button" href="javascript:void(0);" onclick="return exportList('${switchStateRecord.switchStateRecord.switchid}','4');"><span>导出</span></a>					
						<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a>
				</td>
				</tr>			
			</table>
				
		</form>
	</div>
</body>
</html>