$(function () {
    var chartT
    var nowTime = (new Date()).valueOf();//当前时间戳
    $('#startTime').val(getMoth(nowTime))

    var data = control.forecast
    //按钮下拉框
    $('.devType').empty()
    for (var i = 0; i < data.length; i++) {
        var html = ''
        html += '<li class="pic' + i + '" uniqueId="' + data[i].uniqueId + '" imgSrc="' + data[i].src + '">' + data[i].text + '</li>'
        $('.devType').append(html)
    }
    changeBGC()
    $('.pic0').click()

    // 各li点击事件
    function changeBGC() {
        var uniqueId
        $('.devType li').each(function () {
            $(this).click(function () {
                $('.devType li').css({
                    backgroundColor: 'white',
                    color: 'black'
                })
                $(this).css({
                    backgroundColor: '#379be9',
                    color: '#fff'
                })

                comeBackFn()//还原平面图尺寸
                uniqueId = $(this).attr('uniqueId')
                var imgSrc = $(this).attr('imgSrc')
                $('.imgSrc').attr('src', imgSrc)
                passengerStatus(uniqueId)
                devOp()

                var detectId = $('#devClassN').val()
                var inputGrained = $('#inputGrained').val()
                var startTime1 = parseInt($('#startTime').val())
                var startTime = getTime(startTime1)
                chart(detectId, inputGrained, startTime)
                // 点击左侧设备设施，右侧列表显示对应数据
                $('.objBtn').unbind('click')
                $('.objBtn').click(function () {
                    var aa = $(this).attr('detectId')
                    $('#devClassN').val(aa)
                    var detectId = aa
                    var inputGrained = $('#inputGrained').val()
                    var startTime1 = parseInt($('#startTime').val())
                    var startTime = getTime(startTime1)

                    if (startTime == '') {
                        alert('日期不能为空')
                        return false
                    }
                    chart(detectId, inputGrained, startTime)
                    chartT = setInterval(function () {
                        chart(detectId, inputGrained, startTime)
                    }, 10000)
                })
                getDevLiFn(uniqueId)
            })
        })
    }

    // 设备设施下拉列表
    function devOp() {
        $("#devClassN").empty()
        for (var i = 0; i < detectID.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + detectID[i].detect_id + '">' + detectID[i].detect_alais + '</option>';
            $("#devClassN").append(OHtml);
        }
    }

    // -------------------------------------------------------------------------

    // 设备设施下拉列表
    $("#devClassN").empty()
    for (var i = 0; i < detectID.length; i++) {
        var OHtml = '';
        OHtml += '<option value="' + detectID[i].detect_id + '">' + detectID[i].detect_alais + '</option>';
        $("#devClassN").append(OHtml);
    }

    // 控制图表折线颜色
    Highcharts.setOptions({
        colors: ['#5b9bd5', '#ed7d31'],
        lang: {
            rangeSelectorZoom: '',
        },
    });

    //获取客流趋势
    $('.submit').click(function () {
        clearInterval(chartT);
        var detectId = $('#devClassN').val()
        var inputGrained = $('#inputGrained').val()
        var startTime1 = parseInt($('#startTime').val())
        var startTime = getTime(startTime1)
        if (startTime == '') {
            alert('日期不能为空')
            return false
        }
        chart(detectId, inputGrained, startTime)

        chartT = setInterval(function () {
            chart(detectId, inputGrained, startTime)
        }, 1000 * 60 * 5)
    })

    function chart(detectId, inputGrained, startTime) {
        $('.lodding').removeClass('hidden')
        var live = {
            deviceNum: [],
            numValue: [],
            densityValue: [],
            time: []
        }
        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/passenger/trend",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                "detect_id": detectId,
                "grained": parseInt(inputGrained),
                "start": startTime,
            },
            success: function (def) {
                console.log(def)
                if (def.code == "10000") {
                    if (def.data !== null) {
                        live.deviceNum.push(def.data[0].device_num)
                        for (var i = 0; i < def.data.length; i++) {
                            live.time.push(def.data[i].timestamp)
                            live.numValue.push(parseInt(def.data[i].num_value))
                            live.densityValue.push(parseFloat(def.data[i].density_value))
                        }
                    }
                } else {
                    alert(def.msg)
                }
            }
        });
        //流量
        $('#chart1').empty()
        $('#chart2').empty()

        Highcharts.setOptions({
            colors: ['#5b9bd5', '#ed7d31'],
            lang: {
                printChart: '打印图表',
                downloadJPEG: '下载 JPEG 文件',
                downloadPDF: '下载 PDF  文件',
                downloadPNG: '下载 PNG  文件',
                downloadSVG: '下载 SVG  文件',
                downloadCSV: '下载 CSV  文件',
                downloadXLS: '下载 XLS  文件',
                viewData: '查看数据表格'
            },
            exporting: {
                url: 'https://export.highcharts.com.cn'
            },
        });
        $('#chart1').highcharts('StockChart', {
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
                text: '客流量'
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
                name: live.deviceNum[0],
                data: (function () {
                    var data1 = [];
                    for (var i = 0; i < live.time.length; i++) {
                        var time = live.time[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                        var timey = parseInt(time[0]),
                            timem = parseInt(time[1] - 1),
                            timed = parseInt(time[2]),
                            timeh = parseInt(time[3]),
                            timemi = parseInt(time[4]),
                            times = parseInt(time[5]);
                        data1.push({
                            x: Date.UTC(timey, timem, timed, timeh, timemi, times),
                            y: live.numValue[i]
                        });
                        // console.log(data1)
                    }
                    return data1;
                })()
            }],
            credits: {
                enabled: false
            },
        });
        $('.lodding1').addClass('hidden')
        //密度
        Highcharts.setOptions({
            colors: ['#5b9bd5', '#ed7d31'],
            lang: {
                printChart: '打印图表',
                downloadJPEG: '下载 JPEG 文件',
                downloadPDF: '下载 PDF  文件',
                downloadPNG: '下载 PNG  文件',
                downloadSVG: '下载 SVG  文件',
                downloadCSV: '下载 CSV  文件',
                downloadXLS: '下载 XLS  文件',
                viewData: '查看数据表格'
            },
            exporting: {
                url: 'https://export.highcharts.com.cn'
            },
        });
        $('#chart2').highcharts('StockChart', {
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
                text: '客流密度'
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
                name: live.deviceNum[0],
                data: (function () {
                    var data1 = [];
                    for (var i = 0; i < live.time.length; i++) {
                        var time = live.time[i].split("-").join(",").split(" ").join(",").split(":").join(",").split(",");
                        var timey = parseInt(time[0]),
                            timem = parseInt(time[1] - 1),
                            timed = parseInt(time[2]),
                            timeh = parseInt(time[3]),
                            timemi = parseInt(time[4]),
                            times = parseInt(time[5]);
                        data1.push({
                            x: Date.UTC(timey, timem, timed, timeh, timemi, times),
                            y: live.densityValue[i]
                        });
                    }
                    return data1;
                })()
            }],
            credits: {
                enabled: false
            },

        });
        $('.lodding2').addClass('hidden')
    }

})
