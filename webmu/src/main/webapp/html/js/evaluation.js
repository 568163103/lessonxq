
$(function () {

    var b = 0
    var nowTime = (new Date()).valueOf();//当前时间戳
    $('#startTime').val(getMoth(nowTime))
    var cur_time = getMoth(nowTime)


    var data = control.evaluation
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
                $('#btnSure').unbind('click')
                $('#btnSure').click(function () {// 获取时间轴数据
                    var startTime = $('#startTime').val()
                    var end = getTimee(startTime, 1)
                    timeline(startTime, end, uniqueId)
                })
                getDevLiFn(uniqueId)
            })
        })
    }

    // ----------------------------------------------------------------------------------

    $('#btnSure').click()

    $('.next').mousedown(function (e) {
        var a = $('.parul').css('left')
        var d = parseInt(a.split('.')[0].replace(/[^0-9]/ig, ""))
        var b = $('.parul').width()
        var c = $('.tempWrap').width()
        if (b - d + 50 < c) {
            var STime = $('.parul li:last').attr('timestamp')
            var end = getTimee(STime, 1, 1)
            timeline(STime, end, uniqueId)
        }
    })

    // 获取时间轴数据
    function timeline(STime, ETime, uniqueId) {
        $('#startTime').val(STime)
        $(".eva-table .lineTr").empty()
        $(".parul").empty()
        $.ajax({
            url: ""+ajaxUrl+"/guangzhou/device/status/times",
            type: "get",
            dataType: "json",
            data: {
                "unique_id": uniqueId,
                "start": STime,
                "end": ETime
            },
            success: function (objects) {
                if (objects.code == "10000") {
                    if (objects.data !== null) {
                        for (var i = 0; i < objects.data.length; i++) {
                            var timeSub
                            var a = i - 1
                            var mon = objects.data[i].timestamp.split('-')[1].split('-')[0]
                            var day = objects.data[i].timestamp.split('-')[2].split(' ')[0]

                            var hour = objects.data[i].timestamp.split(' ')[1].split(':')[0]
                            var min = objects.data[i].timestamp.split(':')[1]
                            if (i > 0) {
                                var Oday = objects.data[a].timestamp.split('-')[2].split(' ')[0]
                                timeSub = hour + ':' + min
                                if (parseInt(day) > parseInt(Oday)) {
                                    timeSub = mon + '-' + day
                                } else {
                                    timeSub = hour + ':' + min
                                }
                            } else {
                                timeSub = hour + ':' + min
                            }
                            var SHtml = '';
                            SHtml += '<li class="time-btn time-btn' + i + '" timestamp="' + objects.data[i].timestamp + '">' +
                                '<div class="timeline-c"></div>' +
                                '<span>' + timeSub + '</span>' +
                                '</li>';
                            $(".parul").append(SHtml);
                        }
                        // 时间轴插件
                        $(".time-box").css("display", "block")
                        var parHdW = $(".time-box").width() - 80;
                        var visN = parseInt(parHdW / 120);
                        jQuery("#Js_Show").slide({
                            mainCell: ".bd ul",
                            autoPlay: false,
                            effect: "left",
                            vis: visN,
                            interTime: 50,
                            trigger: "click",
                            pnLoop: false,
                            autoPage: true
                        })
                        $(".time-btn0").click()
                    }
                } else {
                    alert(objects.msg)
                }
            }
        });

    }

    // 获取表格数据
    $(".parul").delegate(".time-btn", "click", function () {
        $(this).addClass("cur").siblings("li").removeClass("cur");
        $(".eva-table .lineTr").empty()
        var timestamp = $(this).attr('timestamp')
        $("#btnExport").attr('timestamp', timestamp)
        $.ajax({
            url: ""+ajaxUrl+"/guangzhou/device/status",
            type: "get",
            dataType: "json",
            data: {
                "unique_id": uniqueId,
                "timestamp": timestamp
            },
            success: function (objectstime) {
                if (objectstime.code == "10000") {
                    if (objectstime.data !== null) {
                        for (var i = 0; i < objectstime.data.length; i++) {
                            var statuslDesc1
                            var a = parseInt(objectstime.data[i].status_level) + 1
                            for (var j = 0; j < statusArr.length; j++) {
                                if (statusArr[j].value == objectstime.data[i].status_level)
                                    statuslDesc1 = statusArr[j].text
                            }

                            var OHtml = '';
                            OHtml += '<tr class="lineTr">' +
                                '<td class="arrow"><img src="images/' + objectstime.data[i].status_level + '.png">' + objectstime.data[i].alais + '</td>' +
                                '<td class="arrow">' + objectstime.data[i].detect_id + '</td>' +
                                '<td>' + objectstime.data[i].density + '</td>' +
                                '<td>' + objectstime.data[i].nums + '</td>' +
                                '<td>' + statuslDesc1 + '</td>' +
                                '</tr>';
                            $(".eva-table").append(OHtml);
                        }
                    }
                } else {
                    alert(objectstime.msg)
                }
            }
        })
    })
    $('.time-btn0').click()

    

    // 获取客流控制措施
    $.ajax({
        url: ""+ajaxUrl+"/guangzhou/control/method",
        type: "get",
        dataType: "json",
        data: {
            "cur_time": cur_time
        },
        success: function (objects) {
            if (objects.code == "10000") {
                if (objects.data !== null) {
                    var OHtml = '';
                    OHtml += '<li class=""><h4>客流控制措施</h4></li>' +
                        '<li>开始时间：<span>' + objects.data.start_time + '</span></li>' +
                        '<li>结束时间：<span>' + objects.data.end_time + '</span></li>' +
                        '<li>建议采用：<span>' + objects.data.suggest + '</span></li>' +
                        '<li>具体措施：<span>' + objects.data.method + '</span></li>'
                    $(".stream").append(OHtml);

                    var Html = '';
                    Html += '<h4 style="display:inline-block">预警状态：</h4><span>' + objects.data.warn + '</span>'
                    $(".yjstatus").append(Html);
                }
            } else {
                alert(objects.msg)
            }
        }
    });

    // 下载Excel表格
    $("#btnExport").click(function () {
        var timestamp = $(this).attr('timestamp')
        $(".eva-table").table2excel({
            exclude: ".noExl", //过滤位置的 css 类名
            filename: "车站关键设备状态评估表" + timestamp + ".xls", //文件名称  
            name: "Excel Document Name.xlsx",
            exclude_img: true,
            exclude_links: true,
            exclude_inputs: true
        });
    });
})