$(function () {
    var $floorPlan = $(".floor-plan");
    var floorPlan = document.getElementsByClassName('floor-plan')[0]
    var flooBoxW = parseInt($('.floor-plan-box').width())
    var flooBoxH = parseInt($('.floor-plan-box').height())
    var AlldetectId = []
    var AlldetectAlais = []
    
    $.ajax({
        url: "" + ajaxUrl + "/guangzhou/mon/device/list",
        type: "GET",
        dataType: "json",
        async: false,
        success: function (devices) {
            if (devices.code == "10000") {
                if (devices.data !== null) {
                    for (var i = 0; i < devices.data.length; i++) {
                        AlldetectId.push(devices.data[i].detect_id)
                        AlldetectAlais.push(devices.data[i].alais)
                    }
                }
            } else {
                alert(devices.msg)
            }
        }
    });

    //ishome页面?
    var LocString = location.href
    var isHome = LocString.indexOf("home") != -1

    //添加关联监测对象模态框
    var modHtml = '<div id="example" class="modal hide fade in" style="display: none; ">' +
        '<div class="modal-header">' +
        '<a class="close" data-dismiss="modal">×</a>' +
        '<h4>添加关联检测对象</h4>' +
        '</div>' +
        '<div class="modal-body">' +
        '<table class="table table-org table-hover" cellpadding="0" cellspacing="0" border="0">' +
        '<tr>' +
        '<th width="20%">设备IP</th>' +
        '<th width="20%">监测类型</th>' +
        '<th width="20%">设备别名</th>' +
        '<th width="20%">位置</th>' +
        '<th width="10%">面积</th>' +
        '<th width="10%">操作</th>' +
        '</tr>' +
        '</table>' +
        '</div>' +
        '</div>';
    $('#mod').append(modHtml);

    // 桥接元素--添加/视频
    var AHtml = '<div class="addObjBtn" data-toggle="modal" href="#example"></div>'
    $('.floor-plan-box').append(AHtml);
    var BHtml = '<div class="modVideoC" data-toggle="modal" href="#modVideoD"></div>'
    $('.floor-plan-box').append(BHtml);

    if (isHome) {
        var uniqueId = control.home.uniqueId;
        passengerStatus(uniqueId)
        getDevLiFn(uniqueId)
    }

    // 获取域定义表
    function getDef(domainType) {
        var domainArr = []
        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/code/defs",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                domainType: domainType
            },
            success: function (def) {
                if (def.code == "10000") {
                    if (def.data !== null) {
                        for (var i = 0; i < def.data.length; i++) {
                            domainArr.push({ code: def.data[i].domain_value, name: def.data[i].domain_name })
                        }
                    }
                } else {
                    alert(def.msg)
                }
            }
        })
        return domainArr
    }

    // 获取监测对象ID
    function getDevId(deviceType) {
        var DevId = []
        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/mon/objects",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                deviceType: deviceType
            },
            success: function (def) {
                if (def.code == "10000") {
                    if (def.data !== null) {
                        for (var i = 0; i < def.data.length; i++) {
                            DevId.push({ code: def.data[i].detect_id, name: def.data[i].alais })
                        }
                    }
                } else {
                    alert(def.msg)
                }
            }
        })
        return DevId
    }

    // 获取颜色ID
    function getColorId() {
        var DevId = []
        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/staus/colors",
            type: "get",
            async: false,
            dataType: "json",
            data: {},
            success: function (def) {
                if (def.code == "10000") {
                    if (def.data !== null) {
                        for (var i = 0; i < def.data.length; i++) {
                            DevId.push({ code: def.data[i].id, name: def.data[i].color_desc })
                        }
                    }
                } else {
                    alert(def.msg)
                }
            }
        })
        return DevId
    }

    //获取弹窗设备设施列表
    function getDevLiFn(uniqueId) {
        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/mon/device/list",
            type: "GET",
            dataType: "json",
            async: false,
            success: function (devices) {
                if (devices.code == "10000") {
                    if (devices.data !== null) {
                        var arr = getDef(0)

                        for (var i = 0; i < devices.data.length; i++) {
                            var detectItem = '';
                            for (var j = 0; j < arr.length; j++) {
                                if (devices.data[i].detect_item == arr[j].code) {
                                    detectItem = arr[j].name
                                }
                            }

                            AlldetectId.push(devices.data[i].detect_id)
                            AlldetectAlais.push(devices.data[i].alais)

                            var OHtml = '';
                            OHtml += '<tr class="listTr" detectId="' + devices.data[i].detect_id + '">' +
                                '<td>' + devices.data[i].device_ip + '</td>' +
                                '<td>' + detectItem + '</td>' +
                                '<td>' + devices.data[i].alais + '</td>' +
                                '<td>' + devices.data[i].location + '</td>' +
                                '<td>' + devices.data[i].area + '</td>' +
                                '<td><button class="btn addObject" type="button">添加</button></td>' +
                                '</tr>';
                            $(".table-org").append(OHtml);
                        }
                        addObjFn(uniqueId)
                    }
                } else {
                    alert(devices.msg)
                }
            }
        });
    }

    // 平面图右下角颜色说明
    for (var i = 0; i < statusArr.length; i++) {
        var html = ''
        html += '<li><div class="exampleDiv" style="background-color:' + statusArr[i].color + '">' + statusArr[i].text + '</div></li>'
        $(".example").append(html);
    }

    //获取实时客流状态
    function passengerStatus(uniqueId) {
        $(".floor-plan .objBtn").remove()
        $.ajax({
            url: "" + ajaxUrl + "/guangzhou/live/passenger/status",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                unique_id: uniqueId
            },
            success: function (objects) {
                if (objects.code == "10000") {
                    if (objects.data !== null) {
                        if (isHome) {// 右侧页面，此种拥挤度有几处，当前是home页面的时候才运行
                            var statusLevel = [
                                { text: '畅通', num: 0 },
                                { text: '轻度拥堵', num: 0 },
                                { text: '中度拥堵', num: 0 },
                                { text: '严重拥堵', num: 0 }
                            ];
                        }

                        var color
                        var detect_id = []
                        for (var i = 0; i < objects.data.length; i++) {
                            detect_id.push({
                                detect_id: objects.data[i].detect_id,
                                detect_alais: objects.data[i].alais
                            });


                            if (isHome) {// 右侧页面，此种拥挤度有几处，当前是home页面的时候才运行
                                statusLevel[objects.data[i].status_level].num++;
                            }

                            // 设备颜色
                            for (var j = 0; j < statusArr.length; j++) {
                                if (statusArr[j].value == objects.data[i].status_level) {
                                    color = statusArr[j].color
                                }
                            }
                            // 设备位置、颜色
                            var OHtml = '';
                            OHtml += '<div class="site-btn objBtn" ' +
                                'detectId="' + objects.data[i].detect_id + '" ' +
                                'imgX="' + objects.data[i].image_x + '" ' +
                                'imgY="' + objects.data[i].image_y + '" ' +
                                'alais="' + objects.data[i].alais + '" ' +
                                'title="密度：' + objects.data[i].density + '&#10;流量：' + objects.data[i].pass_nums + '" ' +
                                'style="left:' + objects.data[i].image_x + '%;top:' + objects.data[i].image_y + '%;background-color:' + color + '">' + objects.data[i].alais + '</div>';

                            $(".floor-plan").append(OHtml);
                        }

                        window['detectID'] = detect_id

                        if (isHome) {// 右侧页面，此种拥挤度有几处，当前是home页面的时候才运行
                            for (var s = 0; s < statusLevel.length; s++) {
                                var html = '';
                                html += '<li class="clearfix">' +
                                    '<div class="status-name">' + statusLevel[s].text + '</div>' +
                                    '<div class="status-num">' + statusLevel[s].num + '</div>' +
                                    '</li>';
                                $(".statusLevelUl").append(html);
                            }
                        }

                        //右键事件
                        $('.objBtn').delegate($('.objBtn'), 'contextmenu', function (e) {
                            e.preventDefault();//禁止浏览器默认右键事件
                        });

                        $('.objBtn').delegate($('.objBtn'), 'click', function (e) {
                            e.stopPropagation();//停止冒泡事件
                        });

                        $('.objBtn').delegate($('.objBtn'), 'mousedown', function (e) {

                            if (e.which == 3) {
                                e.stopPropagation();//停止冒泡事件
                                $('.FixDiv').remove()
                                $('.objBtn').css('z-index', 100);
                                $('.followDiv').css('z-index', 90);
                                var $siteBtn = $(e.target);
                                e = e || window.event;
                                var pointX = e.pageX;
                                var pointY = e.pageY;
                                var $siteBtnTop = parseInt($siteBtn.offset().top),
                                    $siteBtnLeft = parseInt($siteBtn.offset().left);
                                var x = pointX - $siteBtnLeft;
                                var y = pointY - $siteBtnTop;

                                $('.rightUl').remove();
                                var RHtml
                                if (isHome) {
                                    RHtml = '<ul class="rightUl">' +
                                        '<li class="delObject">删除</li>' +
                                        '<li class="playerVideo" data-toggle="modal" href="#modVideo">播放</li>' +
                                        '</ul>';
                                } else {
                                    RHtml = '<ul class="rightUl">' +
                                        '<li class="delObject">删除</li>' +
                                        '</ul>';
                                }
                                $(e.target).append(RHtml);

                                $(e.target).css('z-index', 1000);
                                $('.rightUl').css({
                                    display: 'block',
                                    left: x + 'px',
                                    top: y + 'px'
                                });

                                //删除关联监测对象
                                $('.delObject').click(function (e) {
                                    e.stopPropagation();//停止冒泡事件
                                    var detectId = $(this).parents('.objBtn').attr('detectId');
                                    var yes = confirm("确定删除：" + detectId + "？");
                                    if (yes) {
                                        $.ajax({
                                            url: "" + ajaxUrl + "/guangzhou/object",
                                            type: "DELETE",
                                            dataType: "json",
                                            contentType: "application/json",
                                            data: JSON.stringify({
                                                "unique_id": uniqueId,
                                                "detect_id": detectId,
                                            }),
                                            success: function (udelres) {
                                                if (udelres.code == "10000") {
                                                    $(this).parents('.objBtn').remove()
                                                } else {
                                                    alert('删除失败')
                                                    window.location.reload();
                                                }
                                            }
                                        })
                                    } else {
                                        $('.rightUl').remove()
                                    }
                                })

                                if (isHome) {
                                    // 点击弹出播放视频模态框
                                    $('.playerVideo').click(function () {
                                        $('.videoBoxB').empty()
                                        var alais = $(this).parents('.objBtn').attr('alais');
                                        var videoUrl = '2200001201000201'
                                        var html = '<iframe src="http://47.93.44.39:18080/thirdparty/formInterface.do?cmd=120101&id=' + videoUrl + '" frameborder="0" style="width:100%;height:400px;"></iframe>'
                                        $('.videoBoxB').append(html)
                                        $('.videoModTitle').text(alais)
                                        $('.modVideoC').click()
                                    })
                                }
                            }
                        });

                        $('.objBtn').hover(function () {
                            $('.followDiv').hide()
                        }, function () {
                            $('.followDiv').show()
                            $('.rightUl').remove()
                        })

                    }
                } else {
                    alert(objects.msg)
                }
            }
        });
    }

    //右键添加监测对象代码块
    var mousemove
    mousemove = 0
    $floorPlan.hover(function () {
        mousemoveFun()
        floorPlan.addEventListener('DOMMouseScroll', scrollFunc, false);
        floorPlan.onmousewheel = scrollFunc;
    }, function () {
        $('.followDiv').remove()
        $('.FixDiv').remove()
        mousemove = 0
    });

    function mousemoveFun() {
        $('.followDiv').remove()
        var followDiv = '<div class="site-btn followDiv">添加</div>';
        $('.floor-plan').append(followDiv);
        var w = $floorPlan.width() - $('.followDiv').width()
        var h = $floorPlan.height() - $('.followDiv').height()
        //鼠标移动事件
        if (mousemove == 0) {
            $floorPlan.mousemove(function (e) {
                var b = parseInt($floorPlan.height())
                var c = parseInt($floorPlan.width())
                var x = (mouPos(e)[0] / c) * 100
                var y = (mouPos(e)[1] / b) * 100
                followDivCss(x, y)
            });
        }
    }

    //右键事件
    $floorPlan.delegate($floorPlan, 'contextmenu', function (e) {
        e.preventDefault();//禁止浏览器默认右键事件
    });

    $floorPlan.delegate($floorPlan, 'click', function (e) {
        e.stopPropagation();//停止冒泡事件
    });

    $floorPlan.delegate($floorPlan, 'mousedown', function (e) {
        if (e.which == 3) {
            mousemove = 1
            $floorPlan.unbind('mousemove')
            var imageX = $('.followDiv').attr('imageX')
            var imageY = $('.followDiv').attr('imageY')

            $('.FixDiv').remove()
            $('.followDiv').remove()
            $('.rightUl').remove()
            var FixDiv = '<div class="site-btn FixDiv">添加</div>'
            $('.floor-plan').append(FixDiv)
            $('.FixDiv').css({
                left: imageX + '%',
                top: imageY + '%'
            })

            $('.FixDiv').hover(function () {

            }, function () {
                $('.FixDiv').remove()
                mousemove = 0
                mousemoveFun()
            })

            if (typeof imageX != "string") {
                $('.FixDiv').remove()
                mousemove = 0
                mousemoveFun()
            }

            $('.rightObj').remove();
            var RHtml = '<ul class="rightObj">' +
                '<li class="addObjectLi">添加</li>' +
                '</ul>';
            $('.FixDiv').append(RHtml);

            var uw = $('.rightObj').width()
            var uh = $('.rightObj').height()

            $('.rightObj').css({
                display: 'block',
                left: 20 + 'px',
                top: 20 + 'px',
                color: 'black'
            });

            var fl = e.pageX - parseInt($('.floor-plan-box').offset().left);
            var ft = e.pageY - parseInt($('.floor-plan-box').offset().top);
            if ((flooBoxW - fl) < uw) {
                var l = -uw + 20
                $('.rightObj').css('left', l + 'px');
            }
            if ((flooBoxH - ft) < uh) {
                var t = -uh + 20
                $('.rightObj').css('top', t + 'px');
            }

            $('.addObjectLi').click(function () {
                $.cookie('imageX', imageX)
                $.cookie('imageY', imageY)
                $('.addObjBtn').click()
            })
        }
    });

    function addObjFn(uniqueId) {
        //添加页面关联监测对象
        $(".table-org .addObject").unbind('click')
        $(".table-org").on("click", ".addObject", function () {
            var detectId = $(this).parents('.listTr').attr('detectId');
            var imageX = $.cookie('imageX');
            var imageY = $.cookie('imageY');
            $.ajax({
                url: "" + ajaxUrl + "/guangzhou/object",
                type: "post",
                dataType: "json",
                contentType: "application/json",
                data: {
                    "unique_id": uniqueId,
                    "detect_id": detectId,
                    "image_x": imageX,
                    "image_y": imageY
                },
                success: function (data) {
                    if (data.code == "10000") {
                        alert('添加设备设施成功！');
                        if (clas == 'home') {
                            parent.location.reload();
                        }
                        $('.' + clas + '').click()
                    } else {
                        alert(data.msg)
                    }
                }
            })
        });
    }

    //点击添加/返回按钮事件
    function backBtn() {
        $('.list').toggleClass('open');
        $('.tableList').toggleClass('hidden');
        $('.add').toggleClass('back');
        $('.del').toggleClass('hidden');
        $('.choose').toggleClass('hidden');
        $('.setDef').toggleClass('hidden');
        $('.reload').toggleClass('hidden');
        $('.left>ul').toggleClass('hidden');
        $('.list input').val('')
        $('.cover').toggleClass('hidden');// 蒙板
    }

    // 点击空白删除右击下拉菜单
    $(document.body).click(function () {
        $('.rightUl').remove()
    });

    //模态框关闭事件
    $('#example').on('hidden.bs.modal', function () {
        $('.FixDiv').remove();
        $('.form-group input').val('')
    });

    //判断选项是否被选中
    function check(ele) {
        if (ele.checked) {
            return 1
        } else {
            return 0
        }
    }

    // 获取最近n 小时 时间戳
    function getTime(time) {
        var timeZ = 1000 * 60 * 60 * parseInt(time)
        var nowTime = (new Date()).valueOf() - timeZ
        return getMoth(nowTime)
    }

    // 获取已知时间setTime加几个小时time之后的时间格式
    // 如果是两个值，第一个值是已知时间，第二个值是要加上的小时
    // 如果是三个值，第一个值是已知时间，第二个值是要加上的小时，第三个值是要加上的秒数（1秒就是1）
    function getTimee(setTime, time, s) {
        if (arguments.length == 2) {
            var timeZ = 1000 * 60 * 60 * parseInt(time)
            var nowTime = (new Date(setTime)).valueOf() + timeZ
            return getMoth(nowTime)
        } else {
            var timeZ = 1000 * 60 * 60 * parseInt(time) + s * 1000
            var nowTime = (new Date(setTime)).valueOf() + timeZ
            return getMoth(nowTime)
        }
    }

    // 将时间戳转换为能看懂的时间格式
    function getMoth(str) {
        var oDate = new Date(str),
            y = oDate.getFullYear(),
            m = oDate.getMonth() + 1,
            d = oDate.getDate(),
            h = oDate.getHours(),
            mi = oDate.getMinutes(),
            s = oDate.getSeconds()
        oTime = getzf(y) + '-' + getzf(m) + '-' + getzf(d) + ' ' + getzf(h) + ':' + getzf(mi) + ':' + getzf(s);
        return oTime;
    };
    function getzf(num) {//补零
        if (parseInt(num) < 10) {
            num = '0' + num;
        }
        return num;
    }

    // 鼠标滚轮放大设备
    var scrollFunc = function (e) {
        e.preventDefault();
        e = e || window.event;
        if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件  
            if (e.wheelDelta > 0) {
                big(e)
            }
            if (e.wheelDelta < 0) {
                sma(e)
            }
        } else if (e.detail) {  //Firefox滑轮事件
            if (e.detail > 0) {
                sma(e)
            }
            if (e.detail < 0) {
                big(e)
            }
        }
    }
    var a = 0
    var m = 0
    var timeT
    // 还原平面图尺寸
    $('.comeBack').click(function () {
        comeBackFn()
    })

    function comeBackFn() { 
        a = 0
        m = 0
        $floorPlan.css({
            height: flooBoxH + 'px',
            width: flooBoxW + 'px',
            top: 0 + 'px',
            left: 0 + 'px'
        })
        $('.comeBack').addClass('hidden')
     }


    // 获取鼠标在平面图的坐标
    function mouPos(e) {
        var e = e || window.event
        var pointX = e.pageX;
        var pointY = e.pageY;
        var ft = parseInt($floorPlan.offset().top)
        var fl = parseInt($floorPlan.offset().left);
        var x = pointX - fl;
        var y = pointY - ft;
        return [x, y]
    }

    function big(e) {
        var num = 60
        var b = parseInt($floorPlan.height())
        var c = parseInt($floorPlan.width())
        var aa = mouPos(e)[0] / c
        var bb = mouPos(e)[1] / b
        var el = aa * 100
        var et = bb * 100
        followDivCss(el, et)
        var l = aa * num * (-1)
        var t = bb * num * (-1)
        a = a + l
        m = m + t
        $floorPlan.css({
            height: num + b + 'px',
            width: num + c + 'px',
            top: m + 'px',
            left: a + 'px'
        })
        if (b == flooBoxH) {
            $('.comeBack').removeClass('hidden')
        }

    }

    function sma(e) {
        var num = 60
        var b = parseInt($floorPlan.height())
        var c = parseInt($floorPlan.width())
        var aa = mouPos(e)[0] / c
        var bb = mouPos(e)[1] / b
        var el = aa * 100
        var et = bb * 100
        followDivCss(el, et)
        var l = aa * num
        var t = bb * num
        a = a + l
        m = m + t
        if (b < flooBoxH || b == flooBoxH) {
            b = flooBoxH
            c = flooBoxW
            $('.comeBack').addClass('hidden')
        } else {
            b = b - num
            c = c - num
        }
        a > 0 ? a = 0 : a
        m > 0 ? m = 0 : m
        $floorPlan.css({
            height: b + 'px',
            width: c + 'px',
            top: m + 'px',
            left: a + 'px'
        })
    }

    function followDivCss(x, y) {
        $('.followDiv').attr('imageX', x);
        $('.followDiv').attr('imageY', y);
        $('.followDiv').css({
            left: x + '%',
            top: y + '%'
        })
    }

    window['getDef'] = getDef;
    window['getDevId'] = getDevId
    window['check'] = check
    window['uniqueId'] = uniqueId
    window['getColorId'] = getColorId
    window['getTime'] = getTime
    window['getTimee'] = getTimee
    window['getMoth'] = getMoth
    window['AlldetectId'] = AlldetectId
    window['AlldetectAlais'] = AlldetectAlais
    window['backBtn'] = backBtn
    window['passengerStatus'] = passengerStatus
    window['getDevLiFn'] = getDevLiFn
    window['comeBackFn'] = comeBackFn
});