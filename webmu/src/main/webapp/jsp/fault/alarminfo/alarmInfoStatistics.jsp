<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<%@ include file="/jsp/common/head/headForm.jsp"%>
	<script type="text/javascript">
	function exportList(){
		$("#pageForm").attr("action","${ctx}/fault/alarmInfo!exportList2.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/fault/alarmInfo!alarmInfoStatistics.do");
	}
	</script>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">故障管理 &gt; 告警统计 </span>
</div>
<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/fault/alarmInfo!alarmInfoStatistics.do" method="post">
	<div class="win-advsearch">
		<table width="100%">
			<tr>
			<!--	<td>统计周期：
					<select class="validate[required]" name="statisticsTime" id="statisticsTime">
						<option value="">请选择</option>
						<option value="1" <c:if test="${statisticsTime eq '1'}">selected</c:if> >一天内</option>
						<option value="2" <c:if test="${statisticsTime eq '2'}">selected</c:if> >七天内</option>
						<option value="3" <c:if test="${statisticsTime eq '3'}">selected</c:if> >一月内</option>
					</select>					
				</td>  -->
				<td>设备编号：
						<input type="text" id="sourceId" name="sourceId" value="${sourceId }"/>
				</td>
				<td>告警级别：
						<select class="validate[required]" name="alarmLevel" id="level">
							<option value="">请选择</option>
							<c:forEach var="type" items="${levels}">
								<option value="${type.value}" <c:if test="${alarmLevel eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
				</td>
				<td>告警类型：
						<select class="validate[required]" name="alarmType" id="alarmType">
							<option value="">请选择</option>
							<c:forEach var="type" items="${alarmTypes2}">
								<option value="${type.value}" <c:if test="${alarmType eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
				</td>
				<td>告警状态：
					<select class="validate[required]" name="status" id="statisticsTime">
						<option value="">请选择</option>
						<option value="0" <c:if test="${status eq '0'}">selected</c:if> >待确认</option>
						<option value="1" <c:if test="${status eq '1'}">selected</c:if> >处理中</option>
						<option value="2" <c:if test="${status eq '2'}">selected</c:if> >待归档</option>
						<option value="3" <c:if test="${status eq '3'}">selected</c:if> >已归档</option>
					</select>
				</td>
				<td>开始时间：
						<input type="text" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss' ,maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate" style="width:145px;" id="beginTime" name="beginTime" value="${beginTime }"/>
				</td>
				<td>结束时间：
						<input type="text" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}' })" class="Wdate" style="width:145px;" id="endTime" name="endTime" value="${endTime }"/>
				</td>
				<td align="right">
						<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>查询</span></a>
						<a class="a_button" href="javascript:void(0);" onclick="return exportList();"><span>导出</span></a>
				</td>
			</tr>
		</table>
	</div>
	</form>
<div id="main" style="height:600px"></div>
<!-- ECharts单文件引入 -->
<script src="${ctx}/js/echarts/echarts.js"></script>
<script type="text/javascript">
	require.config({
		paths: {
			echarts: '${ctx}/js/echarts'
		}
	});

	require(
		[
			'echarts',
			'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
		],
		function (ec) {
			// 基于准备好的dom，初始化echarts图表
			var myChart = ec.init(document.getElementById('main'));

			var option = {
				tooltip: {
					show: true
				},
				legend: {
					data:['告警数量']
				},
				xAxis : [
					{
						type : 'category',
						data : ['${alarmTypes}'],
						axisLabel: {
                                   interval:0,
                                   rotate:40
            
                        }
					}
				],
				yAxis : [
					{
						type : 'value'
					}
				],
				series : [
					{
						"name":"告警数量",
						"type":"bar",
						"data":[${alarmDatas}]
					}
				]
			};
			myChart.setOption(option);
		}
	);
</script>
</div>
</body>
</html>