$(function () {


    //左上角
    var myChart = echarts.init(document.getElementById('leftTop'));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['固定摄像机', '云台摄像机', '固定IP摄像机', '云台IP摄像机', '其他'],
            textStyle: {//图例文字的样式
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
                {value: 335, name: '固定摄像机'},
                {value: 310, name: '云台摄像机'},
                {value: 234, name: '固定IP摄像机'},
                {value: 135, name: '云台IP摄像机'},
                {value: 1548, name: '其他'}
            ]
        }]
    }
    myChart.setOption(option);


    //左中
    var myChart1 = echarts.init(document.getElementById('leftMiddle'));

    // 指定图表的配置项和数据
    var option1 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#283b56'
                },
            }
        },
        legend: {
            data: ['', ''],
            textStyle: {//图例文字的样式
                color: '#fff',
            },
            lineStyle: {
                borderColor: "#fff"
            }

        },
        dataZoom: {
            show: false,
            start: 0,
            end: 100
        },
        xAxis: [{
            type: 'category',
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1,//这里是为了突出显示加上的
                }
            },
            boundaryGap: true,
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
                        width: 1,//这里是为了突出显示加上的
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
            },
            {}
        ],
        yAxis: [{
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1,//这里是为了突出显示加上的
                }
            },
            scale: true,
            name: '',
            max: 30,
            min: 0,
            boundaryGap: [0.2, 0.2]
        },
            {
                type: 'value',
                axisLine: {
                    lineStyle: {
                        color: '#fff',
                        width: 1,//这里是为了突出显示加上的
                    }
                },
                scale: true,
                name: '',
                max: 1200,
                min: 0,
                boundaryGap: [0.2, 0.2]
            },
        ],
        series: [{
            name: '',
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
                name: '',
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
    myChart1.setOption(option1);


    //中上
    var myChart2 = echarts.init(document.getElementById('middleTop'));

    // 指定图表的配置项和数据
    var option2 = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['线路状况'],
            textStyle: {//图例文字的样式
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
                    width: 1,//这里是为了突出显示加上的
                }
            },
        },
        yAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1,//这里是为了突出显示加上的
                }
            },
        },
        series: [
            {
                name: '线路状况',
                type: 'line',
                stack: '总量',
                data: [120, 132, 101, 134, 90, 230, 210]
            }
        ]
    };


    myChart2.setOption(option2);


    //中下
    var myChart3 = echarts.init(document.getElementById('middleBottom'));

    // 指定图表的配置项和数据
    var option3 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1,//这里是为了突出显示加上的
                }
            }
        },
        yAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1,//这里是为了突出显示加上的
                }
            }
        },
        series: [{
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar',
        }]
    };
    myChart3.setOption(option3);


    //右上
    var myChart4 = echarts.init(document.getElementById('rightTop'));

    // 指定图表的配置项和数据
    var option4 = {
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
            textStyle: {//图例文字的样式
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
                    width: 1,//这里是为了突出显示加上的
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
                        width: 1,//这里是为了突出显示加上的
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
                    width: 1,//这里是为了突出显示加上的
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
                        width: 1,//这里是为了突出显示加上的
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


    //右中
    var myChart5 = echarts.init(document.getElementById('rightMiddle'));

    // 指定图表的配置项和数据
    var option5 = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['超限网口数量'],
            textStyle: {//图例文字的样式
                color: '#fff',
            },
        },
        xAxis: {
            type: 'category',
            data: ['一', '二', '三', '四', '五', '六', '日'],
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1,//这里是为了突出显示加上的
                }
            },
        },
        yAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: '#fff',
                    width: 1,//这里是为了突出显示加上的
                }
            },
        },
        series: [
            {
                name: '超限网口数量',
                data: [820, 932, 901, 934, 1290, 1330, 1320],
                type: 'line'
            }
        ]
    };
    myChart5.setOption(option5);

    getServerInfo();


})

function getServerInfo() {
    var url = "/api/v1/server/getServerType";
    axios.post(url)
        .then(res => {
            console.log(res)
            console.log(res.data.cmsCount);

            //右下
            var myChart6 = echarts.init(document.getElementById('rightBottom'));

            // 指定图表的配置项和数据
            var option6 = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: ['cms服务器', 'dms服务器', 'mss服务器', '其他服务器'],
                    textStyle: {//图例文字的样式
                        color: '#fff',
                    }
                },
                series: [{
                    name: '服务器状况',
                    type: 'pie',
                    radius: ['50%', '70%'],
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
                        {value: res.data.serverStatistics.cmsCount, name: 'cms服务器'},
                        {value: res.data.serverStatistics.dmsCount, name: 'dms服务器'},
                        {value: res.data.serverStatistics.mssCount, name: 'mss服务器'},
                        {value: 0, name: '其他服务器'},
                        // { value: 1548, name: '搜索引擎' }
                    ]
                }]
            }
            myChart6.setOption(option6);
            $('#cms').text("cms服务器:" + res.data.serverStatistics.cmsCount);
            $('#dms').text("dms服务器:" + res.data.serverStatistics.dmsCount);
            $('#mss').text("mss服务器:" + res.data.serverStatistics.mssCount);
            $('#online').text("在线: " + (res.data.serverStatistics.mssOnline + res.data.serverStatistics.dmsOnline + res.data.serverStatistics.cmsOnline));
            $('#offline').text("离线: " + (res.data.serverStatistics.mssOffOnline + res.data.serverStatistics.dmsOffOnline + res.data.serverStatistics.cmsOffOnline));
        })
        .catch(err => {
            console.error(err);
        })
}