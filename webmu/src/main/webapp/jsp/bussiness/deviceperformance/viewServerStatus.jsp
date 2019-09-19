<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<title>${global_app_name}</title>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">性能管理 &gt; 性能监视 </span>
</div>
</div>
<div class="win-bodyer">
	<table style="width: 100%;height: 100%;">
		<tr>
			<td><div id="cpu" style="height:400px;width: 800px;"></div></td>
			<td><div id="memory" style="height:400px;width: 800px;"></div></td>
		</tr>
		<tr>
			<td><div id="net" style="height:400px;width: 800px;"></div></td>
			<td><div id="user" style="height:400px;width: 800px;"></div></td>
		</tr>
		<tr>
			<td><div id="disk" style="height:400px;width: 800px;"></div></td>
			<td></td>
		</tr>
	</table>
	<!-- ECharts单文件引入 -->
	<script src="${ctx}/js/echarts/echarts.js"></script>
	<script type="text/javascript">
		var ECharts = {};
		function initChart(result){
			$.each(result,function(i,e) {
				var id = e.id,name = e.name,ymin = e.ymin,ymax = e.ymax,yunit = e.yunit,childNames = e.childNames,xaxis = e.xaxis, data= e.childDataes;
				require(
					[
						'echarts',
						'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
					],
					function (ec) {
						var seriesData = [];
						$.each(childNames,function(i,e){
							seriesData[i]={
								name: childNames[i],
								type: 'line',
								data: data[i]
							}
						});
						// 基于准备好的dom，初始化echarts图表
						ECharts[id] = ec.init(document.getElementById(id));
						option = {
							title: {
								text: name
							},
							animation:false,
							tooltip : {
								trigger: 'axis'
							},
							legend: {
								data: childNames
							},
							xAxis: [
								{
									type: 'category',
									boundaryGap: false,
									data: xaxis
								}
							],
							yAxis: [
								{
									type: 'value',
									axisLabel: {
										formatter: '{value}'+yunit
									},
									min:ymin,
									max:ymax
								}
							],
							series: seriesData
						};
						ECharts[id].setOption(option);
					}
				);
			});
		}
		$(function (){
			require.config({
				paths: {
					echarts: '${ctx}/js/echarts'
				}
			});
			initChart(${result});
			setInterval(function(){
				$.post("${ctx}/bussiness/devicePerformance!getServerStatus.do?serverId=${param.serverId}",function(data){
					initChart(data);
				});
			},5000)
		})
	</script>
</div>
</body>
</html>