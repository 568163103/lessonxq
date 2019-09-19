<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	function handleAlarm(id){
		window.location.href = "${ctx}/fault/alarmInfo!handleAlarm.do?id="+id;
	}
	
	function showhandleAlarm(id,status,type) {
		jPromptcopy('说明:', '', '处理中、待归档', function(r) {
		if(r == null){
				return true;
			}else{
				$.ajax({  
                    type : "POST",  //提交方式  
                    url : "${ctx}/fault/alarmInfo!handleAlarm.do",//路径  
                    data : {  
                        "id" : id,"status":status,"memo":r  ,"type":type
                    },//数据，这里使用的是Json格式进行传输  
                    success : function(result) {//返回数据根据结果进行相应的处理  
                        if (result.code=='200' ) {  
                        	location.reload();
            	   			return true; 
                        } 
                    }  
                });
                }  
		});
	}
	
	function exportList(){
		$("#pageForm").attr("action","${ctx}/fault/alarmInfo!exportList.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/fault/alarmInfo!findPage.do");
	}
	
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">故障管理 &gt; 告警查询 </span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/fault/alarmInfo!findPage.do" method="post">
		<div class="win-advsearch">
			<table width="100%">
				<tr>
					<td>设备编号：
						<input type="text" id="pageObject.params.sourceId" name="pageObject.params.sourceId" value="${pageObject.params.sourceId }"/>
					</td>
					<td>设备名称：
						<input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name }"/>
					</td>
					<td>开始时间：
						<input type="text" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss' ,maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate" style="width:145px;" id="beginTime" name="pageObject.params.beginTime" value="${pageObject.params.beginTime }"/>
					</td>
					<td>结束时间：
						<input type="text" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}' })" class="Wdate" style="width:145px;" id="endTime" name="pageObject.params.endTime" value="${pageObject.params.endTime }"/>
					</td>
				</tr>
				<tr>

					<td>故障类型：
						<select class="validate[required]" name="pageObject.params.alarmType" id="alarmType">
							<option value="">请选择</option>
							<c:forEach var="type" items="${alarmTypes}">
								<option value="${type.value}" <c:if test="${pageObject.params.alarmType eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>告警级别：
						<select class="validate[required]" name="pageObject.params.alarmLevel" id="level" style="width: 50%;">
							<option value="">请选择</option>
							<c:forEach var="type" items="${levels}">
								<option value="${type.value}" <c:if test="${pageObject.params.alarmLevel eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>告警状态：
						<select class="validate[required]" name="pageObject.params.dealState" id="dealState" style="width: 50%;">
							<option value="">请选择</option>
							<c:forEach var="type" items="${dealStateMap}">
								<option value="${type.key}" <c:if test="${pageObject.params.dealState eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>

					<td align="right" colspan="3">
						<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>查询</span></a>
					</td>
				</tr>
			</table>
		</div>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th width="10%"><div>设备编号</div></th>
						<th width="10%">设备名称</th>
						<th width="10%">告警类型</th>
						<th width="11%">开始时间</th>
						<th width="11%">结束时间</th>
						<th width="5%">告警级别</th>
						<th width="5%">告警状态</th>
						<th width="10%">处理人员</th>
						<th width="10%">处理时间</th>
						<th width="10%">处理备注</th>
						<th width="10%">备注</th>
						<th width="5%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="alarmInfo" items="${pageObject.resultList}">
			    	<tr <c:if test="${alarmInfo.alarmLevel == '1'}">class="font-red" </c:if>
					 <c:if test="${alarmInfo.alarmLevel == '2'}">class="font-brown" </c:if>	 >
				    	<td>${alarmInfo.sourceId}</td>
				    	<td>${alarmInfo.name}</td>
						<td><c:if test="${alarmInfo.alarmType == '11' && fn:substring(alarmInfo.sourceId, 14, 16) != '01'}">摄像机断开</c:if>
						<c:if test="${alarmInfo.alarmType != '11' || fn:substring(alarmInfo.sourceId, 14, 16) == '01'}">${alarmInfo.alarmTypeName}</c:if></td>
				    	<td>${alarmInfo.beginTime}</td>
						<td>${alarmInfo.endTime}</td>
						<td>${alarmInfo.alarmLevelName}</td>
						<td <c:if test="${alarmInfo.dealState == '0'}">style="color: red"</c:if>>
						<c:if test="${alarmInfo.dealState == '0'}">待确认</c:if>
						<c:if test="${alarmInfo.dealState == '1'}">处理中</c:if>
						<c:if test="${alarmInfo.dealState == '2'}">待归档</c:if>
						<c:if test="${alarmInfo.dealState == '3'}">已归档</c:if></td>
						<td>${alarmInfo.dealUserId}</td>
						<td>${alarmInfo.dealTime}</td>
						<td>${alarmInfo.memo}</td>
						<td <c:if test="${fn:length(alarmInfo.description)>700}">title='...${fn:substring(alarmInfo.description, 600, fn:length(alarmInfo.description))}'</c:if>
						<c:if test="${fn:length(alarmInfo.description)<=700}">title='${alarmInfo.description}'</c:if>>
							<c:if test="${fn:length(alarmInfo.description)>18}">${fn:substring(alarmInfo.description, 0, 18)}...</c:if>
							<c:if test="${fn:length(alarmInfo.description)<=18}">${alarmInfo.description}</c:if>
						</td>
						<td><c:if test="${alarmInfo.dealState == '0'}"><a href="javascript:showhandleAlarm('${alarmInfo.id}','1','${alarmInfo.type}')">告警确认</a></c:if>
						<c:if test="${alarmInfo.dealState == '1'}"><a href="javascript:showhandleAlarm('${alarmInfo.id}','2','${alarmInfo.type}')">告警消除</a>&nbsp;&nbsp;<a href="javascript:showhandleAlarm('${alarmInfo.id}','0','${alarmInfo.type}')">取消确认</a></c:if>
						<c:if test="${alarmInfo.dealState == '2'}"><a href="javascript:showhandleAlarm('${alarmInfo.id}','3','${alarmInfo.type}')">归档</a>&nbsp;&nbsp;
						<a href="javascript:showhandleAlarm('${alarmInfo.id}','1','${alarmInfo.type}')">取消消除</a></c:if></td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="12">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="12" style="text-align: center;vertical-align: top;height: 35px">
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