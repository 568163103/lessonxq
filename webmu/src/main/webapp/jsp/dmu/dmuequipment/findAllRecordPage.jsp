<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/jsp/common/head/headPage.jsp" %>
    <title>${global_app_name}</title>
    <script type="text/javascript">
        function checkedItems() {
            var flag = false;
            $("#pageForm").find("[name=items]").each(function () {
                if (this.checked) {
                    flag = true;
                }
            });
            return flag;
        }

        function myrefresh() {
            window.location.href="${ctx}/dmu/dmuEquipment!findAllRecordPage.do";
        }

        setTimeout('myrefresh()', 60000); //指定1秒刷新一次

        function query() {
            choose();
            $("#pageForm").submit();
        }

        function alltimeQuery(status) {
            choose();
            var objS = $("#time").val();
            if (objS == "") {
                alert("请填写轮询间隔时间。");
                return false;
            }
            $("#status").val(status);
            $("#pageForm").submit();
        }

        function deleteAll() {
            if (!checkedItems()) {
                alert("请至少选择要删除服务器。");
                return false;
            }
            msg = "确认要删除所选服务器？";
            if (window.confirm(msg)) {
                $("#pageForm").attr("action", "${ctx}/dmu/dmuEquipment!delete.do");
            }
            $("#pageForm").submit();
        }

        function exportList() {
            var objS = document.getElementById("type");//获取配送员的信息
            var type = objS.options[objS.selectedIndex].value;//获取配送员下拉选定的数据
            if (type == "") {
                alert("请选择设备类型");
                return false;
            }
            $("#pageForm").attr("action", "${ctx}/dmu/dmuEquipment!exportList.do");
            $("#pageForm").submit();
            $("#pageForm").attr("action", "${ctx}/dmu/dmuEquipment!findAllRecordPage.do");
        }

        $(function () {
            selectSupp();
            choose();
        })

        function selectSupp() {
            var objS = document.getElementById("type");//获取配送员的信息
            var type = objS.options[objS.selectedIndex].value;//获取配送员下拉选定的数据
            if (type == '服务器') {
                $(".paraall").hide()
                $(".para-box").hide();
                $(".para-box1").show();
                $("#type2").val("1");
                $("#disk").hide();
                $("#PC").hide();
                $("#flash").hide();
                $("#server").show();
                $("#switch1").hide();
                $("#encoder").hide();
            } else if (type == '编码器') {
                $(".paraall").hide()
                $(".para-box").hide();
                $(".para-box2").show();
                $("#type2").val("2");
                $("#disk").hide();
                $("#PC").hide();
                $("#flash").hide();
                $("#server").hide();
                $("#switch1").hide();
                $("#encoder").show();
            } else if (type == 'IP摄像机') {
                $(".paraall").hide()
                $(".para-box").hide();
                $(".para-box3").show();
                $("#type2").val("2");
                $("#disk").hide();
                $("#PC").hide();
                $("#flash").hide();
                $("#server").hide();
                $("#switch1").hide();
                $("#encoder").show();
            } else if (type == '磁盘阵列') {
                $(".paraall").hide()
                $(".para-box").hide();
                $(".para-box4").show();
                $("#type2").val("3");
                $("#disk").show();
                $("#PC").hide();
                $("#flash").hide();
                $("#server").hide();
                $("#switch1").hide();
                $("#encoder").hide();
            } else if (type == '交换机') {
                $(".paraall").hide()
                $(".para-box").hide();
                $(".para-box5").show();
                $("#type2").val("4");
                $("#disk").hide();
                $("#PC").hide();
                $("#flash").hide();
                $("#server").hide();
                $("#switch1").show();
                $("#encoder").hide();
            } else if (type == '终端') {
                $(".paraall").hide()
                $(".para-box").hide();
                $(".para-box6").show();
                $("#type2").val("5");
                $("#disk").hide();
                $("#PC").show();
                $("#flash").hide();
                $("#server").hide();
                $("#switch1").hide();
                $("#encoder").hide();
            } else if (type == '解码器') {
                $(".paraall").hide()
                $(".para-box").hide();
                $(".para-box7").show();
                $("#type2").val("6");
                $("#disk").hide();
                $("#PC").hide();
                $("#flash").show();
                $("#server").hide();
                $("#switch1").hide();
                $("#encoder").hide();
            } else if (type == '光端机') {
                $(".paraall").hide()
                $(".para-box").hide()
                $(".para-box8").show();
                $("#type2").val("7");
                $("#disk").hide();
                $("#PC").hide();
                $("#flash").show();
                $("#server").hide();
                $("#switch1").hide();
                $("#encoder").hide();
            }else if (type == '') {
                $(".paraall").show()
                $(".para-box").hide();

            }


        }

        function choose(){
            var paraSelArr = []
            var paraSel = "";
            $(".para-box").each(function(){
                var that = $(this)
                if( that.css("display")  == 'block' ){
                    paraSel = that.find(".paratype").ySelectedValues(",")
                }
            })

            if( paraSel == '' ){

            }else{
                paraSelArr = paraSel.split(",");
                console.log(paraSel,paraSelArr)
                $(".paratable").find("th").hide()
                $(".paratypetr").find("td").hide()
                for (var i =0; i< paraSelArr.length; i++ ){
                    var className = paraSelArr[i]
                    $(".paratable").find("th."+className+"").show()
                    $(".paratypetr").find("td."+className+"").show()
                }
                $(".paratable").find("th").css("width",1/(paraSelArr.length)*100+"%")

            }
        }


    </script>
    <script type="text/javascript">
        $(function() {
            $('.paratype').ySelect({
                placeholder: '请先选择一些参数',
                showSearch: true,
                numDisplayed: 4,
                overflowText: '已选中 {n}项',
                isCheck: false
            });
        });
    </script>
</head>
<body class="main_bg">
<div class="win-header" id="a">
    <span class="win-header-title">网管设备 &gt; 设备列表 </span>
</div>
<div class="win-bodyer">
    <form id="pageForm" name="pageForm" action="${ctx}/dmu/dmuEquipment!findAllRecordPage.do" method="post">
        <input type="hidden" name="id">
        <input type="hidden" name="type" id="type2">
        <input type="hidden" name="time" value="${pageObject.params.time}">
        <input type="hidden" name="status" id="status">
        <fieldset style="width: 98%">
            <legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
            <table width="100%" class="inputTable">
                <tr>
                    <td style="text-align: right">设备ID：</td>
                    <td><input type="text" id="resId" name="pageObject.params.resId" value="${pageObject.params.resId}">
                    </td>
                    <td style="text-align: right">设备名称：</td>
                    <td><input type="text" id="pageObject.params.name" name="pageObject.params.name"
                               value="${pageObject.params.name}"></td>


                    <td style="text-align: right">设备类型：</td>
                    <td>
                        <select id="type" name="pageObject.params.type" onchange="selectSupp()">
                            <option value="">全部</option>
                            <c:forEach var="type" items="${types}">
                                <option value="${type.name}"
                                        <c:if test="${pageObject.params.type eq type.name}">selected</c:if>>${type.name}</option>
                            </c:forEach>
                        </select>
                    </td>


                    <td>
                        <select class="paraall">
                            <option disabled="disabled" selected="selected">请先设备类型</option>
                        </select>
                        <!--服务器-->
                        <div class="para-box para-box1">
                            <select id='paratype1' name="pageObject.params.paratype"  class="paratype"  multiple="multiple">
                                <option value="paratype1" <c:if test="${fn:contains(pageObject.params.paratype,'paratype1')}">selected</c:if>>设备资源ID</option>
                                <option value="paratype2" <c:if test="${fn:contains(pageObject.params.paratype,'paratype2')}">selected</c:if>>设备资源名称</option>
                                <option value="paratype3" <c:if test="${fn:contains(pageObject.params.paratype,'paratype3')}">selected</c:if>>设备资源类型</option>
                                <option value="paratype4" <c:if test="${fn:contains(pageObject.params.paratype,'paratype4')}">selected</c:if>>操作系统信息</option>
                                <option value="paratype5" <c:if test="${fn:contains(pageObject.params.paratype,'paratype5')}">selected</c:if>>硬盘分区数</option>
                                <option value="paratype6" <c:if test="${fn:contains(pageObject.params.paratype,'paratype6')}">selected</c:if>>内存总容量</option>
                                <option value="paratype7" <c:if test="${fn:contains(pageObject.params.paratype,'paratype7')}">selected</c:if>>内存已使用容量</option>
                                <option value="paratype8" <c:if test="${fn:contains(pageObject.params.paratype,'paratype8')}">selected</c:if>>内存剩余容量</option>
                                <option value="paratype9" <c:if test="${fn:contains(pageObject.params.paratype,'paratype9')}">selected</c:if>>CPU数量</option>
                                <option value="paratype10" <c:if test="${fn:contains(pageObject.params.paratype,'paratype10')}">selected</c:if>>网卡端口数量</option>
                                <option value="paratype11" <c:if test="${fn:contains(pageObject.params.paratype,'paratype11')}">selected</c:if>>状态获取时间</option>
                            </select>
                        </div>

                        <!--编码器-->
                        <div class="para-box para-box2">
                            <select id='paratype2' class="paratype" name="pageObject.params.paratype" multiple="multiple" style="display: none">
                                <option value="paratype1"<c:if test="${fn:contains(pageObject.params.paratype,'paratype1')}">selected</c:if>>设备资源ID</option>
                                <option value="paratype2"<c:if test="${fn:contains(pageObject.params.paratype,'paratype2')}">selected</c:if>>设备资源名称</option>
                                <option value="paratype3"<c:if test="${fn:contains(pageObject.params.paratype,'paratype3')}">selected</c:if>>设备资源类型</option>
                                <option value="paratype4"<c:if test="${fn:contains(pageObject.params.paratype,'paratype4')}">selected</c:if>>编码方式</option>
                                <option value="paratype5"<c:if test="${fn:contains(pageObject.params.paratype,'paratype5')}">selected</c:if>>当前码流类型</option>
                                <option value="paratype6"<c:if test="${fn:contains(pageObject.params.paratype,'paratype6')}">selected</c:if>>主码流码率类型</option>
                                <option value="paratype7"<c:if test="${fn:contains(pageObject.params.paratype,'paratype7')}">selected</c:if>>主码流分辨率</option>
                                <option value="paratype8"<c:if test="${fn:contains(pageObject.params.paratype,'paratype8')}">selected</c:if>>主码流帧率</option>
                                <option value="paratype9"<c:if test="${fn:contains(pageObject.params.paratype,'paratype9')}">selected</c:if>>主码流GOP</option>
                                <option value="paratype10"<c:if test="${fn:contains(pageObject.params.paratype,'paratype10')}">selected</c:if>>子码流分辨率</option>
                                <option value="paratype11"<c:if test="${fn:contains(pageObject.params.paratype,'paratype11')}">selected</c:if>>状态获取时间</option>
                            </select>
                        </div>


                        <!--IP摄像机-->
                        <div class="para-box para-box3">
                            <select id='paratype3' class="paratype" name="pageObject.params.paratype"  multiple="multiple">
                                <option value="paratype1" <c:if test="${fn:contains(pageObject.params.paratype,'paratype1')}">selected</c:if>>设备资源ID</option>
                                <option value="paratype2" <c:if test="${fn:contains(pageObject.params.paratype,'paratype2')}">selected</c:if>>设备资源名称</option>
                                <option value="paratype3" <c:if test="${fn:contains(pageObject.params.paratype,'paratype3')}">selected</c:if>>设备资源类型</option>
                                <option value="paratype4" <c:if test="${fn:contains(pageObject.params.paratype,'paratype4')}">selected</c:if>>编码方式</option>
                                <option value="paratype5"<c:if test="${fn:contains(pageObject.params.paratype,'paratype5')}">selected</c:if>>当前码流类型</option>
                                <option value="paratype6"<c:if test="${fn:contains(pageObject.params.paratype,'paratype6')}">selected</c:if>>主码流码率类型</option>
                                <option value="paratype7"<c:if test="${fn:contains(pageObject.params.paratype,'paratype7')}">selected</c:if>>主码流分辨率</option>
                                <option value="paratype8"<c:if test="${fn:contains(pageObject.params.paratype,'paratype8')}">selected</c:if>>主码流帧率</option>
                                <option value="paratype9"<c:if test="${fn:contains(pageObject.params.paratype,'paratype9')}">selected</c:if>>主码流GOP</option>
                                <option value="paratype10"<c:if test="${fn:contains(pageObject.params.paratype,'paratype10')}">selected</c:if>>子码流分辨率</option>
                                <option value="paratype11"<c:if test="${fn:contains(pageObject.params.paratype,'paratype11')}">selected</c:if>>状态获取时间</option>
                            </select>
                        </div>

                        <!--磁盘阵列-->
                        <div class="para-box para-box4">
                            <select id='paratype4' class="paratype"  name="pageObject.params.paratype"  multiple="multiple">
                                <option value="paratype1" <c:if test="${fn:contains(pageObject.params.paratype,'paratype1')}">selected</c:if>>设备资源ID</option>
                                <option value="paratype2"<c:if test="${fn:contains(pageObject.params.paratype,'paratype2')}">selected</c:if>>设备资源名称</option>
                                <option value="paratype3"<c:if test="${fn:contains(pageObject.params.paratype,'paratype3')}">selected</c:if>>设备资源类型</option>
                                <option value="paratype4"<c:if test="${fn:contains(pageObject.params.paratype,'paratype4')}">selected</c:if>>硬盘总容量</option>
                                <option value="paratype5"<c:if test="${fn:contains(pageObject.params.paratype,'paratype5')}">selected</c:if>>硬盘剩余容量</option>
                                <option value="paratype6"<c:if test="${fn:contains(pageObject.params.paratype,'paratype6')}">selected</c:if>>端口个数</option>
                                <option value="paratype7"<c:if test="${fn:contains(pageObject.params.paratype,'paratype7')}">selected</c:if>>CPU温度</option>
                                <option value="paratype8"<c:if test="${fn:contains(pageObject.params.paratype,'paratype8')}">selected</c:if>>风扇状态</option>
                                <option value="paratype9"<c:if test="${fn:contains(pageObject.params.paratype,'paratype9')}">selected</c:if>>坏盘数量</option>
                                <option value="paratype10"<c:if test="${fn:contains(pageObject.params.paratype,'paratype10')}">selected</c:if>>阵列状态</option>
                                <option value="paratype11"<c:if test="${fn:contains(pageObject.params.paratype,'paratype11')}">selected</c:if>>状态获取时间</option>
                            </select>
                        </div>

                        <!--交换机-->
                        <div class="para-box para-box5">
                            <select id='paratype5' class="paratype"  name="pageObject.params.paratype" multiple="multiple">
                                <option value="paratype1"<c:if test="${fn:contains(pageObject.params.paratype,'paratype1')}">selected</c:if>>设备资源ID</option>
                                <option value="paratype2"<c:if test="${fn:contains(pageObject.params.paratype,'paratype2')}">selected</c:if>>设备资源名称</option>
                                <option value="paratype3"<c:if test="${fn:contains(pageObject.params.paratype,'paratype3')}">selected</c:if>>设备资源类型</option>
                                <option value="paratype4"<c:if test="${fn:contains(pageObject.params.paratype,'paratype4')}">selected</c:if>>内存使用率</option>
                                <option value="paratype5"<c:if test="${fn:contains(pageObject.params.paratype,'paratype5')}">selected</c:if>>CPU使用率</option>
                                <option value="paratype6"<c:if test="${fn:contains(pageObject.params.paratype,'paratype6')}">selected</c:if>>端口数量</option>
                                <option value="paratype11"<c:if test="${fn:contains(pageObject.params.paratype,'paratype11')}">selected</c:if>>状态获取时间</option>
                            </select>
                        </div>

                        <!--光端机-->
                        <div class="para-box para-box6">
                            <select id='paratype6' class="paratype" name="pageObject.params.paratype"  multiple="multiple">
                                <option value="paratype1"<c:if test="${fn:contains(pageObject.params.paratype,'paratype1')}">selected</c:if>>设备资源ID</option>
                                <option value="paratype2"<c:if test="${fn:contains(pageObject.params.paratype,'paratype2')}">selected</c:if>>设备资源名称</option>
                                <option value="paratype3"<c:if test="${fn:contains(pageObject.params.paratype,'paratype3')}">selected</c:if>>设备资源类型</option>
                                <option value="paratype4"<c:if test="${fn:contains(pageObject.params.paratype,'paratype4')}">selected</c:if>>CPU使用率</option>
                                <option value="paratype5"<c:if test="${fn:contains(pageObject.params.paratype,'paratype5')}">selected</c:if>>内存使用率</option>
                                <option value="paratype6"<c:if test="${fn:contains(pageObject.params.paratype,'paratype6')}">selected</c:if>>工作状态</option>
                                <option value="paratype11"<c:if test="${fn:contains(pageObject.params.paratype,'paratype11')}">selected</c:if>>状态获取时间</option>
                            </select>
                        </div>

                        <!--终端-->
                        <div class="para-box para-box7">
                            <select id='paratype7' class="paratype"  name="pageObject.params.paratype"   multiple="multiple">
                                <option value="paratype1"<c:if test="${fn:contains(pageObject.params.paratype,'paratype1')}">selected</c:if>>设备资源ID</option>
                                <option value="paratype2"<c:if test="${fn:contains(pageObject.params.paratype,'paratype2')}">selected</c:if>>设备资源名称</option>
                                <option value="paratype3"<c:if test="${fn:contains(pageObject.params.paratype,'paratype3')}">selected</c:if>>设备资源类型</option>
                                <option value="paratype4"<c:if test="${fn:contains(pageObject.params.paratype,'paratype4')}">selected</c:if>>硬盘总容量</option>
                                <option value="paratype5"<c:if test="${fn:contains(pageObject.params.paratype,'paratype5')}">selected</c:if>>硬盘剩余容量</option>
                                <option value="paratype6"<c:if test="${fn:contains(pageObject.params.paratype,'paratype6')}">selected</c:if>>CPU使用率</option>
                                <option value="paratype7"<c:if test="${fn:contains(pageObject.params.paratype,'paratype7')}">selected</c:if>>工作状态</option>
                                <option value="paratype11"<c:if test="${fn:contains(pageObject.params.paratype,'paratype11')}">selected</c:if>>状态获取时间</option>
                            </select>
                        </div>

                        <!--解码器-->
                        <div class="para-box para-box8">
                            <select id='paratype8' class="paratype"  name="pageObject.params.paratype"   multiple="multiple">
                                <option value="paratype1"<c:if test="${fn:contains(pageObject.params.paratype,'paratype1')}">selected</c:if>>设备资源ID</option>
                                <option value="paratype2"<c:if test="${fn:contains(pageObject.params.paratype,'paratype2')}">selected</c:if>>设备资源名称</option>
                                <option value="paratype3"<c:if test="${fn:contains(pageObject.params.paratype,'paratype3')}">selected</c:if>>设备资源类型</option>
                                <option value="paratype4"<c:if test="${fn:contains(pageObject.params.paratype,'paratype4')}">selected</c:if>>CPU使用率</option>
                                <option value="paratype5"<c:if test="${fn:contains(pageObject.params.paratype,'paratype5')}">selected</c:if>>内存使用率</option>
                                <option value="paratype6"<c:if test="${fn:contains(pageObject.params.paratype,'paratype6')}">selected</c:if>>工作状态</option>
                                <option value="paratype11"<c:if test="${fn:contains(pageObject.params.paratype,'paratype11')}">selected</c:if>>状态获取时间</option>
                            </select>
                        </div>
                    </td>
                </tr>
                <tr>

                    <td style="text-align: right">查询起始时间：</td>
                    <td><input class="Wdate" type="text" id="qssj" name="pageObject.params.beginTime"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'jsjs\')||\'%y-%M-%d\'}'})"
                               value="${pageObject.params.beginTime }"/></td>
                    <td style="text-align: right">查询截止时间：</td>
                    <td><input class="Wdate" type="text" id="jsjs" name="pageObject.params.endTime"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'qssj\')||\'%y-%M-%d\'}'})"
                               value="${pageObject.params.endTime }"/></td>
                    <td style="text-align: right">轮询间隔时间：</td>
                    <td><input type="text" id="time" name="pageObject.params.time" value="${pageObject.params.time}">(分钟)
                    </td>
                    <c:choose>
                        <c:when test="${pageObject.params.status eq '0'}">
                            <td style="text-align: center"><a class="a_button" href="javascript:void(0)"
                                                              onclick="return alltimeQuery(1);"><span>关闭轮询</span></a>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td  style="text-align: center"><a class="a_button" href="javascript:void(0)"
                                                               onclick="return alltimeQuery(0);"><span>开启轮询</span></a>
                            </td>
                        </c:otherwise>
                    </c:choose>
                    <td  style="text-align: center"><a class="a_button" href="javascript:void(0)"
                                                       onclick="return query();"><span>查询</span></a></td>
                </tr>
            </table>
        </fieldset>
        <div id="pageDiv" name="pageDiv" class="pageDiv">
            <table class="paratable" width="100%" border="0" cellspacing="0" cellpadding="0">
                <thead>
                <tr id="disk" style="display:none">
                    <th class="paratype1">设备资源ID</th>
                    <th class="paratype2">设备资源名称</th>
                    <th class="paratype3">设备资源类型</th>
                    <th class="paratype4">硬盘总容量</th>
                    <th class="paratype5">硬盘剩余容量</th>
                    <th class="paratype6">端口个数</th>
                    <th class="paratype7">CPU温度</th>
                    <th class="paratype8">风扇状态</th>
                    <th class="paratype9">坏盘数量</th>
                    <th class="paratype10">阵列状态</th>
                    <th class="paratype11">状态获取时间</th>
                </tr>
                <tr id="PC" style="display:none">
                    <th class="paratype1">设备资源ID</th>
                    <th class="paratype2">设备资源名称</th>
                    <th class="paratype3">设备资源类型</th>
                    <th class="paratype4">硬盘总容量</th>
                    <th class="paratype5">硬盘剩余容量</th>
                    <th class="paratype6">内存使用率</th>
                    <th class="paratype7">CPU使用率</th>
                    <th class="paratype8">工作状态</th>
                    <th class="paratype9">工作状态</th>
                    <th class="paratype10">工作状态</th>
                    <th class="paratype11">状态获取时间</th>
                </tr>
                <tr id="flash" style="display:none">
                    <th class="paratype1">设备资源ID</th>
                    <th class="paratype2">设备资源名称</th>
                    <th class="paratype3">设备资源类型</th>
                    <th class="paratype4">CPU使用率</th>
                    <th class="paratype5">内存使用率</th>
                    <th class="paratype6">工作状态</th>
                    <th class="paratype7">工作状态</th>
                    <th class="paratype8">工作状态</th>
                    <th class="paratype9">工作状态</th>
                    <th class="paratype10">工作状态</th>
                    <th class="paratype11">状态获取时间</th>
                </tr>
                <tr id="server" style="display:none">
                    <th class="paratype1">设备资源ID</th>
                    <th class="paratype2">设备资源名称</th>
                    <th class="paratype3">设备资源类型</th>
                    <th class="paratype4">操作系统信息</th>
                    <th class="paratype5">硬盘分区数</th>
                    <th class="paratype6">内存总容量</th>
                    <th class="paratype7">内存已使用容量</th>
                    <th class="paratype8">内存剩余容量</th>
                    <th class="paratype9">CPU数量</th>
                    <th class="paratype10">网卡端口数量</th>
                    <th class="paratype11">状态获取时间</th>
                </tr>
                <tr id="switch1" style="display:none">
                    <th class="paratype1">设备资源ID</th>
                    <th class="paratype2">设备资源名称</th>
                    <th class="paratype3">设备资源类型</th>
                    <th class="paratype4">内存使用率</th>
                    <th class="paratype5">CPU使用率</th>
                    <th class="paratype6">端口数量</th>
                    <th class="paratype11">状态获取时间</th>
                </tr>
                <tr id="encoder" style="display:none">
                    <th class="paratype1">设备资源ID</th>
                    <th class="paratype2">设备资源名称</th>
                    <th class="paratype3">设备资源类型</th>
                    <th class="paratype4">编码方式</th>
                    <th class="paratype5">当前码流类型</th>
                    <th class="paratype6">主码流码率类型</th>
                    <th class="paratype7">主码流分辨率</th>
                    <th class="paratype8">主码流帧率</th>
                    <th class="paratype9">主码流GOP</th>
                    <th class="paratype10">子码流分辨率</th>
                    <th class="paratype11">状态获取时间</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="dmuEquipment" items="${pageObject.resultList}">
                    <tr class="paratypetr">
                        <td class="paratype1">${dmuEquipment.resId}</td>
                        <td class="paratype2">${dmuEquipment.name}</td>
                        <td class="paratype3">${dmuEquipment.type}</td>
                        <td class="paratype4">${dmuEquipment.a1}</td>
                        <td class="paratype5">${dmuEquipment.a2}</td>
                        <td class="paratype6">${dmuEquipment.a3}</td>
                        <td class="paratype7">${dmuEquipment.a4}</td>
                        <td class="paratype8">${dmuEquipment.a5}</td>
                        <td class="paratype9">${dmuEquipment.a6}</td>
                        <td class="paratype10">${dmuEquipment.a7}</td>
                        <td class="paratype11">${dmuEquipment.recordTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="11">
                        <jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="11" style="text-align: center;vertical-align: top;height: 35px">
                        <a class="a_button" href="javascript:void(0);"
                           onclick="return exportList();"><span>导出</span></a>
                    </td>
                </tr>
                </tfoot>
            </table>
        </div>
    </form>
</div>
</body>
</html>