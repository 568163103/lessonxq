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
		<span class="win-header-title">网管设备 &gt; 设备状态 &gt; 服务器</span>
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
						<th width="10%" style="text-align: center;">操作系统信息</th>
						<th width="10%" style="text-align: center;">硬盘分区数</th>
						<th width="10%" style="text-align: center;">内存总容量</th>		
						<th width="10%" style="text-align: center;">内存已使用容量</th>
						<th width="10%" style="text-align: center;">内存剩余容量</th>
						<th width="10%" style="text-align: center;">CPU数量</th>
						<th width="10%" style="text-align: center;">网卡端口数量</th>			
						<th width="10%" style="text-align: center;">状态记录时间</th>									
					</tr>
				</thead>
				<tbody>
				    	<td height = "25px" style="text-align: center;">${serverStateRecord.serverStateRecord.serverid}</td>
				    	<td style="text-align: center;">${serverStateRecord.serverStateRecord.systemInfo}</td>
						<td style="text-align: center;">${serverStateRecord.serverStateRecord.diskNum}</td>
						<td style="text-align: center;">${serverStateRecord.serverStateRecord.totalMem}</td>
						<td style="text-align: center;">${serverStateRecord.serverStateRecord.usedMem}</td>
				    	<td style="text-align: center;">${serverStateRecord.serverStateRecord.leftMem}</td>
						<td style="text-align: center;">${serverStateRecord.serverStateRecord.cpuNum}</td>
						<td style="text-align: center;">${serverStateRecord.serverStateRecord.networkCard}</td>
						<td style="text-align: center;">${serverStateRecord.serverStateRecord.recordTime}</td>
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
						<th width="90%" height = "30px" style="text-align: center;" colspan = "4">硬盘信息</th>
					</tr>
					<tr>						
						<th width="20%" height = "30px" style="text-align: center;">硬盘分区盘符</th>
						<th width="20%" style="text-align: center;">硬盘分区总容量</th>
						<th width="20%" style="text-align: center;">硬盘分区已用容量</th>
						<th width="20%" style="text-align: center;">硬盘分区剩余容量</th>						
					</tr>
				</thead>
				<tbody >
				<c:forEach var="serverStateRecordDisk" items="${serverStateRecord.serverStateRecordDisk}">		
				    	<td height = "25px" style="text-align: center;">${serverStateRecordDisk.driver}</td>
				    	<td style="text-align: center;">${serverStateRecordDisk.total}</td>
						<td style="text-align: center;">${serverStateRecordDisk.used}</td>
						<td style="text-align: center;">${serverStateRecordDisk.left}</td>
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
			
			<table width="90%" border="1" cellspacing="0" cellpadding="0">
				<thead>
					<tr>						
						<th width="90%" height = "30px"style="text-align: center;" colspan = "2">CPU信息</th>
					</tr>
					<tr>						
						<th width="20%"  height = "30px" style="text-align: center;">CPU使用百分比</th>
						<th width="20%" style="text-align: center;">CPU温度</th>				
					</tr>
				</thead>
				<tbody>
				<c:forEach var="serverStateRecordCpu" items="${serverStateRecord.serverStateRecordCpu}">		
				    	<td height = "25px" style="text-align: center;">${serverStateRecordCpu.cpuPercent}</td>
				    	<td style="text-align: center;">${serverStateRecordCpu.cpuTemp}</td>
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
			
			<table width="90%" border="1" cellspacing="0" cellpadding="0">
				<thead>
					<tr>						
						<th width="90%" height = "30px" style="text-align: center;" colspan = "6">网卡信息</th>
					</tr>
					<tr>						
						<th width="15%" height = "30px" style="text-align: center;">网卡端口</th>
						<th width="15%" style="text-align: center;">网卡端口连接状态</th>				
						<th width="15%" style="text-align: center;">网卡端口带宽</th>				
						<th width="15%" style="text-align: center;">网卡端口流量</th>				
						<th width="15%" style="text-align: center;">IP地址</th>				
						<th width="15%" style="text-align: center;">MAC地址</th>				
					</tr>
				</thead>
				<tbody>
				<c:forEach var="serverStateRecordNetcard" items="${serverStateRecord.serverStateRecordNetcard}">		
				    	<td height = "25px" style="text-align: center;">${serverStateRecordNetcard.port}</td>
				    	<td style="text-align: center;">${serverStateRecordNetcard.portState}</td>
						<td style="text-align: center;">${serverStateRecordNetcard.bandwidth}</td>
						<td style="text-align: center;">${serverStateRecordNetcard.dataFlow}</td>
						<td style="text-align: center;">${serverStateRecordNetcard.ip}</td>
						<td style="text-align: center;">${serverStateRecordNetcard.mac}</td>
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
					<a class="a_button" href="javascript:void(0);" onclick="return exportList('${serverStateRecord.serverStateRecord.serverid}','1');"><span>导出</span></a>
					<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a>
				</td>
				</tr>			
			</table>
				
		</form>
	</div>
</body>
</html>