$(function () {
    var devIdList = getDevId(1)
    var devIdList2 = getDevId(7)

    // 获取实时客流数据
    function liveValue(detectId, timeArr) {
        var data = []
        var timestamp = []
        var level = []
        var detectIdStr = ''
        var index
        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/live/passenger/values",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                "detect_id": detectId,
                "start": timeArr
            },
            success: function (def) {
                if (def.code == "10000") {
                    if (def.data !== null) {
                        for (var i = 0; i < def.data.length; i++) {
                            index = i
                            timestamp.push(def.data[i].timestamp)
                            data.push(parseInt(def.data[i].nums))
                            level.push(def.data[i].level)
                            detectIdStr = def.data[i].detect_id
                        }
                    }
                } else {
                    alert(def.msg)
                }
            }
        });

        var passenger = {
            data: data,
            level: level,
            timestamp: timestamp,
            detectIdStr: detectIdStr,
            index: index
        }
        return passenger
    }

    // 获取基准客流数据
    function baseValue(detectId, timeArr) {
        var data = []
        var timestamp = []
        var detectIdStr = ''

        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/live/passenger/values",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                "detect_id": detectId,
                "start": timeArr
            },
            success: function (def) {
                if (def.code == "10000") {
                    if (def.data !== null) {
                        for (var i = 0; i < def.data.length; i++) {
                            data.push(parseInt(def.data[i].nums))
                            timestamp.push(def.data[i].timestamp)
                            detectIdStr = def.data[i].detect_id
                        }
                    }
                } else {
                    alert(def.msg)
                }
            }
        });

        var passenger = {
            data: data,
            timestamp: timestamp,
            detectIdStr: detectIdStr,
        }
        return passenger
    }

    // 获取行走速度
    function speedValue(detectId, timeArr, grained) {
        var data = []
        var timestamp = []
        var detectIdStr = ''

        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/walker/speed",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                "detect_id": detectId,
                "start": timeArr,
                "grained": grained
            },
            success: function (def) {
                if (def.code == "10000") {
                    if (def.data !== null) {
                        for (var i = 0; i < def.data.length; i++) {
                            data.push(parseInt(def.data[i].walk_speed))
                            timestamp.push(def.data[i].timestamp)
                            detectIdStr = def.data[i].detect_id
                        }
                    }
                } else {
                    alert(def.msg)
                }
            }
        });

        var passenger = {
            data: data,
            timestamp: timestamp,
            detectIdStr: detectIdStr,
        }
        return passenger
    }

    // 获取行走速度变化
    function speedChangeValue(detectId, timeArr, grained) {
        var data = []
        var data2 = []
        var timestamp = []
        var detectIdStr = ''

        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/walker/change/speed",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                "detect_id": detectId,
                "start": timeArr,
                "grained": grained
            },
            success: function (def) {
                // console.log('行走速度变化', def)
                if (def.code == "10000") {
                    if (def.data !== null) {
                        for (var i = 0; i < def.data.length; i++) {
                            // 正常行走速度
                            data.push(parseInt(def.data[i].normal_walk_speed))
                            // 异常行走速度
                            data2.push(parseInt(def.data[i].exception_walk_speed))
                            timestamp.push(def.data[i].timestamp)
                            detectIdStr = def.data[i].detect_id
                        }
                    }
                } else {
                    alert(def.msg)
                }
            }
        });

        var passenger = {
            data: data,
            data2: data2,
            timestamp: timestamp,
            detectIdStr: detectIdStr,
        }
        return passenger
    }

    //  获取逃离线路信息
    function escape(detectId, timeArr, grained) {
        var data = []
        var timestamp = []
        var detectIdStr = ''

        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/walker/escape/line",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                "detect_id": detectId,
                "start": timeArr,
                "grained": grained
            },
            success: function (def) {
                // console.log('逃离线路', def)
                if (def.code == "10000") {
                    if (def.data !== null) {
                        for (var i = 0; i < def.data.length; i++) {
                            // 密度
                            data.push(parseInt(def.data[i].passenger_density))
                            timestamp.push(def.data[i].timestamp)
                            detectIdStr = def.data[i].detect_id
                        }
                    }
                } else {
                    alert(def.msg)
                }
            }
        });

        var passenger = {
            data: data,
            timestamp: timestamp,
            detectIdStr: detectIdStr,
        }
        return passenger
    }

    // 折线颜色设置
    Highcharts.setOptions({
        colors: ['#5b9bd5', '#ed7d31'],
        lang: {
            rangeSelectorZoom: '',
        },
    });

    //******************************************************************************************第一部分
    $('.locally1').on('click', function () {
        clickStyle($(this))
        var fatherI = $('.tab-select')
        fatherI.empty()

        var input2 = '<label class="control-label" for="inputTime">时间：</label>' +
            '<div class="controls labelFloat">' +
            '<select id="inputTime">' +
            '<option value="1">最近1小时</option>' +
            '<option value="12">最近12小时</option>' +
            '<option value="24">最近一天</option>' +
            '<option value="168">最近一星期</option>' +
            '</select>' +
            '</div>'
        label(input2, fatherI)

        var input3 = '<button class="subInput btn btnFloat">查询</button>'
        label(input3, fatherI)

        // 图表内容区
        var view = '<li class="real-box span6 border-bg">' +
            '<div class="real-box1">' +
            '<div class="lodding lodding1 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real1" style="width: 100%; height: 100%;"></div>' +
            '</div>' +
            '</li>' +

            '<li class="real-box span6 border-bg">' +
            '<div class="real-box1">' +
            '<div class="lodding lodding2 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real2" style=" width: 100%; height: 100%;"></div>' +
            '</div>' +
            '</li>'
        var fatherV = $('.row-fluid')
        siteTabBox(view, fatherV)

        $('.subInput').click(function () {

            $('#real1').empty()
            $('#real2').empty()
            $('.lodding').removeClass('hidden')

            var inputTime = parseInt($('#inputTime').val())
            var detectId = $('#inputId').val()

            // var live = liveValue(detectId, getTime(inputTime))

            // 实时客流数据
            var lives = [];
            for (var i = 0; i < devIdList.length; i++) {
                var detectIds = devIdList[i].code;
                lives[i] = liveValue(detectIds, getTime(inputTime))
            }
            $('#real1').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '用户选择出入口偏好'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    crosshair: true,
                    categories: (function () {
                        var datax = [];
                        for (var i = 0; i < devIdList.length; i++) {
                            var dataxx = devIdList[i].name;
                            datax.push(dataxx);
                        }
                        return datax;
                    })(),
                },
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                yAxis: {
                    min: 0,
                    title: null
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        borderWidth: 0
                    }
                },

                series: [{
                    name: '实时客流量',
                    data: (function () {
                        var datas = [];
                        for (var n = 0; n < lives.length; n++) {
                            var livess = lives[n];
                            var livedatas = livess.data[livess.data.length - 1]
                            datas.push(livedatas);
                        }
                        return datas;
                    })()
                }]
            });
            $('.lodding1').addClass('hidden')
            // 用户选择乘车线路偏好
            var devArr = [
                [
                    { code: "002", name: "K2", line: "一号线" },
                    { code: "003", name: "K3" },
                    { code: "004", name: "K4" },
                ], [
                    { code: "005", name: "K5", line: "五号线" },
                    { code: "006", name: "K6" }
                ]
            ]
            var livesAll = [];
            for (var i = 0; i < devArr[0].length; i++) {
                var detectIds = devArr[0][i].code;
                livesAll[i] = liveValue(detectIds, getTime(inputTime))
            }

            var livesAll2 = [];
            for (var i = 0; i < devArr[1].length; i++) {
                var detectIds = devArr[1][i].code;
                livesAll2[i] = liveValue(detectIds, getTime(inputTime))
            }

            $('#real2').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '用户选择乘车线路偏好'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    crosshair: true,
                    categories: (function () {
                        var datax = [devArr[0][0].line, devArr[1][0].line];
                        return datax;
                    })(),
                },
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                yAxis: {
                    min: 0,
                    title: null
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        borderWidth: 0
                    }
                },

                series: [{
                    name: '实时客流量',
                    data: (function () {
                        var datas = [];
                        var livedatass = 0
                        for (var n = 0; n < livesAll.length; n++) {
                            var livess = livesAll[n];
                            var livedatas = 0;
                            for (var i = 0; i < livess.data.length; i++) {
                                livedatas += livess.data[i];
                            }
                            livedatass += livedatas;

                        }
                        datas.push(livedatass);

                        var livedatass2 = 0
                        for (var n = 0; n < livesAll2.length; n++) {
                            var livess = livesAll2[n];
                            var livedatas = 0;
                            for (var i = 0; i < livess.data.length; i++) {
                                livedatas += livess.data[i];
                            }
                            livedatass2 += livedatas;

                        }
                        datas.push(livedatass2);
                        return datas;
                    })()
                }]
            });
            $('.lodding2').addClass('hidden')
        })
        $('.subInput').click()
    })

    //******************************************************************************************第二部分
    $('.locally2').on('click', function () {
        clickStyle($(this))
        var fatherI = $('.tab-select')
        fatherI.empty()

        var input1 = '<label class="control-label" for="inputName">设备ID：</label>' +
            '<div class="controls labelFloat">' +
            '<select id="inputName" style="width: 140px;"></select>' +
            '</div>'
        label(input1, fatherI)

        var input4 = '<label class="control-label" for="inputTime">时间：</label>' +
            '<div class="controls labelFloat">' +
            '<select id="inputTime" style="width: 140px;">' +
            '<option value="1">最近1小时</option>' +
            '<option value="2">最近3小时</option>' +
            '<option value="3">最近6小时</option>' +
            '<option value="4">最近12小时</option>' +
            '</select>' +
            '</div>'
        label(input4, fatherI)

        var input5 = '<label class="control-label" for="grained">粒度：</label>' +
            '<div class="controls labelFloat">' +
            '<select id="grained" style="width: 140px;">' +
            '<option value="1">5分钟</option>' +
            '<option value="2">10分钟</option>' +
            '<option value="3">15分钟</option>' +
            '<option value="4">30分钟</option>' +
            '<option value="5">1小时</option>' +
            '<option value="6">1天</option>' +
            '</select>' +
            '</div>'
        label(input5, fatherI)

        var input3 = '<button class="subInput btn btnFloat">查询</button>'
        label(input3, fatherI)

        var view = '<li class="real-box span6 border-bg">' +
            '<div class="real-box1">' +
            '<div class="lodding lodding1 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real1" style="width: 100%; height: 100%;"></div>' +
            '</div>' +
            '</li>' +

            '<li class="real-box span6 border-bg">' +
            '<div class="real-box1">' +
            '<div class="lodding lodding2 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real2" style=" width: 100%; height: 100%;"></div>' +
            '</div>' +
            '</li>' +

            '<li class="real-box span6 border-bg" style="margin-left:0;">' +
            '<div class="real-box1">' +
            '<div class="lodding lodding3 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real3" style="width: 100%; height: 100%;"></div>' +
            '</div>' +
            '</li>' +

            '<li class="real-box span6 border-bg"">' +
            '<div class="real-box1">' +
            '<div class="lodding lodding4 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real4" style="width: 100%; height: 100%;"></div>' +
            '</div>' +
            '</li>'
        var fatherV = $('.row-fluid')
        siteTabBox(view, fatherV)

        // 设备ID下拉
        for (var i = 0; i < AlldetectAlais.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + AlldetectId[i] + '">' + AlldetectAlais[i] + '</option>';
            $("#inputName").append(OHtml);
        }

        $('.subInput').click(function () {
            $('#real1').empty()
            $('#real2').empty()
            $('#real3').empty()
            $('#real4').empty()

            $('.lodding').removeClass('hidden')

            var detectId = $('#inputName').val()
            var inputTime = $('#inputTime').val()
            var grained = $('#grained').val()

            var live = liveValue(detectId, getTime(inputTime))
            var sd = speedValue(detectId, getTime(inputTime), grained)
            // console.log(live)
            // console.log(sd)
            // 接入后客流量
            $('#real1').highcharts('StockChart', {
                rangeSelector: {
                    buttonTheme: {
                        display: 'none', // 不显示按钮
                    },
                    rangeSelectorZoom: {
                        display: 'none'
                    },
                    inputEnabled: false,
                    selected: 1
                },
                title: {
                    text: '客流到达特性'
                },
                xAxis: {
                    title: null,
                    startOnTick: true,
                    endOnTick: true,
                    showLastLabel: true,
                    type: 'datetime',
                },
                yAxis: {
                    title: null
                },
                plotOptions: {
                    scatter: {
                        marker: {
                            radius: 5,
                            states: {
                                hover: {
                                    enabled: true,
                                    lineColor: 'rgb(100,100,100)'
                                }
                            }
                        },
                        states: {
                            hover: {
                                marker: {
                                    enabled: false
                                }
                            }
                        },
                        tooltip: {
                            headerFormat: '<b>{series.name}</b><br>',
                            pointFormat: '{point.x} cm, {point.y} kg'
                        }
                    }
                },
                series: [{
                    name: '客流到达特性',
                    data: (function () {
                        var data1 = [];
                        for (var i = 0; i < live.timestamp.length; i++) {
                            var time = live.timestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            data1.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                live.data[i]
                            ]);
                        }
                        // console.log(data1)
                        return data1;
                    })()
                }],
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },

            });
            $('.lodding1').addClass('hidden')

            // 拥挤度
            $('#real2').highcharts('StockChart', {
                rangeSelector: {
                    buttonTheme: {
                        display: 'none', // 不显示按钮
                    },
                    rangeSelectorZoom: {
                        display: 'none'
                    },
                    inputEnabled: false,
                    selected: 1
                },
                title: {
                    text: '拥挤度'
                },
                xAxis: {
                    title: null,
                    startOnTick: true,
                    endOnTick: true,
                    showLastLabel: true,
                    type: 'datetime',
                },
                yAxis: {
                    title: null
                },
                plotOptions: {
                    scatter: {
                        marker: {
                            radius: 5,
                            states: {
                                hover: {
                                    enabled: true,
                                    lineColor: 'rgb(100,100,100)'
                                }
                            }
                        },
                        states: {
                            hover: {
                                marker: {
                                    enabled: false
                                }
                            }
                        },
                        tooltip: {
                            headerFormat: '<b>{series.name}</b><br>',
                            pointFormat: '{point.x} cm, {point.y} kg'
                        }
                    },
                },
                series: [{
                    name: '拥挤度',
                    data: (function () {
                        var data2 = [];
                        for (var i = 0; i < live.timestamp.length; i++) {
                            var time = live.timestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            data2.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                live.level[i]
                            ]);
                        }

                        return data2;
                    })()
                }],
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },

            });
            $('.lodding2').addClass('hidden')

            // 流量规模
            $('#real3').highcharts('StockChart', {
                rangeSelector: {
                    buttonTheme: {
                        display: 'none', // 不显示按钮
                    },
                    rangeSelectorZoom: {
                        display: 'none'
                    },
                    inputEnabled: false,
                    selected: 1
                },
                title: {
                    text: '流量规模'
                },
                xAxis: {
                    title: null,
                    startOnTick: true,
                    endOnTick: true,
                    showLastLabel: true,
                    type: 'datetime',
                },
                yAxis: {
                    title: null
                },
                plotOptions: {
                    scatter: {
                        marker: {
                            radius: 5,
                            states: {
                                hover: {
                                    enabled: true,
                                    lineColor: 'rgb(100,100,100)'
                                }
                            }
                        },
                        states: {
                            hover: {
                                marker: {
                                    enabled: false
                                }
                            }
                        },
                        tooltip: {
                            headerFormat: '<b>{series.name}</b><br>',
                            pointFormat: '{point.x} cm, {point.y} kg'
                        }
                    }
                },
                series: [{
                    name: '流量规模',
                    data: (function () {
                        var data3 = [];
                        var scale = 0
                        for (var i = 0; i < live.timestamp.length; i++) {
                            var a = i - 1
                            var time = live.timestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            if (i == 0) {
                                scale = live.data[0]
                            } else {
                                scale = live.data[i] + scale
                            }
                            // console.log(scale)
                            data3.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                scale
                            ]);
                        }
                        return data3;
                    })()
                }],
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },

            });
            $('.lodding3').addClass('hidden')

            // 行走速度
            $('#real4').highcharts('StockChart', {
                rangeSelector: {
                    buttonTheme: {
                        display: 'none', // 不显示按钮
                    },
                    rangeSelectorZoom: {
                        display: 'none'
                    },
                    inputEnabled: false,
                    selected: 1
                },
                title: {
                    text: '行走速度'
                },
                xAxis: {
                    title: null,
                    startOnTick: true,
                    endOnTick: true,
                    showLastLabel: true,
                    type: 'datetime',
                },
                yAxis: {
                    title: null
                },
                plotOptions: {
                    scatter: {
                        marker: {
                            radius: 5,
                            states: {
                                hover: {
                                    enabled: true,
                                    lineColor: 'rgb(100,100,100)'
                                }
                            }
                        },
                        states: {
                            hover: {
                                marker: {
                                    enabled: false
                                }
                            }
                        },
                        tooltip: {
                            headerFormat: '<b>{series.name}</b><br>',
                            pointFormat: '{point.x} cm, {point.y} kg'
                        }
                    }
                },
                series: [{
                    name: '行走速度',
                    data: (function () {
                        var data2 = [];
                        for (var i = 0; i < sd.timestamp.length; i++) {
                            var time = sd.timestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            data2.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                sd.data[i]
                            ]);
                        }

                        return data2;
                    })()
                }],
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
            });
            $('.lodding4').addClass('hidden')
        })

        $('.subInput').click()
    })

    //******************************************************************************************第三部分
    $('.locally3').on('click', function () {
        // 一号线和五号线关于逃生方向的设备
        var lineObj = [
            [
                { name: "C2", code: "017" },
                { name: "L1", code: "007" },
                { name: "L2", code: "008" },
                { name: "K2", code: "002" },
                { name: "K7", code: "034" },
                { name: "K8", code: "035" },
            ],
            [
                { name: "C7", code: "022" },
                { name: "L3", code: "009" },
                { name: "L4", code: "010" },
                { name: "K1", code: "001" },
                { name: "K5", code: "005" },
                { name: "K6", code: "006" },
            ]
        ]

        clickStyle($(this))
        var fatherI = $('.tab-select')
        $('.tab-select').empty()


        var view = '<li class="real-box span6 border-bg view2" style="height:900px;">' +
            '<div class="real-box1 real4">' +
            // '<div class="lodding lodding2 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real2" style="width: 100%; height: 50%;margin-top:10px;"></div>' +
            '<div id="real3" style="width: 100%; height: 50%;margin-top:10px;"></div>' +
            '</div>' +
            '</li>' +

            '<li class="real-box span6 border-bg view1">' +
            '<div class="real-box1 real4">' +
            '<div class="lodding lodding1 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real1" style="width: 100%; height: 100%;margin-top:10px;"></div>' +
            '</div>' +
            '</li>'
        var fatherV = $('.row-fluid')
        siteTabBox(view, fatherV)

        var input3 = '<label class="control-label" for="inputTime2" style="width:60px;">时间：</label>' +
            '<div class="controls labelFloat">' +
            '<select class="short" id="inputTime2" style="width:100px;">' +
            '<option value="1">最近1小时</option>' +
            '<option value="2">最近3小时</option>' +
            '<option value="3">最近6小时</option>' +
            '<option value="4">最近12小时</option>' +
            '</select>' +
            '</div>' +

            '<label class="control-label" for="grained2" style="width:60px;">粒度：</label>' +
            '<div class="controls labelFloat">' +
            '<select id="grained2" style="width: 70px;">' +
            '<option value="1">5分钟</option>' +
            '<option value="2">10分钟</option>' +
            '<option value="3">15分钟</option>' +
            '<option value="4">30分钟</option>' +
            '<option value="5">1小时</option>' +
            '<option value="6">1天</option>' +
            '</select>' +
            '</div>'
        var fatherI3 = $('.view2')
        label(input3, fatherI3)

        var input4 = '<button class="changeP btn btnFloat subInput2">查询</button>'
        label(input4, fatherI3)

        var input1 = '<label class="control-label" for="inputName" style="width:60px;">设备ID：</label>' +
            '<div class="controls labelFloat">' +
            '<select class="short" id="inputName" style="width:60px;">' +
            '</select>' +
            '</div>' +

            '<label class="control-label" for="inputTime" style="width:60px;">时间：</label>' +
            '<div class="controls labelFloat">' +
            '<select class="short" id="inputTime" style="width:100px;">' +
            '<option value="1">最近1小时</option>' +
            '<option value="2">最近3小时</option>' +
            '<option value="3">最近6小时</option>' +
            '<option value="4">最近12小时</option>' +
            '</select>' +
            '</div>' +

            '<label class="control-label" for="grained" style="width:60px;">粒度：</label>' +
            '<div class="controls labelFloat">' +
            '<select id="grained" style="width: 70px;">' +
            '<option value="1">5分钟</option>' +
            '<option value="2">10分钟</option>' +
            '<option value="3">15分钟</option>' +
            '<option value="4">30分钟</option>' +
            '<option value="5">1小时</option>' +
            '<option value="6">1天</option>' +
            '</select>' +
            '</div>'
        var fatherI1 = $('.view1')
        label(input1, fatherI1)

        var input2 = '<button class="inOut btn btnFloat subInput">查询</button>'
        label(input2, fatherI1)

        // 设备ID下拉
        for (var i = 0; i < AlldetectAlais.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + AlldetectId[i] + '">' + AlldetectAlais[i] + '</option>';
            $("#inputName").append(OHtml);
        }

        $('.subInput').click(function () {
            $('#real1').empty()


            var detectId = $('#inputName').val()
            var inputTime = $('#inputTime').val()
            var grained = $('#grained').val()

            $('.lodding1').removeClass('.hidden')
            var speedChange = speedChangeValue(detectId, getTime(inputTime), grained)
            // 行走速度变化
            $('#real1').highcharts('StockChart', {
                rangeSelector: {
                    buttonTheme: {
                        display: 'none', // 不显示按钮
                    },
                    rangeSelectorZoom: {
                        display: 'none'
                    },
                    inputEnabled: false,
                    selected: 1
                },
                title: {
                    text: '行走速度变化'
                },
                xAxis: {
                    title: null,
                    startOnTick: true,
                    endOnTick: true,
                    showLastLabel: true,
                    type: 'datetime',
                },
                yAxis: {
                    title: null
                },
                plotOptions: {
                    scatter: {
                        marker: {
                            radius: 5,
                            states: {
                                hover: {
                                    enabled: true,
                                    lineColor: 'rgb(100,100,100)'
                                }
                            }
                        },
                        states: {
                            hover: {
                                marker: {
                                    enabled: false
                                }
                            }
                        },
                        tooltip: {
                            headerFormat: '<b>{series.name}</b><br>',
                            pointFormat: '{point.x} cm, {point.y} kg'
                        }
                    }
                },
                series: [{
                    name: '突发事件行走速度m/min',
                    data: (function () {
                        var data2 = [];
                        for (var i = 0; i < speedChange.timestamp.length; i++) {
                            var time = speedChange.timestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            data2.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                speedChange.data2[i]
                            ]);
                        }

                        return data2;
                    })()
                }, {
                    name: '正常行走速度m/min',
                    data: (function () {
                        var data2 = [];
                        for (var i = 0; i < speedChange.timestamp.length; i++) {
                            var time = speedChange.timestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            data2.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                speedChange.data[i]
                            ]);
                        }

                        return data2;
                    })()
                }],
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
            });
            $('.lodding1').addClass('.hidden')

        })
        $('.subInput').click()


        // 逃生路线
        $('.subInput2').click(function () {
            $('#real2').empty()
            $('#real3').empty()

            var inputTime = $('#inputTime2').val()
            var grained = $('#grained2').val()

            var escapeLine = [];
            for (var i = 0; i < lineObj[0].length; i++) {
                var detectIds = lineObj[0][i].code;
                escapeLine[i] = escape(detectIds, getTime(inputTime), grained)
            }

            var escapeLine2 = [];
            for (var i = 0; i < lineObj[1].length; i++) {
                var detectIds = lineObj[1][i].code;
                escapeLine2[i] = escape(detectIds, getTime(inputTime), grained)
            }

            // 逃离路线1
            $('#real2').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '一号线逃离路线'
                },
                xAxis: {
                    crosshair: true,
                    categories: (function () {
                        var datax = [];
                        for (var i = 0; i < lineObj[0].length; i++) {
                            var dataxx = lineObj[0][i].name;
                            datax.push(dataxx);
                        }
                        // console.log(datax)
                        return datax;
                    })(),
                },
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                yAxis: {
                    min: 0,
                    title: null
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        borderWidth: 0
                    }
                },

                series: [{
                    name: '客流密度',
                    data: (function () {
                        var datas = [];
                        for (var n = 0; n < escapeLine.length; n++) {
                            var livess = escapeLine[n];
                            var livedatas = livess.data[livess.data.length - 1]
                            datas.push(livedatas);
                        }
                        return datas;
                    })()
                }]
            });

            // 逃离路线5
            $('#real3').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '五号线逃离路线'
                },
                xAxis: {
                    crosshair: true,
                    categories: (function () {
                        var datax = [];
                        for (var i = 0; i < lineObj[1].length; i++) {
                            var dataxx = lineObj[1][i].name;
                            datax.push(dataxx);
                        }
                        return datax;
                    })(),
                },
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                yAxis: {
                    min: 0,
                    title: null
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        borderWidth: 0
                    }
                },

                series: [{
                    name: '客流密度',
                    data: (function () {
                        var datas = [];
                        for (var n = 0; n < escapeLine2.length; n++) {
                            var livess = escapeLine2[n];
                            var livedatas = livess.data[livess.data.length - 1]
                            datas.push(livedatas);
                        }
                        return datas;
                    })()
                }]
            });
        })
        $('.subInput2').click()
    })


    //******************************************************************************************第四部分
    $('.locally4').on('click', function () {
        clickStyle($(this))
        $('.tab-select').empty()

        var view = '<li class="real-box span6 border-bg view1">' +
            '<div class="real-box1 real4">' +
            '<div class="lodding lodding1 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real1" style="width: 100%; height: 100%;margin-top:10px;"></div>' +
            '</div>' +
            '</li>' +
            '<li class="real-box span6 border-bg view2">' +
            '<div class="real-box1 real4">' +
            '<div class="lodding lodding2 hidden"><img src="images/lodding.gif"></div>' +
            '<div id="real2" style="width: 100%; height: 100%;margin-top:10px;"></div>' +
            '</div>' +
            '</li>'
        var fatherV = $('.row-fluid')
        siteTabBox(view, fatherV)

        var input1 = '<label class="control-label" for="inputId">车站区域：</label>' +
            '<div class="controls labelFloat">' +
            '<select class="short" id="inputId">' +
            '</select>' +
            '</div>' +

            '<label class="control-label" for="inputTime">时间：</label>' +
            '<div class="controls labelFloat">' +
            '<select class="short" id="inputTime" style="width:122px;">' +
            '<option value="168">最近一周</option>' +
            '<option value="720">最近一月</option>' +
            '</select>' +
            '</div>'
        var fatherI1 = $('.view1')
        label(input1, fatherI1)

        var input2 = '<button class="inOut btn btnFloat">查询</button>'
        label(input2, fatherI1)

        var input3 = '<label class="control-label" for="inputId2">线路：</label>' +
            '<div class="controls labelFloat">' +
            '<select class="short" id="inputId2">' +
            '</select>' +
            '</div>' +

            '<label class="control-label" for="inputTime2">时间：</label>' +
            '<div class="controls labelFloat">' +
            '<select class="short" id="inputTime2" style="width:122px;">' +
            '<option value="168">最近一周</option>' +
            '<option value="720">最近一月</option>' +
            '</select>' +
            '</div>'
        var fatherI3 = $('.view2')
        label(input3, fatherI3)

        var input4 = '<button class="changeP btn btnFloat">查询</button>'
        label(input4, fatherI3)

        // 车站区域下拉选项
        for (var i = 0; i < devIdList.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + devIdList[i].code + '">' + devIdList[i].name + '</option>';
            $("#inputId").append(OHtml);
        }

        for (var i = 0; i < devIdList2.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + devIdList2[i].code + '">' + devIdList2[i].name + '</option>';
            $("#inputId").append(OHtml);
        }

        // 线路下拉选项
        for (var i = 0; i < posArr.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + posArr[i].code + '" title="' + posArr[i].detectID + '">' + posArr[i].text + '</option>';
            $("#inputId2").append(OHtml);
        }
        $('.lodding1').removeClass('hidden')
        $('.lodding2').removeClass('hidden')
        // 车站区域图表
        $('.inOut').click(function () {
            $('#real1').empty()
            $('.lodding1').removeClass('hidden')

            var detectId = $('#inputId').val()
            var inputTime = $('#inputTime').val()
            var live = liveValue(detectId, getTime(inputTime))
            var base = baseValue(detectId, getTime(inputTime))

            // 车站区域
            $('#real1').highcharts('StockChart', {
                rangeSelector: {
                    buttonTheme: {
                        display: 'none', // 不显示按钮
                    },
                    rangeSelectorZoom: {
                        display: 'none'
                    },
                    inputEnabled: false,
                    selected: 1
                },
                title: {
                    text: '车站各区域客流变化'
                },
                xAxis: {
                    title: null,
                    startOnTick: true,
                    endOnTick: true,
                    showLastLabel: true,
                    type: 'datetime',
                },
                yAxis: {
                    title: null
                },
                plotOptions: {
                    scatter: {
                        marker: {
                            radius: 5,
                            states: {
                                hover: {
                                    enabled: true,
                                    lineColor: 'rgb(100,100,100)'
                                }
                            }
                        },
                        states: {
                            hover: {
                                marker: {
                                    enabled: false
                                }
                            }
                        },
                        tooltip: {
                            headerFormat: '<b>{series.name}</b><br>',
                            pointFormat: '{point.x} cm, {point.y} kg'
                        }
                    }
                },
                series: [{
                    name: '接入后客流量',
                    data: (function () {
                        var data = [];
                        for (var i = 0; i < live.timestamp.length; i++) {
                            var time = live.timestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            data.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                live.data[i]
                            ]);
                        }
                        return data;
                    })()
                }, {
                    name: '接入前客流量',
                    data: (function () {
                        var datas = [];
                        for (var i = 0; i < base.timestamp.length; i++) {
                            var time = base.timestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            datas.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                base.data[i]
                            ]);
                        }
                        return datas;
                    })()
                }],
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },

            });
            $('.lodding1').addClass('hidden')
        })

        $('.inOut').click()

        // 线路
        $('.changeP').click(function () {
            $('#real2').empty()
            $('.lodding2').removeClass('hidden')
            var detectId = $('#inputId2').val()
            var inputTime = $('#inputTime2').val()
            var inputId2 = $('#inputId2').val()

            var liveArr = []
            var baseArr = []
            var liveTimestamp
            var baseTimestamp
            // 如果是一号线
            if (inputId2 == '1') {
                char(posArr[0])
            }
            // 如果是五号线
            else {
                char(posArr[1])
            }

            function char(obj) {
                var a = obj.detectID.split(',')
                for (var i = 0; i < a.length; i++) {
                    var live = liveValue(a[i], getTime(inputTime))
                    var base = baseValue(a[i], getTime(inputTime))
                    liveTimestamp = live.timestamp
                    baseTimestamp = base.timestamp
                    if (i == 0) {
                        for (var e = 0; e < live.data.length; e++) {
                            var liveNum = parseInt(live.data[e])
                            var baseNum = parseInt(base.data[e])
                            liveArr.push(liveNum)
                            baseArr.push(baseNum)
                        }
                    } else {
                        for (var e = 0; e < live.data.length; e++) {
                            var liveNum = parseInt(live.data[e])
                            var baseNum = parseInt(base.data[e])
                            liveArr[e] = liveArr[e] + liveNum
                            baseArr[e] = baseArr[e] + baseNum
                        }
                    }
                }
            }

            $('#real2').highcharts('StockChart', {
                rangeSelector: {
                    buttonTheme: {
                        display: 'none', // 不显示按钮
                    },
                    rangeSelectorZoom: {
                        display: 'none'
                    },
                    inputEnabled: false,
                    selected: 1
                },
                title: {
                    text: '出行行为变化'
                },
                xAxis: {
                    title: null,
                    startOnTick: true,
                    endOnTick: true,
                    showLastLabel: true,
                    type: 'datetime',
                },
                yAxis: {
                    title: null
                },
                plotOptions: {
                    scatter: {
                        marker: {
                            radius: 5,
                            states: {
                                hover: {
                                    enabled: true,
                                    lineColor: 'rgb(100,100,100)'
                                }
                            }
                        },
                        states: {
                            hover: {
                                marker: {
                                    enabled: false
                                }
                            }
                        },
                        tooltip: {
                            headerFormat: '<b>{series.name}</b><br>',
                            pointFormat: '{point.x} cm, {point.y} kg'
                        }
                    }
                },
                series: [{
                    name: '接入后客流量',
                    data: (function () {
                        var data = [];
                        for (var i = 0; i < liveTimestamp.length; i++) {
                            var time = liveTimestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            data.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                liveArr[i]
                            ]);
                        }
                        return data;
                    })()
                }, {
                    name: '接入前客流量',
                    data: (function () {
                        var datas = [];
                        for (var i = 0; i < baseTimestamp.length; i++) {
                            var time = baseTimestamp[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                            var timey = parseInt(time[0]),
                                timem = parseInt(time[1] - 1),
                                timed = parseInt(time[2]),
                                timeh = parseInt(time[3]),
                                timemi = parseInt(time[4]),
                                times = parseInt(time[5]);
                            datas.push([
                                Date.UTC(timey, timem, timed, timeh, timemi, times),
                                baseArr[i]
                            ]);
                        }
                        return datas;
                    })()
                }],
                exporting: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
            });
            $('.lodding2').addClass('hidden')
        })

        $('.changeP').click()
    })


    //input内容区
    function label(data, father) {
        var input = '<div class="form-horizontal">' +
            '<div class="control-group">' + data + '</div>' +
            '</div>'
        father.append(input)
        father.children().css('display', 'inline-block')
    }

    // 标签点击之后增加背景颜色
    function clickStyle(ele) {
        var eleChildren = ele.parent().children('li')
        eleChildren.removeClass('active')
        ele.addClass('active')
    }

    //视图内容区
    function siteTabBox(data, father) {
        father.empty()
        var content = '<ul class="thumb clearfix">' + data + '</ul>'
        father.append(content)
    }

    $('.locally1').click()
})