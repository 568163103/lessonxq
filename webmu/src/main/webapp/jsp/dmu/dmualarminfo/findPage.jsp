<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	function handleAlarm(id,status){
		window.location.href = "${ctx}/dmu/dmuAlarmInfo!handleAlarm.do?sid="+id+"&status="+status;
	}
	
	function showhandleAlarm(id,status) {
		
				$.ajax({  
                    type : "POST",  //提交方式  
                    url : "${ctx}/dmu/dmuAlarmInfo!
							.do",//路径
                    data : {  
                        "id" : id,"status":status 
                    },//数据，这里使用的是Json格式进行传输  
                    success : function(result) {//返回数据根据结果进行相应的处理  
                        if (result.code=='200' ) {  
                        	location.reload();
            	   			return true; 
                        } 
                    }  
                });
	}
	
	function exportList(){
		$("#pageForm").attr("action","${ctx}/dmu/dmuAlarmInfo!exportList.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/dmu/dmuAlarmInfo!findPage.do");
	}
	
	

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">网管管理 &gt; 告警查询 </span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/dmu/dmuAlarmInfo!findPage.do" method="post">
		<div class="win-advsearch">
			<table width="100%">
				<tr>
					<td>设备编号：
						<input type="text" id="pageObject.params.sourceId" name="pageObject.params.sourceId" value="${pageObject.params.sourceId }"/>
					</td>					
					<td>设备名称：
						<input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name }"/>
					</td>
					<td>警告类型：
						<select class="validate[required]" name="pageObject.params.alarmType" id="alarmType">
							<option value="">请选择</option>
							<c:forEach var="type" items="${alarmTypes}">
								<option value="${type.value}" <c:if test="${pageObject.params.alarmType eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>告警级别：
						<select class="validate[required]" name="pageObject.params.alarmLevel" id="level">
							<option value="">请选择</option>
							<c:forEach var="type" items="${levels}">
								<option value="${type.value}" <c:if test="${pageObject.params.alarmLevel eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>开始时间：
						<input type="text" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss' ,maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate" style="width:145px;" id="beginTime" name="pageObject.params.beginTime" value="${pageObject.params.beginTime }"/>
					</td>
					<td>结束时间：
						<input type="text" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}' })" class="Wdate" style="width:145px;" id="endTime" name="pageObject.params.endTime" value="${pageObject.params.endTime }"/>
					</td>
					<td align="right">
						<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>查询</span></a>
					</td>
				</tr>
			</table>
		</div>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th width="7%"><div>设备编号</div></th>
						<th width="5%">设备名称</th>
						<th width="5%">告警类型</th>
						<th width="5%">告警级别</th>
						<th width="5%">开始时间</th>
						<th width="5%">结束时间</th>
						<th width="3%">告警状态</th>						
						<th width="40%">备注</th>
						<th width="5%">操作</th>
						
					</tr>
				</thead>
				<tbody>
				<c:forEach var="alarmInfo" items="${pageObject.resultList}">
			    	<tr <c:if test="${alarmInfo.alarmLevel == '1'}">class="font-red" </c:if>
					 <c:if test="${alarmInfo.alarmLevel == '2'}">class="font-brown" </c:if>	>
				    	<td>${alarmInfo.sourceId}</td>
				    	<td>${alarmInfo.name}</td>
						<td>${alarmInfo.dmuAlarmTypeName}</td>
						<td>${alarmInfo.dmuAlarmLevelName}</td>
				    	<td>${alarmInfo.beginTime}</td>
						<td>${alarmInfo.endTime}</td>
						<td <c:if test="${alarmInfo.state == '1'}">style="color: red"</c:if>>
						<c:if test="${alarmInfo.state == '1'}">待确认</c:if>
						<c:if test="${alarmInfo.state == '2'}">处理中</c:if>
						<c:if test="${alarmInfo.state == '3'}">待归档</c:if>
						<c:if test="${alarmInfo.state == '4'}">已归档</c:if></td>
						<td>${alarmInfo.description}</td>
						<td><c:if test="${alarmInfo.state == '1'}"><a href="javascript:handleAlarm('${alarmInfo.id}','2')">告警确认</a></c:if>
						<c:if test="${alarmInfo.state == '2'}"><a href="javascript:handleAlarm('${alarmInfo.id}','3')">告警消除</a>&nbsp;&nbsp;<a href="javascript:handleAlarm('${alarmInfo.id}','1')">取消确认</a></c:if>
						<c:if test="${alarmInfo.state == '3'}"><a href="javascript:handleAlarm('${alarmInfo.id}','4')">归档</a>&nbsp;&nbsp;
						<a href="javascript:handleAlarm('${alarmInfo.id}','2')">取消消除</a></c:if></td>
						
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="9">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="9" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:void(0);" onclick="return exportList();"><span>导出</span></a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>