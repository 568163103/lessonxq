<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-x:hidden">
<head>
    <%@ include file="/jsp/common/head/head.jsp"%>
    <script src="${ctx}/js/echarts/echarts.js"></script>
    <script type="text/javascript">
        var ECharts = {};
        function initChart(data) {
        	require(['echarts', 'echarts/chart/pie'], function(ec) {
        		$.each(data, function(i, e) {
                    var childNames = e.childNames;
                    var childDatas = e.childDataes;
                    
                    // 生成图表
                    ECharts[e.num] = ec.init(document.getElementById(e.num));
                    
                    option = {
                            title: {
                                text: "硬盘" + e.num + "使用情况",
                                x: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c}（{d}%）"
                            },
                            legend: {
                                orient: 'vertical',
                                x: 'left',
                                //data: childNames,
                            },
                            //series: [ seriesData ],
                    }; // end option
                    
                    if (e.status == "0") {
                    	var seriesData = {};
                        seriesData["type"] = "pie";
                        seriesData["name"] = "硬盘使用";
                        seriesData["data"] = [];
                        $.each(childNames, function(i, e) {
                            seriesData["data"][i] = {
                                    name: childNames[i],
                                    value: childDatas[i]
                            }
                        });
                        
                        option.color = ['#0000ee', '#008b00'];
                        option.legend.data = childNames;
                        option.series = [ seriesData ];
                        
                    } else if (e.status == "1") {
                    	option.color = ['#000000'];
                    	option.legend.data = ['未格式化'];
                        option.series = [{name:'硬盘使用', type: 'pie', data: [{value: 100, name: '未格式化'}] }];
                    } else if (e.status == "2") {
                    	option.color = ['#d6d6d6'];
                    	option.legend.data = ['异常'];
                        option.series = [{name:'硬盘使用', type: 'pie', data: [{value: 100, name: '异常'}] }];
                    }
                    
                    ECharts[e.num].setOption(option);
        		});
        	});
        }
        
        $(function() {
        	require.config({
                paths: {
                    echarts: '${ctx}/js/echarts'
                }
            });

        	var disks = ${ result };
        	initChart(disks);
        });
    </script>
</head>
<body class="main_bg" style="background: #FFFFFF;">
    <input type="hidden" id="id" name="id" value="<%=request.getParameter("id") %>">
    <div class="win-bodyer">
        <table id="chartTable" width="70%" height="100%" border="1" cellspacing="0" cellpadding="0" class="inputTable">
            <tr>
                <td style="text-align: left" width="12%">编码器名称：</td>
                <td colspan="2">${ name }</td>
            </tr>
            <tr>
                <td style="text-align: left"><label>检测时间：</label></td>
                <td colspan="2">${ lastTime }</td>
            </tr>
            <c:forEach var="disk" items="${ diskList }">
              <tr>
                  <td style="text-align: left">
                      <div>硬盘编号：${ disk.num }</div>
                      <div>硬盘状态：${ disk.statusName }</div>
                  </td>
                  <td colspan="2" style="align: center"><div id="${ disk.num }" style="height:400px;width: 600px;"></div></td>
              </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>