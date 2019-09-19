/**
 * Created by Administrator on 2017/12/16.
 */
$(function () {
    var arr = getDef(2)
    var itemArr = getDef(0)
    var colorID = getColorId()
    //***********************************************************************************第一项，设备设施
    $('.liDevice').click(function () {
        // 表头
        var text = $(this).text()
        $('.center').text(text);

        $('.left>ul').remove()
        $('.del').remove()
        var uHtml = '<button class="btn del">删除</button>';
        $(".left").append(uHtml);

        $('.reload').remove()
        var RHtml = '<button class="btn reload">更新</button>';
        $(".left").append(RHtml);

        $(".tableList").empty();
        var tHtml = '<tr>' +
            '<th width="20%">设备IP</th>' +
            '<th width="15%">监测类型</th>' +
            '<th width="15%">设备ID</th>' +
            '<th width="20%">位置</th>' +
            '<th width="10%">面积</th>' +
            '<th width="10%">别名</th>' +
            '<th width="10%">设备类别</th>' +
            '</tr>';
        $(".tableList").append(tHtml);

        //************************************************************获取列表
        $.ajax({
            url: ""+ajaxUrl+"/guangzhou/mon/device/list",
            type: "GET",
            dataType: "json",
            data: {},
            success: function (devices) {
                if (devices.code == "10000") {
                    if (devices.data !== null) {
                        for (var i = 0; i < devices.data.length; i++) {
                            var detectItem = '';
                            for (var j = 0; j < itemArr.length; j++) {
                                if (devices.data[i].detect_item == itemArr[j].code) {
                                    detectItem = itemArr[j].name
                                }
                            }
                            var gategoryStr = '';
                            for (var j = 0; j < arr.length; j++) {
                                if (devices.data[i].gategory == arr[j].code) {
                                    gategoryStr = arr[j].name
                                }
                            }

                            var OHtml = '';
                            OHtml += '<tr class="listTr" deviceIp="' + devices.data[i].device_ip + '">' +
                                '<td>' +
                                '<label for="check' + i + '"><input id="check' + i + '" type="checkbox" class="devListDel" name="' + devices.data[i].device_ip + '">' + devices.data[i].device_ip + '</label>' +
                                '</td>' +
                                '<td>' + detectItem + '</td>' +
                                '<td>' + devices.data[i].detect_id + '</td>' +
                                '<td>' + devices.data[i].location + '</td>' +
                                '<td>' + devices.data[i].area + '</td>' +
                                '<td>' + devices.data[i].alais + '</td>' +
                                '<td>' + gategoryStr + '</td>' +
                                '</tr>';
                            $(".tableList").append(OHtml);
                        }
                    }
                } else {
                    alert(devices.msg)
                }
            }
        });

        //************************************************************添加
        $('.add').unbind('click')
        $('.add').click(function () {
            backBtn()
            // 点击返回的时候不运行
            var back = $(this).attr('class')
            if (back.indexOf("back") != -1) {
                // 改变表头
                $('.center').addClass("addText")

                $('.list>.form-horizontal').empty();
                var lHtml = '<div class="form-group">' +
                    '<label for="deviceIp" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>设备IP：</label>' +
                    '<div class="col-sm-8 ">' +
                    '<input type="text" class="form-control" id="deviceIp" placeholder="设备IP">' +
                    '</div>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="deviceType" class="col-sm-2 control-label"><span class="red">*</span>监测类型：</label>' +
                    '<div class="col-sm-8">' +
                    '<select id="deviceType" class="form-control"></select>' +
                    '</div>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="detectId" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>设备ID：</label>' +
                    '<div class="col-sm-8 ">' +
                    '<input type="text" class="form-control" id="detectId" placeholder="设备ID">' +
                    '</div>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="deviceLocation" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>位置：</label>' +
                    '<div class="col-sm-8 ">' +
                    '<input type="text" class="form-control" id="deviceLocation" placeholder="位置">' +
                    '</div>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="deviceArea" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>面积：</label>' +
                    '<div class="col-sm-8 ">' +
                    '<input type="text" class="form-control" id="deviceArea" placeholder="面积">' +
                    '</div>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="gategory" class="col-sm-2 control-label"><span class="red">*</span>设备类别：</label>' +
                    '<div class="col-sm-8">' +
                    '<select id="gategory" class="form-control"></select>' +
                    '</div>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="deviceAlias" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>别名：</label>' +
                    '<div class="col-sm-8 ">' +
                    '<input type="text" class="form-control" id="deviceAlias" placeholder="别名">' +
                    '</div>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<div class="col-sm-2 control-label"></div>' +
                    '<div class="col-sm-8">' +
                    '<button class="btn deviceAddBtn" type="button">确定</button>' +
                    '</div>' +
                    '</div>';
                $('.list>.form-horizontal').append(lHtml);

                // 监测类型
                for (var i = 0; i < itemArr.length; i++) {
                    var OHtml = '';
                    OHtml += '<option value="' + itemArr[i].code + '">' + itemArr[i].name + '</option>';
                    $("#deviceType").append(OHtml);
                }

                // 设备类别
                for (var i = 0; i < arr.length; i++) {
                    var OHtml = '';
                    OHtml += '<option value="' + arr[i].code + '">' + arr[i].name + '</option>';
                    $("#gategory").append(OHtml);
                }

                // 添加设备设施
                $('.deviceAddBtn').click(function () {
                    var deviceIp = $('#deviceIp').val();
                    var deviceType = $('#deviceType').val();
                    var detectId = $('#detectId').val();
                    var deviceLocation = $('#deviceLocation').val();
                    var deviceArea = $('#deviceArea').val();
                    var gategory = $('#gategory').val();
                    var deviceAlias = $('#deviceAlias').val();

                    // 检测值是否为空
                    var lookArr = [
                        { look: deviceIp, msg: '设备IP' },
                        { look: detectId, msg: '设备ID' },
                        { look: deviceLocation, msg: '位置' },
                        { look: deviceArea, msg: '面积' },
                        { look: deviceAlias, msg: '别名' },
                    ];
                    for (var i = 0; i < lookArr.length; i++) {
                        if (lookArr[i].look == '' || lookArr[i].look == null) {
                            alert(lookArr[i].msg + '不能为空！');
                            return false
                        }
                    }

                    $.ajax({
                        url: ""+ajaxUrl+"/guangzhou/mon/device",
                        type: "post",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify({
                            "device_ip": deviceIp,
                            "detect_item": deviceType,
                            "detect_id": detectId,
                            "detect_no": parseInt(detectId),
                            "location": deviceLocation,
                            "area": parseFloat(deviceArea),
                            "gategory": gategory,
                            "alais": deviceAlias
                        }),
                        success: function (data) {
                            if (data.code == "10000") {
                                alert('成功！');
                                $('.back').click()
                                $('.liDevice').click()
                            } else {
                                alert(data.msg)
                            }
                        }
                    })
                });
            }else { $('.center').removeClass('addText editText') }
        });

        //************************************************************删除
        $(".del").click(function () {
            var aa = '';
            $('.devListDel').each(function () {
                if (check($(this)[0]) == 1) {
                    aa += $(this).attr('name') + '，'
                }
            });
            if (aa == '') {
                alert('请选择需要删除的用户！')
            } else {
                var yes = confirm("确定删除：" + aa.substring(0, aa.length - 1) + "？");
                if (yes == true) {
                    $('.devListDel').each(function () {
                        if (check($(this)[0]) == 1) {
                            var deviceIp = $(this).parents('.listTr').attr('deviceIp')

                            $(this).parents("tr").remove();
                            $.ajax({
                                url: ""+ajaxUrl+"/guangzhou/mon/device",
                                type: "DELETE",
                                dataType: "json",
                                contentType: "application/json",
                                data: JSON.stringify({
                                    "device_ip": deviceIp // 设备IP
                                }),
                                success: function (udelres) {
                                    if (udelres.code == "10000") {

                                    } else {
                                        alert('删除失败')
                                        window.location.reload();
                                    }
                                }
                            })
                        }
                    })
                } else {
                    $('.devListDel').each(function () {
                        $(this)[0].checked = false
                    })
                }
            }
        })

        //************************************************************更新
        $('.reload').click(function () {
            $.ajax({
                url: ""+ajaxUrl+"/guangzhou/basedevice/reload",
                type: "post",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(),
                success: function (data) {
                    // console.log(data);
                    if (data.code == "10000") {
                        alert('更新成功！');
                    } else {
                        alert(data.msg)
                    }
                }
            })
        })

    });

    //*************************************************************************************第二项，设备设施状态评估参照
    $('.liDeviceRef').click(function () {
        // 表头
        var text = $(this).text()
        $('.center').text(text);

        $('.del').remove()
        $('.reload').remove()

        $('.left>ul').remove();
        var uHtml = '<ul class="nav devTypeUl">' +
            '<li class="dropdown">' +
            '<button class="btn" data-toggle="dropdown">筛选<b class="caret"></b>' +
            '</button>' +
            '<ul id="RefType" class="dropdown-menu"></ul>' +
            '</li>' +
            '</ul>';
        $(".left").append(uHtml);

        $(".tableList").empty();
        var tHtml = '<tr>' +
            '<th width="14%">设备类别</th>' +
            '<th width="14%">状态等级编号</th>' +
            '<th width="14%">阈值上限</th>' +
            '<th width="14%">阈值下限</th>' +
            '<th width="14%">状态等级</th>' +
            '<th width="14%">颜色ID</th>' +
            '<th width="16%">操作</th>' +
            '</tr>';
        $(".tableList").append(tHtml);


        for (var i = 0; i < arr.length; i++) {
            var OHtml = '';
            OHtml += '<li class="devTypeLi" value="' + arr[i].code + '">' + arr[i].name + '</li>';
            $("#RefType").append(OHtml);
        }
        $("#RefType").append('<li class="devTypeLi" value="">全部</li>');

        $(".devTypeLi").each(function () {
            $(this).click(function () {
                var type = $(this).attr('value');
                devStatusList(type)
            })
        });

        //************************************************************获取设备设施状态列表
        var type = '';
        devStatusList(type);

        // ************************************************************添加
        $('.add').unbind('click')
        $('.add').click(function () {
            backBtn()
            // 点击返回的时候不运行
            var back = $(this).attr('class')
            if (back.indexOf("back") != -1) {
                // 改变表头
                $('.center').addClass("addText")

                $('.list>.form-horizontal').empty();
                var lHtml = '<div class="form-group">' +
                    '<label for="devClass" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>设备设施类别：</label>' +
                    '<div class="col-sm-8">' +
                    '<select id="devClass" class="form-control"></select>' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="statusLevel" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>状态等级：</label>' +
                    '<div class="col-sm-8">' +
                    '<select id="statusLevel" class="form-control"></select>' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="thresholdUp" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>阈值上限：</label>' +
                    '<div class="col-sm-8 ">' +
                    '<input type="text" class="form-control" id="thresholdUp" placeholder="阈值上限">' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="thresholdDown" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>阈值下限：</label>' +
                    '<div class="col-sm-8 ">' +
                    '<input type="text" class="form-control" id="thresholdDown" placeholder="阈值下限">' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="colorID" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>颜色ID：</label>' +
                    '<div class="col-sm-8">' +
                    '<select id="colorID" class="form-control" disabled="disabled"></select>' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<div class="col-sm-2 control-label"></div>' +
                    '<div class="col-sm-8">' +
                    '<button class="btn devRefAddBtn" type="button">确定</button>' +
                    '</div>' +
                    '</div>';

                $('.list>.form-horizontal').append(lHtml);

                // 状态等级
                $("#newLevelDesc").empty()
                for (var i = 0; i < statusArr.length; i++) {
                    var OHtml = '';
                    OHtml += '<option value="' + statusArr[i].value + '">' + statusArr[i].text + '</option>';
                    $("#statusLevel").append(OHtml);
                    $("#newLevelDesc").append(OHtml);
                }

                // 设备设施类别
                $("#newDevClass").empty()
                for (var i = 0; i < arr.length; i++) {
                    var OHtml = '';
                    OHtml += '<option value="' + arr[i].code + '">' + arr[i].name + '</option>';
                    $("#devClass").append(OHtml);
                    $("#newDevClass").append(OHtml)
                }

                // 颜色ID
                $("#newColorID").empty()
                for (var i = 0; i < colorID.length; i++) {
                    var OHtml = '';
                    OHtml += '<option value="' + colorID[i].code + '">' + colorID[i].name + '</option>';
                    $("#colorID").append(OHtml);
                    $("#newColorID").append(OHtml);
                }

                // 状态等级和颜色ID联动
                $('#statusLevel').change(function () {
                    var a = $(this).val()
                    $("#colorID").val(a)
                })

                // 添加设备设施状态评估
                $('.devRefAddBtn').click(function () {
                    var deviceType = $('#devClass').val();
                    var statusLevel = $('#statusLevel').val();
                    var thresholdUp = $('#thresholdUp').val();
                    var thresholdDown = $('#thresholdDown').val();
                    var colorID = $('#colorID').val();

                    var levelDesc
                    for (var i = 0; i < statusArr.length; i++) {
                        var a = parseInt(statusLevel)
                        levelDesc = statusArr[a].text
                    }

                    // 检测值是否为空
                    var lookArr = [
                        { look: thresholdUp, msg: '阈值上限' },
                        { look: thresholdDown, msg: '阈值下限' }
                    ];
                    for (var i = 0; i < lookArr.length; i++) {
                        if (lookArr[i].look == '' || lookArr[i].look == null) {
                            alert(lookArr[i].msg + '不能为空');
                            return false
                        }
                    }

                    $.ajax({
                        url: ""+ajaxUrl+"/guangzhou/device/ref",
                        type: "post",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify({
                            "device_type": deviceType,
                            "status_level": parseInt(statusLevel),
                            "threshold_up": thresholdUp,
                            "threshold_down": thresholdDown,
                            "level_desc": levelDesc,
                            "color_id": parseInt(colorID),
                            "modify": 0
                        }),
                        success: function (data) {
                            // console.log(data);
                            if (data.code == "10000") {
                                alert('成功！');
                                $('.back').click()
                                $('.liDeviceRef').click()
                            } else {
                                alert(data.msg)
                            }
                        }
                    })
                });
            } else { $('.center').removeClass('addText editText') }
        });
    })

    //获取设备设施状态列表
    function devStatusList(type) {
        $('.listTr').remove();
        $.ajax({
            url: ""+ajaxUrl+"/guangzhou/device/ref/list",
            type: "get",
            dataType: "json",
            data: {
                type: type
            },
            success: function (devices) {
                // console.log(devices);
                if (devices.code == "10000") {
                    if (devices.data !== null) {
                        for (var i = 0; i < devices.data.length; i++) {
                            var deviceType = '';
                            for (var j = 0; j < arr.length; j++) {
                                if (devices.data[i].device_type == arr[j].code) {
                                    deviceType = arr[j].name
                                }
                            }
                            // 颜色ID
                            var colorIDStr = '';
                            for (var j = 0; j < colorID.length; j++) {
                                if (devices.data[i].color_id == colorID[j].code) {
                                    colorIDStr = colorID[j].name
                                }
                            }

                            var OHtml = '';
                            OHtml += '<tr class="listTr" deviceType="' + devices.data[i].device_type + '" statusLevel="' + devices.data[i].status_level + '" thresholdUp="' + devices.data[i].threshold_up + '" thresholdDown="' + devices.data[i].threshold_down + '" levelDesc="' + devices.data[i].level_desc + '" colorId="' + devices.data[i].color_id + '">' +
                                '<td>' + deviceType + '</td>' +
                                '<td>' + devices.data[i].status_level + '</td>' +
                                '<td>' + devices.data[i].threshold_up + '</td>' +
                                '<td>' + devices.data[i].threshold_down + '</td>' +
                                '<td>' + devices.data[i].level_desc + '</td>' +
                                '<td>' + colorIDStr + '</td>' +
                                '<td><button class="btn devDel">删除</button><button class="btn devmodify">修改</button></td>' +
                                '</tr>';

                            $(".tableList").append(OHtml);
                        }
                    }
                } else {
                    alert(devices.msg)
                }
            }
        });
    }

    //**************************************************************删除
    $(".tableList").on('click', '.devDel', function () {
        var deviceType = $(this).parents('.listTr').attr('deviceType')
        var statusLevel = $(this).parents('.listTr').attr('statusLevel');

        $.ajax({
            url: ""+ajaxUrl+"/guangzhou/device/ref",
            type: "DELETE",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "device_type": deviceType,
                "status_level": parseInt(statusLevel)
            }),
            success: function (udelres) {
                if (udelres.code == "10000") {
                    $('.liDeviceRef').click()
                    alert('删除成功')
                } else {
                    alert('删除失败')
                }
            }
        })
    })
    //*********************************************修改设备设施状态评估参
    $(".tableList").on('click', '.devmodify', function () {
        backBtn()
        // 改变表头
        $('.center').addClass('editText')

        var devicetype = $(this).parents('.listTr').attr('devicetype');
        var statuslevel = $(this).parents('.listTr').attr('statuslevel');
        var thresholdup = $(this).parents('.listTr').attr('thresholdup');
        var thresholddown = $(this).parents('.listTr').attr('thresholddown');
        var leveldesc = $(this).parents('.listTr').attr('leveldesc');
        var colorid = $(this).parents('.listTr').attr('colorid');


        $('.list>.form-horizontal').empty();
        var lHtml = '<div class="form-group">' +
            '<label for="devClass" class="col-sm-2 control-label">' +
            '<span class="red">*</span>设备设施类别：</label>' +
            '<div class="col-sm-8">' +
            '<select id="devClass" class="form-control" disabled></select>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="statusLevel" class="col-sm-2 control-label">' +
            '<span class="red">*</span>状态等级：</label>' +
            '<div class="col-sm-8">' +
            '<select id="statusLevel" class="form-control"></select>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="statusLevelNum" class="col-sm-2 control-label">' +
            '<span class="red">*</span>状态等级编号：</label>' +
            '<div class="col-sm-8">' +
            '<select id="statusLevelNum" class="form-control" disabled="disabled"></select>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="thresholdUp" class="col-sm-2 control-label">' +
            '<span class="red">*</span>阈值上限：</label>' +
            '<div class="col-sm-8 ">' +
            '<input type="text" class="form-control" id="thresholdUp" placeholder="阈值上限" value=' + thresholdup + '>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="thresholdDown" class="col-sm-2 control-label">' +
            '<span class="red">*</span>阈值下限：</label>' +
            '<div class="col-sm-8 ">' +
            '<input type="text" class="form-control" id="thresholdDown" placeholder="阈值下限" value=' + thresholddown + '>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="colorID" class="col-sm-2 control-label">' +
            '<span class="red">*</span>颜色ID：</label>' +
            '<div class="col-sm-8">' +
            '<select id="colorID" class="form-control" disabled="disabled"></select>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<div class="col-sm-2 control-label"></div>' +
            '<div class="col-sm-8">' +
            '<button class="btn devmodBtn" type="button">确定</button>' +
            '</div>' +
            '</div>';

        $('.list>.form-horizontal').append(lHtml);




        // 设备设施类别
        $("#newDevClass").empty()
        for (var i = 0; i < arr.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + arr[i].code + '">' + arr[i].name + '</option>';
            $("#devClass").append(OHtml);
            $("#newDevClass").append(OHtml)
            $("#devClass").val(devicetype)
        }

        $("#newLevelDesc").empty()
        $("#statusLevelNum").empty()
        for (var i = 0; i < statusArr.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + statusArr[i].value + '">' + statusArr[i].text + '</option>';
            var SHtml = '';
            SHtml += '<option value="' + statusArr[i].value + '">' + statusArr[i].value + '</option>';
            $("#statusLevel").append(OHtml);
            $("#statusLevelNum").append(SHtml);
            $("#newLevelDesc").append(OHtml);

            var text = leveldesc;
            $("#statusLevel").find("option").each(function () {
                var this_obj = $(this);
                var this_text = this_obj.html();
                if (text == this_text) {
                    $("#statusLevel").val(this_obj.val())
                }
            })
        }
        $("#statusLevelNum").val(statuslevel)

        $("#newColorID").empty()
        for (var i = 0; i < colorID.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + colorID[i].code + '">' + colorID[i].name + '</option>';
            $("#colorID").append(OHtml);
            $("#newColorID").append(OHtml);
        }
        $("#colorID").val(colorid)

        // 状态等级、状态等级编号、颜色ID,联动
        $('#statusLevel').change(function () {
            var a = $(this).val()
            $("#colorID").val(a)
            $("#statusLevelNum").val(a)
        })


        $(".devmodBtn").on('click', function () {
            var deviceType = $("#devClass").val();
            var thresholdUp = $("#thresholdUp").val();
            var thresholdDown = $("#thresholdDown").val();
            var colorID = $("#colorID").val();
            var statusLevelNum = $("#statusLevelNum").val();

            var levelDesc;
            var levelDescv = $("#statusLevel").val();
            $("#statusLevel").find("option").each(function () {
                var levelDescs = $(this).attr("value")
                if (levelDescs == levelDescv) {
                    levelDesc = $(this).html()
                }
            })

            // 检测值是否为空
            var lookArr = [
                { look: thresholdUp, msg: '阈值上限' },
                { look: thresholdDown, msg: '阈值下限' }
            ];
            for (var i = 0; i < lookArr.length; i++) {
                if (lookArr[i].look == '' || lookArr[i].look == null) {
                    alert(lookArr[i].msg + '不能为空');
                    return false
                }
            }

            $.ajax({
                url: ""+ajaxUrl+"/guangzhou/device/ref",
                type: "post",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    "device_type": deviceType,
                    "status_level": parseInt(statuslevel),
                    "new_status_level": parseInt(statusLevelNum), // 此字段修改时使用，表示新的状态值
                    "threshold_up": thresholdUp,
                    "threshold_down": thresholdDown,
                    "level_desc": levelDesc,
                    "color_id": parseInt(colorID),
                    "modify": 1
                }),
                success: function (data) {
                    // console.log(data);
                    if (data.code == "10000") {
                        alert('修改成功！');
                        // $('.close').click()
                        $('.back').click()
                        $('.liDeviceRef').click()
                    } else {
                        alert(data.msg)
                    }
                }
            })
        })
    })







    // *****************************************************************************第三项，报警阈值表
    $('.liAlarm').click(function () {
        // 表头
        var text = $(this).text()
        $('.center').text(text);

        $('.left>ul').remove()

        $('.del').remove()
        $('.reload').remove()

        $(".tableList").empty();
        var tHtml = '<tr>' +
            '<th width="15%">报警等级编号</th>' +
            '<th width="15%">颜色</th>' +
            '<th width="15%">事件名称</th>' +
            '<th width="15%">报警启动条件</th>' +
            '<th width="20%">预警位置</th>' +
            '<th width="20%">操作</th>' +
            '</tr>';
        $(".tableList").append(tHtml);
        // **********************************************************获取列表
        $.ajax({
            url: ""+ajaxUrl+"/guangzhou/alarm/infos",
            type: "GET",
            dataType: "json",
            data: {},
            success: function (content) {
                if (content.code == "10000") {
                    if (content.data !== null) {
                        for (var i = 0; i < content.data.length; i++) {
                            var OHtml = '';
                            OHtml += '<tr class="listTr" detectId="' + content.data[i].detect_id + '" alarmLevel="' + content.data[i].alarm_level + '" alarmColor="' + content.data[i].alarm_color + '" pos="' + content.data[i].pos + '" alais="' + content.data[i].alais + '" event_name="' + content.data[i].event_name + '">' +
                                '<td>' + content.data[i].alarm_level + '</td>' +
                                '<td>' + content.data[i].alarm_color + '</td>' +
                                '<td>' + content.data[i].event_name + '</td>' +
                                '<td>' + content.data[i].alais + '</td>' +
                                '<td>' + content.data[i].pos + '</td>' +
                                '<td><button class="btn infoDel">删除</button><button class="btn infomodify">修改</button></td>' +
                                '</tr>';
                            $(".tableList").append(OHtml);
                        }
                    }
                } else {
                    alert(content.msg)
                }
            }
        });


        // **********************************************************添加
        $('.add').unbind('click')
        $('.add').click(function () {
            backBtn()

            // 点击返回的时候不运行
            var back = $(this).attr('class')
            if (back.indexOf("back") != -1) {
                // 改变表头
                $('.center').addClass('addText')

                $('.list>.form-horizontal').empty();
                var lHtml =
                    '<div class="form-group">' +
                    '<label for="detectAlais" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>报警启动条件：</label>' +
                    '<div class="col-sm-8 ">' +
                    '<input type="text" class="form-control" id="detectAlais" placeholder="报警启动条件" disabled="disabled">' +
                    '<button type="button" class="addAlias btn" style="margin-left:5px">添加</button>' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group aliasBox">' +

                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="event_name" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>事件名称：</label>' +
                    '<div class="col-sm-8 ">' +
                    '<input type="text" class="form-control" id="event_name" placeholder="事件名称" >' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="alarmColor" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>报警等级：</label>' +
                    '<div class="col-sm-8">' +
                    '<select id="alarmColor" class="form-control"></select>' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="pos" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>预警位置：</label>' +
                    '<div class="col-sm-8">' +
                    '<select id="pos" class="form-control"></select>' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<div class="col-sm-2 control-label"></div>' +
                    '<div class="col-sm-8">' +
                    '<button class="btn alarmAddBtn" type="button">确定</button>' +
                    '</div>' +
                    '</div>';

                $('.list>.form-horizontal').append(lHtml);

                // ***************报警启动条件:添加和删除
                var idNum = 0
                $('.addAlias').click(function () {
                    idNum++
                    var html = ''
                    html += '<label for="" class="col-sm-2 control-label del' + idNum + '">' +
                        '</label>' +
                        '<div class="col-sm-8 del' + idNum + ' divVlue">' +
                        '<select id="alias' + idNum + '" class="form-control selectS"><option value="" selected="selected" disabled="disabled">别名</option></select>' +
                        '<select id="alarm' + idNum + '" class="form-control selectS"><option value="" selected="selected" disabled="disabled">报警等级</option></select>' +
                        '<button type="button" class="delAlias delBtn' + idNum + '">×</button>' +
                        '</div>'
                    $('.aliasBox').append(html);

                    $('#detectAlais').val(alaisStr($('.divVlue')))

                    // 删除此行下拉框
                    $('.aliasBox .col-sm-8 .delAlias').each(function () {
                        $(this).unbind('click')
                        $(this).click(function () {
                            // 提取class下标
                            var reg = /\d+/g;
                            var a = $(this).attr('class').split(' ')[1]
                            var b = parseInt(a.match(reg)[0])
                            $('.del' + b + '').remove()
                            $('#detectAlais').val(alaisStr($('.divVlue')))
                        })
                    })

                    // 别名下拉框
                    for (var i = 0; i < AlldetectAlais.length; i++) {
                        var OHtml = '';
                        OHtml += '<option value="' + AlldetectAlais[i] + '">' + AlldetectAlais[i] + '</option>';
                        $('#alias' + idNum + '').append(OHtml);
                    }
                    // 报警等级下拉框
                    for (var j = 0; j < statusArr.length; j++) {
                        var OHtml = '';
                        OHtml += '<option value="' + statusArr[j].value + '">' + statusArr[j].value + '</option>';
                        $('#alarm' + idNum + '').append(OHtml);
                    }

                    // 下拉框和input联动
                    $('.aliasBox .col-sm-8 select').each(function () {
                        $(this).unbind('change')
                        $(this).change(function () {
                            $('#detectAlais').val(alaisStr($('.divVlue')))
                        })
                    })

                })

                // 预警位置pos
                $("#pos").empty()
                for (var i = 0; i < posArr.length; i++) {
                    var OHtml = '';
                    OHtml += '<option value="' + posArr[i].text + '">' + posArr[i].text + '</option>';
                    $("#pos").append(OHtml);
                }

                // 颜色
                $("#newAlarmColor").empty()
                for (var i = 0; i < statusArr.length; i++) {
                    var OHtml = '';
                    OHtml += '<option value="' + statusArr[i].value + '">' + statusArr[i].value + '</option>';
                    $("#alarmColor").append(OHtml);
                    $("#newAlarmColor").append(OHtml);
                }

                // 获取设备设施ID值
                $("#newDetectId").empty()
                for (var i = 0; i < AlldetectId.length; i++) {
                    var OHtml = '';
                    OHtml += '<option value="' + AlldetectId[i] + '">' + AlldetectId[i] + '</option>';
                    $("#detectId").append(OHtml);
                    $("#newDetectId").append(OHtml);
                }

                // 获取设备设施别名
                $("#newDetectId").empty()
                for (var i = 0; i < AlldetectAlais.length; i++) {
                    var OHtml = '';
                    OHtml += '<option value="' + AlldetectAlais[i] + '">' + AlldetectAlais[i] + '</option>';
                    $("#detectAlais").append(OHtml);
                    $("#newDetectId").append(OHtml);
                }

                $('.alarmAddBtn').click(function () {
                    var detectAlais = $("#detectAlais").val();
                    var alarmLevel = $('#alarmColor').val();
                    var pos = $('#pos').val();
                    var event_name = $('#event_name').val();
                    var levelDesc = $('#detectId').val() + '+' + alarmLevel;

                    var alarmColor
                    for (var i = 0; i < statusArr.length; i++) {
                        var a = parseInt(alarmLevel)
                        alarmColor = statusArr[a].colorH
                    }



                    // 检测值是否为空
                    if (detectAlais.indexOf("别名") != -1 || detectAlais.indexOf("报警等级") != -1) {
                        alert('别名或报警等级不能为空')
                        return false
                    }

                    var lookArr = [
                        { look: detectAlais, msg: '报警启动条件' },
                        { look: event_name, msg: '事件名称' }

                    ];
                    for (var i = 0; i < lookArr.length; i++) {
                        if (lookArr[i].look == '' || lookArr[i].look == null) {
                            alert(lookArr[i].msg + '不能为空');
                            return false
                        }
                    }

                    $.ajax({
                        url: ""+ajaxUrl+"/guangzhou/alarm/info",
                        type: "post",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify({
                            "alais": detectAlais,
                            "event_name": event_name,
                            "pos": pos,
                            "alarm_level": parseInt(alarmLevel),
                            "alarm_color": alarmColor,
                        }),
                        success: function (data) {
                            // console.log(data)
                            if (data.code == "10000") {
                                alert('成功！');
                                $('.back').click()
                                $('.liAlarm').click()
                            } else {
                                alert(data.msg)
                            }
                        }
                    })
                })
            }else { $('.center').removeClass('addText editText') }
        })
    })

    //修改报警阈值
    $(".tableList").on('click', '.infomodify', function () {

        backBtn()

        // 改变表头
        $('.center').addClass('editText')

        var alarmcolor = $(this).parents('.listTr').attr('alarmcolor');
        var alarmlevel = $(this).parents('.listTr').attr('alarmlevel');
        var pos = $(this).parents('.listTr').attr('pos');
        var alais = $(this).parents('.listTr').attr('alais');
        var event_name = $(this).parents('.listTr').attr('event_name');
        $('.list>.form-horizontal').empty();
        var lHtml =

            '<div class="form-group">' +
            '<label for="detectAlais" class="col-sm-2 control-label">' +
            '<span class="red">*</span>旧报警启动条件：</label>' +
            '<div class="col-sm-8 ">' +
            '<input type="text" class="form-control" id="detectAlais" value="' + alais + '" disabled="disabled">' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="newdetectAlais" class="col-sm-2 control-label">' +
            '<span class="red">*</span>新报警启动条件：</label>' +
            '<div class="col-sm-8 ">' +
            '<input type="text" class="form-control" id="newdetectAlais" placeholder="新报警启动条件" disabled="disabled">' +
            '<button type="button" class="addAlias btn" style="margin-left:5px">添加</button>' +
            '</div>' +
            '</div>' +

            '<div class="form-group aliasBox">' +

            '</div>' +

            '<div class="form-group">' +
            '<label for="event_name" class="col-sm-2 control-label">' +
            '<span class="red">*</span>旧事件名称：</label>' +
            '<div class="col-sm-8 ">' +
            '<input type="text" class="form-control" id="event_name" disabled="disabled" value="' + event_name + '" >' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="new_event_name" class="col-sm-2 control-label">' +
            '<span class="red">*</span>新事件名称：</label>' +
            '<div class="col-sm-8 ">' +
            '<input type="text" class="form-control" id="new_event_name" placeholder="新事件名称" >' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="pos" class="col-sm-2 control-label">' +
            '<span class="red">*</span>旧预警位置：</label>' +
            '<div class="col-sm-8">' +
            '<select id="pos" class="form-control" disabled="disabled"></select>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="newpos" class="col-sm-2 control-label">' +
            '<span class="red">*</span>新预警位置：</label>' +
            '<div class="col-sm-8">' +
            '<select id="newpos" class="form-control"></select>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="alarmColor" class="col-sm-2 control-label">' +
            '<span class="red">*</span>旧报警等级：</label>' +
            '<div class="col-sm-8">' +
            '<select id="alarmColor" class="form-control" disabled="disabled"></select>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="newalarmColor" class="col-sm-2 control-label">' +
            '<span class="red">*</span>新报警等级：</label>' +
            '<div class="col-sm-8">' +
            '<select id="newalarmColor" class="form-control"></select>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<div class="col-sm-2 control-label"></div>' +
            '<div class="col-sm-8">' +
            '<button class="btn infoModBtn" type="button">确定</button>' +
            '</div>' +
            '</div>';

        $('.list>.form-horizontal').append(lHtml);

        var idNum = 0
        $('.addAlias').click(function () {
            idNum++
            var html = ''
            html += '<label for="" class="col-sm-2 control-label del' + idNum + '">' +
                '</label>' +
                '<div class="col-sm-8 del' + idNum + ' divVlue">' +
                '<select id="alias' + idNum + '" class="form-control selectS"><option value="" selected="selected" disabled="disabled">别名</option></select>' +
                '<select id="alarm' + idNum + '" class="form-control selectS"><option value="" selected="selected" disabled="disabled">报警等级</option></select>' +
                '<button type="button" class="delAlias delBtn' + idNum + '">×</button>' +
                '</div>'
            $('.aliasBox').append(html);

            $('#newdetectAlais').val(alaisStr($('.divVlue')))

            // 删除此行下拉框
            $('.aliasBox .col-sm-8 .delAlias').each(function () {
                $(this).unbind('click')
                $(this).click(function () {
                    // 提取class下标
                    var reg = /\d+/g;
                    var a = $(this).attr('class').split(' ')[1]
                    var b = parseInt(a.match(reg)[0])
                    $('.del' + b + '').remove()
                    $('#newdetectAlais').val(alaisStr($('.divVlue')))
                })
            })

            // 别名下拉框
            for (var i = 0; i < AlldetectAlais.length; i++) {
                var OHtml = '';
                OHtml += '<option value="' + AlldetectAlais[i] + '">' + AlldetectAlais[i] + '</option>';
                $('#alias' + idNum + '').append(OHtml);
            }
            // 报警等级下拉框
            for (var j = 0; j < statusArr.length; j++) {
                var OHtml = '';
                OHtml += '<option value="' + statusArr[j].value + '">' + statusArr[j].value + '</option>';
                $('#alarm' + idNum + '').append(OHtml);
            }

            // 下拉框和input联动
            $('.aliasBox .col-sm-8 select').each(function () {
                $(this).unbind('change')
                $(this).change(function () {
                    $('#newdetectAlais').val(alaisStr($('.divVlue')))
                })
            })

        })

        // 旧预警位置
        $("#pos").empty()
        $("#newpos").empty()
        for (var i = 0; i < posArr.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + posArr[i].text + '">' + posArr[i].text + '</option>';
            $("#pos").append(OHtml);
            $("#newpos").append(OHtml);
        }
        $("#pos").val(pos)

        // // 颜色
        $("#newalarmColor").empty()
        for (var i = 0; i < statusArr.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + statusArr[i].value + '">' + statusArr[i].value + '</option>';
            $("#newalarmColor").append(OHtml);
        }

        $("#newDetectId").empty()
        for (var i = 0; i < AlldetectId.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + AlldetectId[i] + '">' + AlldetectId[i] + '</option>';
            $("#detectId").append(OHtml);
            $("#newDetectId").append(OHtml);
            // $("#detectId").val(detectid)
        }

        // 获取设备设施别名
        $("#newDetectId").empty()
        for (var i = 0; i < AlldetectAlais.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + AlldetectAlais[i] + '">' + AlldetectAlais[i] + '</option>';
            $("#detectAlais").append(OHtml);
            $("#newDetectId").append(OHtml);
        }


        // 颜色
        for (var i = 0; i < statusArr.length; i++) {
            var OHtml = '';
            OHtml += '<option value="' + statusArr[i].value + '">' + statusArr[i].value + '</option>';
            $("#alarmColor").append(OHtml);
            $("#alarmColor").val(alarmlevel)
        }


        $(".infoModBtn").on('click', function () {
            var newdetectAlais = $('#newdetectAlais').val();
            var newpos = $('#newpos').val();
            var newalarmColor = $('#newalarmColor').val();
            var new_event_name = $('#new_event_name').val();

            var alarmColor
            for (var i = 0; i < statusArr.length; i++) {
                var a = parseInt(newalarmColor)
                alarmColor = statusArr[a].colorH
            }

            // 检测值是否为空
            if (newdetectAlais.indexOf("别名") != -1 || newdetectAlais.indexOf("报警等级") != -1) {
                alert('别名或报警等级不能为空')
                return false
            }

            var lookArr = [
                { look: newdetectAlais, msg: '新报警启动条件' },
                { look: new_event_name, msg: '新事件名称' }

            ];
            for (var i = 0; i < lookArr.length; i++) {
                if (lookArr[i].look == '' || lookArr[i].look == null) {
                    alert(lookArr[i].msg + '不能为空');
                    return false
                }
            }

            $.ajax({
                url: ""+ajaxUrl+"/guangzhou/alarm/info/modify",
                type: "post",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    "alias": alais,// 旧别名
                    "new_alias": newdetectAlais, // 新别名
                    "pos": pos, //预警位置
                    "new_pos": newpos, //预警位置 	
                    "alarm_level": parseInt(alarmlevel),
                    "new_alarm_level": parseInt(newalarmColor),
                    "event_name": event_name,
                    "new_event_name": new_event_name,
                    "alarm_color": alarmColor,
                }),
                success: function (data) {
                    // console.log(data)
                    if (data.code == "10000") {
                        alert('修改成功！');
                        $('.back').click()
                        $('.liAlarm').click()
                    } else {
                        alert(data.msg)
                    }
                }
            })
        })
    })

    // **********************************************************删除
    $(".tableList").on('click', '.infoDel', function () {
        var alarmLevel = $(this).parents('.listTr').attr('alarmLevel');
        var alarmColor = $(this).parents('.listTr').attr('alarmColor');
        var alais = $(this).parents('.listTr').attr('alais');
        var pos = $(this).parents('.listTr').attr('pos');
        var event_name = $(this).parents('.listTr').attr('event_name');

        $.ajax({
            url: ""+ajaxUrl+"/guangzhou/alarm/info",
            type: "DELETE",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "alais": alais,
                "event_name": event_name,
                "pos": pos,
                "alarm_level": parseInt(alarmLevel),
            }),
            success: function (udelres) {
                if (udelres.code == "10000") {
                    alert('删除成功')
                    $('.liAlarm').click()
                } else {
                    alert('删除失败')
                }
            }
        })
    })




    //***********************************************************************第四部分，外部事件列表
    $('.external').click(function () {
        // 表头
        var text = $(this).text()
        $('.center').text(text);

        $('.left>ul').remove()

        $('.del').remove()
        $('.reload').remove()

        $(".tableList").empty();
        var tHtml = '<tr>' +
            '<th width="7%">事件ID</th>' +
            '<th width="15%">起始时间</th>' +
            '<th width="15%">结束时间</th>' +
            '<th width="7%">事件类别</th>' +
            '<th width="7%">事件级别</th>' +
            '<th width="13%">事件名称</th>' +
            '<th width="7%">线路编号</th>' +
            '<th width="7%">车站编号</th>' +
            '<th width="15%">操作</th>' +
            '</tr>';
        $(".tableList").append(tHtml);
        //获取列表
        $.ajax({
            url: ""+ajaxUrl+"/guangzhou/exten/events",
            type: "GET",
            dataType: "json",
            async: false,
            data: {},
            success: function (exten) {
                // console.log(exten)
                if (exten.code == "10000") {
                    if (exten.data !== null) {
                        for (var i = 0; i < exten.data.length; i++) {
                            var OHtml = '';
                            OHtml += '<tr class="listTr" extenid="' + exten.data[i].event_id + '" starttime="' + exten.data[i].start_time + '" endtime="' + exten.data[i].end_time + '" eventtype="' + exten.data[i].event_type + '" eventlevel="' + exten.data[i].event_level + '" linenum="' + exten.data[i].line_num + '" stationnum="' + exten.data[i].station_num + '" eventName="' + exten.data[i].event_name + '">' +
                                '<td>' + exten.data[i].event_id + '</td>' +
                                '<td>' + exten.data[i].start_time + '</td>' +
                                '<td>' + exten.data[i].end_time + '</td>' +
                                '<td>' + exten.data[i].event_type + '</td>' +
                                '<td>' + exten.data[i].event_level + '</td>' +
                                '<td>' + exten.data[i].event_name + '</td>' +
                                '<td>' + exten.data[i].line_num + '</td>' +
                                '<td>' + exten.data[i].station_num + '</td>' +
                                '<td><button class="btn extenDel">删除</button><button class="btn extenMod">修改</button></td>' +
                                '</tr>';
                            $(".tableList").append(OHtml);
                        }
                    }
                } else {
                    alert(exten.msg)
                }
            }
        });
        //添加外部事件

        $('.add').unbind('click')
        $('.add').click(function () {
            backBtn()

            // 点击返回的时候不运行
            var back = $(this).attr('class')
            if (back.indexOf("back") != -1) {
                // 改变表头
                $('.center').addClass('addText')

                $('.list>.form-horizontal').empty();
                var lHtml = '<div class="form-group">' +
                    '<label for="extenId" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>事件ID：</label>' +
                    '<div class="col-sm-8">' +
                    '<select id="extentId" disabled class="form-control"></select>' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="extenstart" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>起始时间：</label>' +
                    '<div class="col-sm-8 input-daterange">' +
                    '<input type="text" class="start" id="extenstart" class="form-control" placeholder="开始时间">' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="extenend" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>结束时间：</label>' +
                    '<div class="col-sm-8 input-daterange">' +
                    '<input type="text" class="end" id="extenend" class="form-control" placeholder="结束时间">' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="extentype" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>事件类别：</label>' +
                    '<div class="col-sm-8">' +
                    '<input type="text" id="extentype" class="form-control" placeholder="事件类别">' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="extentlevel" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>事件级别：</label>' +
                    '<div class="col-sm-8">' +
                    '<input type="text" id="extentlevel" class="form-control" placeholder="事件级别">' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="extentlevelName" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>事件名称：</label>' +
                    '<div class="col-sm-8">' +
                    '<input type="text" id="extentlevelName" class="form-control" placeholder="事件名称">' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="extenline" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>线路编号：</label>' +
                    '<div class="col-sm-8">' +
                    '<input type="text" id="linenum" class="form-control" placeholder="线路编号">' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<label for="extenstation" class="col-sm-2 control-label">' +
                    '<span class="red">*</span>车站编号：</label>' +
                    '<div class="col-sm-8">' +
                    '<input type="text" id="stationnum" class="form-control" placeholder="车站编号">' +
                    '</div>' +
                    '</div>' +

                    '<div class="form-group">' +
                    '<div class="col-sm-2 control-label"></div>' +
                    '<div class="col-sm-8">' +
                    '<button class="btn extenAddBtn" type="button">确定</button>' +
                    '</div>' +
                    '</div>';

                $('.list>.form-horizontal').append(lHtml);
                //时间插件
                initDateTimePicker()

                //设备ID
                var extenid = $(".tableList tr:last").find("td:first").text();
                // console.log(extenid)
                if ($(".tableList").find("tr").length == 1) {
                    var html1 = '';
                    html1 += '<option>' + 1 + '</option>';
                    $("#extentId").append(html1);
                } else {
                    var html1 = '';
                    html1 += '<option>' + (parseInt(extenid) + 1) + '</option>';
                    $("#extentId").append(html1);
                }

                //添加外部事件
                $('.extenAddBtn').click(function () {
                    var event_id = $('#extentId').val();
                    var start_time = $('#extenstart').val();
                    var end_time = $('#extenend').val();
                    var event_type = $('#extentype').val();
                    var event_level = $('#extentlevel').val();
                    var line_num = $('#linenum').val();
                    var station_num = $('#stationnum').val();
                    var extentlevelName = $('#extentlevelName').val();

                    // 检测值是否为空
                    var lookArr = [
                        { look: start_time, msg: '开始时间' },
                        { look: end_time, msg: '结束时间' },
                        { look: event_type, msg: '事件类别' },
                        { look: event_level, msg: '事件级别' },
                        { look: extentlevelName, msg: '事件名称' },
                        { look: line_num, msg: '线路编号' },
                        { look: station_num, msg: '车站编号' },

                    ];
                    for (var i = 0; i < lookArr.length; i++) {
                        if (lookArr[i].look == '' || lookArr[i].look == null) {
                            alert(lookArr[i].msg + '不能为空');
                            return false
                        }
                    }

                    $.ajax({
                        url: ""+ajaxUrl+"/guangzhou/exten/event",
                        type: "post",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify({
                            "event_id": parseInt(event_id),         // 事件ID
                            "start_time": start_time,    // 起时间
                            "end_time": end_time, // 结束时间
                            "event_type": event_type,   // 事件类别,之前是数字类型
                            "event_level": event_level, // 事件级别,之前是数字类型
                            "event_name": extentlevelName,  // 事件名称
                            "line_num": parseInt(line_num), // 涉及的线路编号
                            "station_num": parseInt(station_num), // 涉及的车站编号
                            "modify": 0
                        }),
                        success: function (data) {
                            // console.log(data)
                            if (data.code == "10000") {
                                alert('添加成功！');
                                $('.back').click()
                                $('.external').click()
                            } else {
                                alert(data.msg)
                            }
                        }
                    })
                })
            }else { $('.center').removeClass('addText editText') }
        })
    });
    //修改外部事件
    $(".tableList").on('click', '.extenMod', function () {

        backBtn()
        // 改变表头
        $('.center').addClass('editText')

        var evtenid = $(this).parents('.listTr').attr('extenid');
        var starttime = $(this).parents('.listTr').attr('starttime');
        var endtime = $(this).parents('.listTr').attr('endtime');
        var eventtype = $(this).parents('.listTr').attr('eventtype');
        var eventlevel = $(this).parents('.listTr').attr('eventlevel');
        var eventName = $(this).parents('.listTr').attr('eventName');
        var linenum = $(this).parents('.listTr').attr('linenum');
        var stationnum = $(this).parents('.listTr').attr('stationnum');

        $('.list>.form-horizontal').empty();
        var lHtml = '<div class="form-group">' +
            '<label for="extenId" class="col-sm-2 control-label">' +
            '<span class="red">*</span>事件ID：</label>' +
            '<div class="col-sm-8">' +
            '<select id="extentId" disabled class="form-control">' +
            '<option selected>' + evtenid + '</option>' +
            '</select>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="extenstart" class="col-sm-2 control-label">' +
            '<span class="red">*</span>起始时间：</label>' +
            '<div class="col-sm-8 input-daterange">' +
            '<input type="text" class="start" id="extenstart" class="form-control" value="' + starttime + '">' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="extenend" class="col-sm-2 control-label">' +
            '<span class="red">*</span>结束时间：</label>' +
            '<div class="col-sm-8 input-daterange">' +
            '<input type="text" class="end" id="extenend" class="form-control" value="' + endtime + '">' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="extentype" class="col-sm-2 control-label">' +
            '<span class="red">*</span>事件类别：</label>' +
            '<div class="col-sm-8">' +
            '<input type="text" id="extentype" class="form-control" value=' + eventtype + '>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="extentlevel" class="col-sm-2 control-label">' +
            '<span class="red">*</span>事件级别：</label>' +
            '<div class="col-sm-8">' +
            '<input type="text" id="extentlevel" class="form-control" value=' + eventlevel + '>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="extentlevelName" class="col-sm-2 control-label">' +
            '<span class="red">*</span>事件名称：</label>' +
            '<div class="col-sm-8">' +
            '<input type="text" id="extentlevelName" class="form-control" value=' + eventName + '>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="extenline" class="col-sm-2 control-label">' +
            '<span class="red">*</span>线路编号：</label>' +
            '<div class="col-sm-8">' +
            '<input type="text" id="linenum" class="form-control" value=' + linenum + '>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<label for="extenstation" class="col-sm-2 control-label">' +
            '<span class="red">*</span>车站编号：</label>' +
            '<div class="col-sm-8">' +
            '<input type="text" id="stationnum" class="form-control" value=' + stationnum + '>' +
            '</div>' +
            '</div>' +

            '<div class="form-group">' +
            '<div class="col-sm-2 control-label"></div>' +
            '<div class="col-sm-8">' +
            '<button class="btn eventmodBtn" type="button">确定</button>' +
            '</div>' +
            '</div>';

        $('.list>.form-horizontal').append(lHtml);
        // 时间插件
        initDateTimePicker()

        $(".eventmodBtn").on('click', function () {
            var event_id = $('#extentId').val();
            var start_time = $('#extenstart').val();
            var end_time = $('#extenend').val();
            var event_type = $('#extentype').val();
            var event_level = $('#extentlevel').val();
            var event_name = $('#extentlevelName').val();
            var line_num = $('#linenum').val();
            var station_num = $('#stationnum').val();

            // 检测值是否为空
            var lookArr = [
                { look: start_time, msg: '开始时间' },
                { look: end_time, msg: '结束时间' },
                { look: event_type, msg: '事件类别' },
                { look: event_level, msg: '事件级别' },
                { look: event_name, msg: '事件名称' },
                { look: line_num, msg: '线路编号' },
                { look: station_num, msg: '车站编号' },

            ];
            for (var i = 0; i < lookArr.length; i++) {
                if (lookArr[i].look == '' || lookArr[i].look == null) {
                    alert(lookArr[i].msg + '不能为空');
                    return false
                }
            }

            $.ajax({
                url: ""+ajaxUrl+"/guangzhou/exten/event",
                type: "post",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    "event_id": parseInt(event_id),         // 事件ID
                    "start_time": start_time,    // 起时间
                    "end_time": end_time, // 结束时间
                    "event_type": event_type,   // 事件类别
                    "event_level": event_level, // 事件级别
                    "event_name": event_name,  // 事件名称
                    "line_num": parseInt(line_num), // 涉及的线路编号
                    "station_num": parseInt(station_num), // 涉及的车站编号
                    "modify": 1
                }),
                success: function (data) {
                    // console.log(data)
                    if (data.code == "10000") {
                        alert('修改成功！');
                        $('.back').click()
                        $('.external').click()
                    } else {
                        alert(data.msg)
                    }
                }
            })
        })
    })
    // 删除外部事件
    $(".tableList").on('click', '.extenDel', function () {
        var extenid = $(this).parents('.listTr').attr('extenid')
        $.ajax({
            url: ""+ajaxUrl+"/guangzhou/exten/event ",
            type: "DELETE",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "event_id": parseInt(extenid)
            }),
            success: function (udelres) {
                if (udelres.code == "10000") {
                    alert('删除成功')
                    $('.external').click()
                } else {
                    alert('删除失败')
                }
            }
        })
    })

    function initDateTimePicker() {
        //时间插件
        var initDateTimePicker = function (opts) {
            var defaults = {
                type: "date",
                el: ".input-datepicker",
                range: ".input-daterange",
                start: ".start",
                end: ".end",
                minView: 0
            },
                opts = $.extend({}, defaults, opts);
            if (opts.minView === 0) {
                opts.format = opts.format || "yyyy-mm-dd hh:ii:00";
            } else if (opts.minView === 1) {
                opts.format = opts.format || "yyyy-mm-dd hh:00";
            } else if (opts.minView === 2) {
                opts.format = opts.format || "yyyy-mm-dd";
            }
            if (opts.type === "range") {
                var $range = $(opts.range),
                    $start = $range.find(opts.start),
                    $end = $range.find(opts.end);
                $start.datetimepicker(opts).on("changeDate", function (date) {
                    $end.datetimepicker("setStartDate", date.date);
                });
                $end.datetimepicker(opts).on("changeDate", function (date) {
                    $start.datetimepicker("setEndDate", date.date);
                });
            } else if (opts.type === "date") {
                return $(opts.el).datetimepicker(opts);
            }
        };

        initDateTimePicker({
            type: "range",
            minView: 0,
            minuteStep: 1
        });
    }


    // 组合字符串
    function alaisStr(ele) {
        var str = ''
        for (var e = 0; e < ele.length; e++) {
            var a = ele[e].childNodes[0].id
            var c = $('#' + a + '').val()
            var b = ele[e].childNodes[1].id
            var d = $('#' + b + '').val()
            c == null ? c = '别名' : c
            d == null ? d = '报警等级' : d
            str += c + '-' + d + ','
        }
        return str.substring(0, str.length - 1)
    }

    //移除点击事件
    function removeClick() {
        for (var i = 0; i < arguments.length; i++) {
            arguments[i].unbind("click")
        }
    }

    $(".site-tab ul li").on("click", function () {
        $(this).addClass("active").siblings().removeClass("active");
    })
    $('.liDevice').click()

});