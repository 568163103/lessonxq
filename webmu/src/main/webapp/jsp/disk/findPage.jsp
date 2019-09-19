<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-x: hidden;">
<head>
    <%@ include file="/jsp/common/head/headPage.jsp"%>
    <link rel="stylesheet" href="${ctx}/css/dynatree/ui.dynatree.css" />
    <script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.dynatree.js"></script>
    <title>${global_app_name}</title>
    <script type="text/javascript">
        function loadChildNode(node) {
        	if (node.data.key == "0")
                return;
            if (node.data.isFolder && !node.data.loaded) {
                // 加载下级目录
                var url = "${ctx }/thirdparty/basicFormInterface!execute.do";
                var param = {"cmd": "150106", "id": node.data.key, "ip": node.data.ip};
                $.post(url, param, function(data) {
                    node.data.loaded = true;
                    for (var idx = 0; idx < data.length; ++idx) {
                        if (data[idx].status == 0) 
                            continue;
                        if (data[idx].model == "platform") {
                            node.addChild({
                                title: data[idx].name,
                                isFolder: true,
                                icon: 'tree_folder.png',
                                key: data[idx].id,
                                isLazy: true,
                                ip: data[idx].ip,
                            });
                        } else {
                            node.addChild({
                                title: data[idx].name,
                                isFolder: false,
                                icon: 'tree_camera_online.png',
                                key: data[idx].id,
                                tag: '1',
                                ip: node.data.ip
                            });
                        }
                    }
                });
            }
        }
        
        $(function() {
        	$("#tree_0").dynatree({
        		checkbox: false,
                selectMode: 3,
                autoCollapse: false,
                fx: { height: "toggle", duration: 200 },
                imagePath:"${ctx}/css/dynatree/images/",
        		onActivate: function(node) {
        			debugger;
        			if (node.data.isFolder)
        				return;
        			
        			// 加载硬盘状态
                    var url = "${ctx }/thirdparty/basicFormInterface!execute.do?cmd=150102&id=" + node.data.key;
                    url += "&name=" + node.data.title
                    url += "&tag=" + node.data.tag;
                    if (node.data.tag == "1") {
                        // 下级
                        url += "&ip=" + node.data.ip;
                    }
                    $("#diskView").attr("src", url);
                },
                onLazyRead: function(node) {
                	loadChildNode(node);
                }
        	});
        	
        	var rootNode = $("#tree_0").dynatree("getRoot");
        	var childNode = rootNode.addChild({
        		title: "前端设备",
                isFolder: true,
                expand: true,
                icon: 'tree_folder.png',
                key: '0'
        	});
        	
        	var encoders = ${encoders};
        	for (var idx = 0; idx < encoders.length; ++idx) {
        		if (encoders[idx].status == 0)
        			continue;
        		if (encoders[idx].model == "platform") {
        			var cc = childNode.addChild({
                        title: encoders[idx].name,
                        isFolder: true,
                        icon: 'tree_folder.png',
                        key: encoders[idx].id,
                        isLazy: true,
                        ip: encoders[idx].ip,
                        loaded: false
                    });
        		} else {
        			childNode.addChild({
                        title: encoders[idx].name,
                        isFolder: false,
                        icon: 'tree_camera_online.png',
                        key: encoders[idx].id,
                        tag: '0',
                        ip: encoders[idx].ip
                    });
        		}
        	}
        });
    </script>
</head>
<body class="main_bg" style="background: background:#FFFFFF;">
    <div class="win-header" id="a">
        <span class="win-header-title">性能管理 &gt; 硬盘检测 </span>
    </div>
    <div class="win-bodyer">
        <table width="95%" border="0">
            <tr>
                <td id="leftTree" align="center" style="width:10%;height:100%;">
                    <fieldset style="height:100%;">
                        <table class="inputTable" border="0">
                            <tr>
                                <td align="center" style="padding-left: 2px">
                                    <div id="tree_0" style="padding-left: 2px;width:230px;height:800px;overflow:auto;"></div>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
                <td align="left" style="width: 100%; height: 800px; float: left;">
                    <iframe id="diskView" style="width:100%;height:100%" frameBorder="0" scrolling="auto" allowtransparency=yes src=""></iframe>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>