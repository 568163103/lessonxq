<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-x: hidden;">
<head>
    <%@ include file="/jsp/common/head/headPage.jsp"%>
    <link rel="stylesheet" href="${ctx}/css/dynatree/ui.dynatree.css" />
    <script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.dynatree.js"></script>
    <title>${global_app_name}</title>
    <script type="text/javascript">
        $(function() {
            $("#tree_0").dynatree({
                checkbox: false,
                selectMode: 3,
                autoCollapse: false,
                fx: { height: "toggle", duration: 200 },
                imagePath:"${ctx}/css/dynatree/images/",
                onActivate: function(node) {
                    if (node.data.isFolder)
                        return;
                    
                    // 加载存储视图
                    var flag = $("#flag").val();
                    var url = "${ctx }/storage/storageact!getStorageView.do?id=" + node.data.id;
                    url += "&flag=" + flag;
                    $("#storageView").attr("src", url);
                },
            });
            
            var ectree = ${ectree};
            var rootNode = $("#tree_0").dynatree("getRoot");
            var childNode = rootNode.addChild({
                title: "前端设备",
                isFolder: true,
                expand: true,
                icon: 'tree_folder.png',
                key: '0',
                children: ectree.ecTreeNodes,
            });
        });
    </script>
</head>
<body class="main_bg" style="background: background:#FFFFFF;">
    <input type="hidden" id="flag" value="${ flag }"/>
    <div class="win-header" id="a">
        <c:choose>
            <c:when test="${ flag == '1' }">
                <span class="win-header-title">存储检测 &gt; 中心存储 </span>
            </c:when>
            <c:when test="${ flag == '2' }">
                <span class="win-header-title">存储检测 &gt; 前端存储 </span>
            </c:when>
        </c:choose>
    </div>
    <div class="win-bodyer">
        <table width="80%" height="490px" border="0">
            <tr>
                <td id="leftTree" align="center" style="width:10%;height:100%;">
                    <fieldset style="height:100%;">
                        <table class="inputTable" border="0">
                            <tr>
                                <td align="center" style="padding-left: 2px">
                                    <div id="tree_0" style="padding-left: 2px;width:230px;height:100%;overflow:auto;"></div>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
                <td align="left" style="width: 100%; height: 100%; float: left;">
                    <iframe id="storageView" style="width:100%;height:100%" frameBorder="0" scrolling="auto" allowtransparency=yes src=""></iframe>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>