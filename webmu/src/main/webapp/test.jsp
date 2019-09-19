<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<%@ include file="/jsp/common/head/headForm.jsp"%>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
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
					data:['销量']
				},
				xAxis : [
					{
						type : 'category',
						data : ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
					}
				],
				yAxis : [
					{
						type : 'value'
					}
				],
				series : [
					{
						"name":"销量",
						"type":"bar",
						"data":[5, 20, 40, 10, 10, 20]
					}
				]
			};
			myChart.setOption(option);
		}
	);
</script>
</body>
</html>