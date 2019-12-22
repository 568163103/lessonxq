$(function () {

    getChannelInfo();




    //中上--北京南站铁路状况
    var myChart2 = echarts.init(document.getElementById('middleTop'));

    // 指定图表的配置项和数据
    var option2 = {
        color: ['#7CCD7C'],
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['线路状况'],
            textStyle: { //图例文字的样式
                color: '#fff',
            },
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1, //这里是为了突出显示加上的
                }
            },
        },
        yAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1, //这里是为了突出显示加上的
                }
            },
        },
        series: [{
            name: '线路状况',
            type: 'line',
            stack: '总量',
            data: [120, 132, 101, 134, 90, 230, 210]
        }]
    };


    myChart2.setOption(option2);


    //中下--服务器运行状态
    var myChart3 = echarts.init(document.getElementById('middleBottom'));

    // 指定图表的配置项和数据
    var option3 = {
        color: ['#7CCD7C'],
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1, //这里是为了突出显示加上的
                }
            }
        },
        yAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1, //这里是为了突出显示加上的
                }
            }
        },
        series: [{
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar',
        }]
    };
    myChart3.setOption(option3);


    //右上--录像诊断
    var myChart4 = echarts.init(document.getElementById('rightTop'));

    // 指定图表的配置项和数据
    var option4 = {
        color: ['#7CCD7C', '#87CEFA'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#283b56'
                }
            }
        },
        legend: {
            data: ['录像总数', '录像正常'],
            textStyle: { //图例文字的样式
                color: '#fff',
            }
        },
        dataZoom: {
            show: false,
            start: 0,
            end: 100
        },
        xAxis: [{
            type: 'category',
            boundaryGap: true,
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1, //这里是为了突出显示加上的
                }
            },
            data: (function () {
                var now = new Date();
                var res = [];
                var len = 10;
                while (len--) {
                    res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
                    now = new Date(now - 2000);
                }
                return res;
            })()
        },
            {
                type: 'category',
                axisLine: {
                    lineStyle: {
                        color: '#fff',
                        width: 1, //这里是为了突出显示加上的
                    }
                },
                boundaryGap: true,
                data: (function () {
                    var res = [];
                    var len = 10;
                    while (len--) {
                        res.push(10 - len - 1);
                    }
                    return res;
                })()
            }
        ],
        yAxis: [{
            type: 'value',
            scale: true,
            name: '数量',
            max: 30,
            min: 0,
            boundaryGap: [0.2, 0.2],
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1, //这里是为了突出显示加上的
                }
            },
        },
            {
                type: 'value',
                scale: true,
                name: '正常量',
                max: 1200,
                min: 0,
                boundaryGap: [0.2, 0.2],
                axisLine: {
                    lineStyle: {
                        color: '#fff',
                        width: 1, //这里是为了突出显示加上的
                    }
                },
            }

        ],
        series: [{
            name: '录像总数',
            type: 'bar',
            xAxisIndex: 1,
            yAxisIndex: 1,
            data: (function () {
                var res = [];
                var len = 10;
                while (len--) {
                    res.push(Math.round(Math.random() * 1000));
                }
                return res;
            })()
        },
            {
                name: '录像正常',
                type: 'line',
                data: (function () {
                    var res = [];
                    var len = 0;
                    while (len < 10) {
                        res.push((Math.random() * 10 + 5).toFixed(1) - 0);
                        len++;
                    }
                    return res;
                })()
            }
        ]
    };
    myChart4.setOption(option4);


    //网络异常统计
    jQuery(".txtMarquee-top").slide({
        titCell: ".hd ul",
        mainCell: ".bd ul",
        autoPage: true,
        effect: "top",
        autoPlay: false,
        vis: 6,
        pnLoop: false
    });


    getServerInfo();
    getCpuInfo();
    getDiskCapacity();
    // getCpuInfoTest();


})

/**
 * 本地测试 cpu
 */
function getCpuInfoTest() {
    var cpu_temperature = 35.0;
    var cpu_usage_rate = 35.0;
    //存储容量-左
    var myChart1 = echarts.init(document.getElementById('leftMiddle'));

    // 指定图表的配置项和数据
    Highcharts.chart('leftMiddle', {
        chart: {
            backgroundColor: 'rgba(0,0,0,0)',
            type: 'cylinder',
            margin: 25,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 25,
                depth: 50,
                viewDistance: 25
            }
        },
        animation: false,
        title: {
            text: ''
        },
        credits: {
            enabled: false //不显示LOGO
        },
        exporting: {
            enabled: false
        },
        yAxis: {
            min: 0,
            title: {
                text: '数值',
                style: {
                    color: '#fff',
                }
            },
            labels: {
                style: {
                    color: '#fff',
                    fontSize: '14px'
                }
            }
        },
        xAxis: {
            labels: {
                style: {
                    color: '#fff',
                    fontSize: '14px'
                }
            },
            categories: [
                'cpu使用率(%)', 'cpu温度(℃)'
            ],
            crosshair: true
        },
        plotOptions: {
            series: {
                depth: 50,
                colorByPoint: true
            }
        },
        colors: ['#4dd3b9', '#fdd67f'],
        series: [{
            data: [cpu_usage_rate, cpu_temperature],
            name: 'Cylinders',
            showInLegend: false
        }]
    });
    $('#cpuinfo').html('');
    var cpuHtml = '';
    cpuHtml += "<span id='cpu1' class='color1'><span class='circle'></span>使用率:" + cpu_usage_rate + "%</span>";
    cpuHtml += "<span id='cpu2' class='color1'><span class='circle'></span>温度:" + cpu_temperature + "℃</span>";
    $('#cpuinfo').append(cpuHtml);
}

/**
 * CPU信息获取
 */
function getCpuInfo() {
    let url = "/api/v1/alarm/getCpuInfo";
    axios.post(url)
        .then(res => {
            var cpu_temperature = parseFloat(res.data.cpu_temperature);
            var cpu_usage_rate = parseFloat(res.data.cpu_usage_rate);
            //存储容量-左
            var myChart1 = echarts.init(document.getElementById('leftMiddle'));

            // 指定图表的配置项和数据
            Highcharts.chart('leftMiddle', {
                chart: {
                    backgroundColor: 'rgba(0,0,0,0)',
                    type: 'cylinder',
                    margin: 25,
                    options3d: {
                        enabled: true,
                        alpha: 15,
                        beta: 25,
                        depth: 50,
                        viewDistance: 25
                    }
                },
                animation: false,
                title: {
                    text: ''
                },
                credits: {
                    enabled: false //不显示LOGO
                },
                exporting: {
                    enabled: false
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '数值',
                        style: {
                            color: '#fff',
                        }
                    },
                    labels: {
                        style: {
                            color: '#fff',
                            fontSize: '14px'
                        }
                    }
                },
                xAxis: {
                    labels: {
                        style: {
                            color: '#fff',
                            fontSize: '14px'
                        }
                    },
                    categories: [
                        'cpu使用率(%)', 'cpu温度(℃)'
                    ],
                    crosshair: true
                },
                plotOptions: {
                    series: {
                        depth: 50,
                        colorByPoint: true
                    }
                },
                colors: ['#4dd3b9', '#fdd67f'],
                series: [{
                    data: [cpu_usage_rate, cpu_temperature],
                    name: 'Cylinders',
                    showInLegend: false
                }]
            });
            $('#cpuinfo').html('');
            var cpuHtml = '';
            cpuHtml += "<span id='cpu1' class='color1'><span class='circle'></span>使用率:" + cpu_usage_rate + "%</span>";
            cpuHtml += "<span id='cpu2' class='color1'><span class='circle'></span>温度:" + cpu_temperature + "℃</span>";
            $('#cpuinfo').append(cpuHtml);
        })
        .catch(err => {
            console.error(err);
        })

}


function getChannelInfo() {


    axios.post('/api/v1/channel/getChannelList')
        .then(res => {
            //摄像机类型
            var myChart = echarts.init(document.getElementById('leftTop'));

            // 指定图表的配置项和数据
            var option = {
                color: ['#CDCDC1', '#6495ED', '#DAA520', '#666666', '#E066FF'],
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: ['固定摄像机', '云台摄像机', '固定IP摄像机', '云台IP摄像机', '其他'],
                    textStyle: { //图例文字的样式
                        color: '#fff',
                    },
                },
                series: [{
                    name: '摄像机状况',
                    type: 'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center',
                            color: '#fff',
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '20',
                                fontWeight: 'bold'

                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false,
                        }

                    },
                    data: [
                        {value: res.data.channelStatistics.fixedCameraCount, name: '固定摄像机'},
                        {value: res.data.channelStatistics.ptzCameraCount, name: '云台摄像机'},
                        {value: res.data.channelStatistics.fixedIpCameraCount, name: '固定IP摄像机'},
                        {value: res.data.channelStatistics.ptzIpCameraCount, name: '云台IP摄像机'},
                        {value: 0, name: '其他'}
                    ]
                }]
            }
            myChart.setOption(option);
            $('#fix').text('固定摄像机: ' + res.data.channelStatistics.fixedCameraCount);
            $('#fix1').text('云台摄像机: ' + res.data.channelStatistics.ptzCameraCount);
            $('#fix2').text('固定IP摄像机: ' + res.data.channelStatistics.fixedIpCameraCount);
            $('#fix3').text('云台IP摄像机: ' + res.data.channelStatistics.ptzIpCameraCount);
            $('#channelOnline').text("在线: " + res.data.onlineChannel);
            $('#channelOfOnline').text("离线: " + res.data.offOnlineChannel);
        })
        .catch(err => {
            console.error(err);
        })


}


function getServerInfo() {
    var url = "/api/v1/server/getServerType";
    axios.post(url)
        .then(res => {
            //右下--自定义微服务统计
            var myChart6 = echarts.init(document.getElementById('rightBottom'));

            // 指定图表的配置项和数据
            var option6 = {
                color: ['#CDCDC1', '#6495ED', '#DAA520', '#666666'],
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: ['cms服务', 'dms服务', 'mss服务', '其他服务'],
                    textStyle: { //图例文字的样式
                        color: '#fff',
                    }
                },
                series: [{
                    name: '服务状况',
                    type: 'pie',
                    radius: ['40%', '60%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '20',
                                fontWeight: 'bold',
                                color: '#fff',
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: [
                        {value: res.data.serverStatistics.cmsCount, name: 'cms服务'},
                        {value: res.data.serverStatistics.dmsCount, name: 'dms服务'},
                        {value: res.data.serverStatistics.mssCount, name: 'mss服务'},
                        {value: 0, name: '其他服务'},
                        // { value: 1548, name: '搜索引擎' }
                    ]
                }]
            }
            myChart6.setOption(option6);
            $('#cms').text("cms节点:" + res.data.serverStatistics.cmsCount);
            $('#dms').text("dms节点:" + res.data.serverStatistics.dmsCount);
            $('#mss').text("mss节点:" + res.data.serverStatistics.mssCount);
            $('#cms_server').text("cms服务:" + res.data.serverStatistics.cmsCount);
            $('#dms_server').text("dms服务:" + res.data.serverStatistics.dmsCount);
            $('#mss_server').text("mss服务:" + res.data.serverStatistics.mssCount);
            $('#online').text("在线: " + (res.data.serverStatistics.mssOnline + res.data.serverStatistics.dmsOnline + res.data.serverStatistics.cmsOnline));
            $('#offline').text("离线: " + (res.data.serverStatistics.mssOffOnline + res.data.serverStatistics.dmsOffOnline + res.data.serverStatistics.cmsOffOnline));
            $('#online1').text("在线: " + (res.data.serverStatistics.mssOnline + res.data.serverStatistics.dmsOnline + res.data.serverStatistics.cmsOnline));
            $('#offline1').text("离线: " + (res.data.serverStatistics.mssOffOnline + res.data.serverStatistics.dmsOffOnline + res.data.serverStatistics.cmsOffOnline));
        })
        .catch(err => {
            console.error(err);
        })
}


function getDiskCapacity(){

        //存储容量-右
        let url = '/api/v1/alarm/getDiskCapacity';
        let params = new URLSearchParams();
        axios.post(url,params)
        .then(res => {
            var disk_remainingCapacity = parseFloat(res.data.remainingCapacity);
            var disk_usedCapacity = parseFloat(res.data.usedCapacity);
            var disk_countCapacity = parseFloat(res.data.countCapacity);
            var chart = Highcharts.chart('leftMiddle1', {
                chart: {
                    backgroundColor: 'rgba(0,0,0,0)',
                    type: 'pie',
                    margin: 10,
                    options3d: {
                        enabled: true,
                        alpha: 45,
                        beta: 0
                    }
                },
                title: {
                    text: ''
                },
                credits: {
                    enabled: false //不显示LOGO
                },
                exporting: {
                    enabled: false
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        depth: 35,
                        dataLabels: {
                            enabled: true,
                            format: '{point.name}'
                        }
                    }
                },
                colors: ['#4dd3b9', '#fdd67f'],
                series: [{
                    type: 'pie',
                    name: '硬盘使用占比',
                    data: [
                        ['未使用', disk_remainingCapacity],
                        ['已使用', disk_usedCapacity]
                    ]
                }]
            });
            $('#diskinfo').html('');
            var diskHtml = '';
            diskHtml += "<span id='yinpan1' class='color1'><span class='circle'></span>未使用:" + disk_remainingCapacity + "GB</span>";
            diskHtml += "<span id='yinpan2' class='color2'><span class='circle'></span>已使用:" + disk_usedCapacity + "GB</span>";
            diskHtml += "<span id='yinpan3' >总容量:" + disk_countCapacity + "GB</span>";
            $('#diskinfo').append(diskHtml);

            $('#diskcount').html('');
            var diskCountHtml ='';
            diskCountHtml+="<span id='countyinpan' ></span>总硬盘:"+res.data.diskCount+"</span>";
            diskCountHtml+="<span id='jiedian' ></span>总节点:"+6+"</span>";
            $('#diskcount').append(diskCountHtml);
        })
        .catch(err => {
            console.error(err); 
        })
       
    
}


window.setInterval(function () {
    getServerInfo();
    getChannelInfo();
}, 3000)
window.setInterval(function () {
    getDiskCapacity();
    // getCpuInfoTest();
    getCpuInfo();
}, 6000)
