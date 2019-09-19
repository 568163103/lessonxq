<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headFormTree.jsp"%>
	<style type="text/css">
		.el_highlight{
			background-color:#95cffc;
		}
	</style>
	<title>${global_app_name}</title>
	<script type="text/javascript">
		var rootNode = null;
		var sourceNodes = [];
		var targetNode = null;
		var channelValue = '${channelValue}';
		function isNullNode(){
			if(targetNode==null){
				alert("请选择左侧自定义目录树节点！");
				return true;
			}
			return false;
		}
		function resourceIsEmpty() {
			sourceNodes = [];
			var tree1 = $("#tree_1").dynatree('getTree');
			$.map(tree1.getSelectedNodes(), function(node){
				var keys = node.data.key.split("---");
				if(keys[keys.length-1].substring(6,9)==channelValue){
					sourceNodes[sourceNodes.length]=node;
				}
				node.select(false);
			});
			if(sourceNodes.length == 0){
				alert("请选择右侧资源树的通道节点添加！");
				return true;
			}

			var isExit = false;
			rootNode.visit(function(e){
				$(sourceNodes).each(function(i,sourceNode) {
					isExit = e.data.key.indexOf(sourceNode.data.key) >= 0;
					if(isExit){
						e.focus(true);
						alert("同一资源不能重复添加："+sourceNode.data.title);
						return false;
					}
				});
				if(isExit){
					return false;
				}
			});
			if(isExit){
				sourceNodes = [];
				return true;
			}
			var resIdInput = $("#userTree\\.resId");
			var resNameInput = $("#userTree\\.name");
			resIdInput.val("");resNameInput.val("");
			$(sourceNodes).each(function(i,sourceNode) {
				if(i != 0){
					resIdInput.val(resIdInput.val()+",");
					resNameInput.val(resNameInput.val()+",");
				}
				resIdInput.val(resIdInput.val()+sourceNode.data.key);
				resNameInput.val(resNameInput.val()+sourceNode.data.title);
			});
			sourceNodes = [];
			return false;
		}
		function nodeSave(nodeType){
			if(3==nodeType){
				if(isNullNode()){
					return;
				}
				var keys = targetNode.data.key.split("---");
				if(keys[keys.length-1].substring(6,9)==channelValue){
					alert("通道资源节点不能添加子节点！");
					return;
				}
				if(resourceIsEmpty()){
					return;
				}
			} else {
				$("#editNodeDiv").dialog("close");
				$("#userTree\\.resId").val("");
				$("#userTree\\.name").val($("#editNodeInput").val());
			}
			if(1==nodeType){
				$("#userTree\\.parentId").val('${cmuId}');
			} else {
				var keys = targetNode.data.key.split("---");
				$("#userTree\\.parentId").val(keys[keys.length-1]);
			}

			var siblings = null;
			if(1==nodeType){
				siblings = rootNode.getChildren();
			} else {
				siblings = targetNode.getChildren();
			}
			if(siblings && siblings.length>0){
				var keys = siblings[siblings.length-1].data.key.split("---");
				$("#userTree\\.previousId").val(keys[keys.length-1]);
			} else {
				$("#userTree\\.previousId").val("");
			}
			$.post("${ctx}/bussiness/usertree!saveUserTree.do",$("#pageForm").serializeArray(),function(data){
				if(typeof(data)=="object" && data[0] && data[0]["title"]){
					$.post("${ctx}/bussiness/usertree!findResourceTree.do?uid=${param.uid}&name=",null,function(data){					
						reload_tree_1(data);
					});
					if(1==nodeType){
						rootNode.addChild(data[0]);
						rootNode.expand(true);
					} else {
						targetNode.addChild(data);
						targetNode.expand(true);
					}
				} else {
					alert("保存数据失败！");
				}
			});
			
			
		}
		function nodeRemoveNode(node){
			var keys = node.data.key.split("---");
			$("#userTree\\.resId").val(keys[keys.length-1]);
			$("#userTree\\.name").val(node.data.title);

			var parentNode = node.getParent();
			if(parentNode && parentNode.data.title){
				var keys = parentNode.data.key.split("---");
				$("#userTree\\.parentId").val(keys[keys.length-1]);
			} else {
				$("#userTree\\.parentId").val('${cmuId}');
			}

			var prevSiblingNode = node.getPrevSibling();
			if(prevSiblingNode){
				var keys = prevSiblingNode.data.key.split("---");
				$("#userTree\\.previousId").val(keys[keys.length-1]);
			} else {
				$("#userTree\\.previousId").val("");
			}

			$.post("${ctx}/bussiness/usertree!deleteUserTree.do",$("#pageForm").serializeArray(),function(data){
				if(typeof(data)!="undefined" && data=="ok"){
					node.remove();
					node = null;
				} else {
					alert("删除数据失败！");
				}
			});
		}
		function nodeRemove(){
			if(isNullNode()){
				return;
			}
			nodeRemoveNode(targetNode);
			targetNode = null;
		}
		function nodeRemoveChilds(){
			if(isNullNode()){
				return;
			}
			var childrens = targetNode.getChildren();
			if(null == childrens || childrens.length==0){
				alert("该节点无子节点可删除！");
				return;
			}
			$(childrens).each(function(i,node){
				nodeRemoveNode(node);
			});
		}
		function nodeUpdate(node){
			var keys = node.data.key.split("---");
			$("#userTree\\.resId").val(keys[keys.length-1]);
			$("#userTree\\.name").val(node.data.title);

			var parentNode = node.getParent();
			if(parentNode && parentNode.data.title){
				var keys = parentNode.data.key.split("---");
				$("#userTree\\.parentId").val(keys[keys.length-1]);
			} else {
				$("#userTree\\.parentId").val('${cmuId}');
			}

			var prevSiblingNode = node.getPrevSibling();
			if(prevSiblingNode){
				var keys = prevSiblingNode.data.key.split("---");
				$("#userTree\\.previousId").val(keys[keys.length-1]);
			} else {
				$("#userTree\\.previousId").val("");
			}

			$.post("${ctx}/bussiness/usertree!updateUserTree.do",$("#pageForm").serializeArray(),function(data){
				if(typeof(data)=="object" && data[0] && data[0]["title"]){
					node.data.title=data[0].title;
					node.data.key = data[0].key;
					node.render(false);
				} else {
					alert("更新数据失败！");
				}
			});
		}
		function nodeMove(node,sourceNode,hitMode){
			$.post("${ctx}/bussiness/usertree!moveUserTree.do",[{"name":"flag","value":hitMode},{"name":"siblingIds","value":sourceNode.data.key},{"name":"siblingIds","value":node.data.key}],function(data){
				if(data=="ok"){
					sourceNode.move(node, hitMode);
				} else {
					alert("移动结点失败！");
				}
			});
		}
		function nodeMoveDown(i,object){
			if(isNullNode())
				return;
			$(object).button( "disable" );
			var siblingNode = null;var mode = "before";
			if(i>0){
				siblingNode = targetNode.getNextSibling();
				mode = "after";
			} else {
				siblingNode = targetNode.getPrevSibling();
			}
			if(siblingNode){
				$.post("${ctx}/bussiness/usertree!moveUserTree.do",[{"name":"flag","value":mode},{"name":"siblingIds","value":targetNode.data.key},{"name":"siblingIds","value":siblingNode.data.key}],function(data){
					$(object).button( "enable" );
					if(data=="ok"){
						targetNode.move(siblingNode,mode);
					} else {
						alert("移动结点失败！");
					}
				});
			} else {
				$(object).button( "enable" );
				alert("没有结点可移动！")
			}
		}
		function openUserTreeDialog(action){
			if(action != 1 && isNullNode()){
				return;
			} else if(2 == action){
				var keys = targetNode.data.key.split("---");
				if(keys[keys.length-1].substring(6,9)==channelValue){
					alert("通道资源节点不能添加子节点！");
					return;
				}
			}
			if(action == 0){
				$("#editNodeInput").val(targetNode.data.title);
			} else {
				$("#editNodeInput").val("");
			}
			$("#editNodeButton").attr("name","editNodeButton_"+action);
			$("#editNodeDiv").dialog("open");
		}
		function handleSave(action){
			if($("#editNodeInput").val()==""){
				alert("请输入节点名称");
				return;
			}
			if(action == 0){
				targetNode.data.title = $("#editNodeInput").val();
				nodeUpdate(targetNode);
				$("#editNodeDiv").dialog("close");
			} else {
				nodeSave(action);
			}
		}
		function bindContextMenu(span) {
			$(span).contextMenu({menu: "myMenu"}, function(action, el, pos) {
				targetNode = $.ui.dynatree.getNode(el);
				switch( action ) {
					case "添加主节点":
						openUserTreeDialog(1);
						break;
					case "添加节点":
						openUserTreeDialog(2);
						break;
					case "编辑节点":
						openUserTreeDialog(0);
						break;
					case "删除节点":
						nodeRemove();
						break;
					case "删除子节点":
						nodeRemoveChilds();
						break;
					case "上移节点":
						nodeMoveDown(-1,$('#nodeUp'));
						break;
					case "下移节点":
						nodeMoveDown(1,$('#nodeDown'));
						break;
					case "复制目录树":
						openUserDialog();
						break;
					case "返回":
						window.location.href="${ctx}/security/user!findPage.do";
				}
			});
		}
		function init_tree_0 (userResourceTree){
			$("#tree_0").dynatree({
				autoCollapse: false,
				fx: { height: "toggle", duration: 200 },
				imagePath:"${ctx}/css/dynatree/images/",
				children:userResourceTree,
			    onLazyRead: function(node){
					var keys = node.data.key.split("---");
					node.appendAjax({
						url: "${ctx}/bussiness/usertree!findUserResourceTree.do?uid=${param.uid}",
						data: {pid: keys[keys.length-1],mode: "funnyMode"}
					});
				},
				onActivate: function(dtnode) {
					targetNode = dtnode;
				},
				onCreate: function(node, span){
					bindContextMenu(span);
				},
				dnd: {
					preventVoidMoves: true,
					onDragStart: function(node) {
						return true;
					},
					onDragEnter: function(node, sourceNode) {
						if(node.parent !== sourceNode.parent){
							//return false;
						}
						return ["before", "after"];
					},
					onDrop: function(node, sourceNode, hitMode, ui, draggable) {
						nodeMove(node,sourceNode,hitMode);
					}
				}
			});
		}
		
		function init_tree_1 (resourceTree){
			$("#tree_1").dynatree({
				activeVisible:true,
				checkbox: true,
				selectMode: 3,
				autoCollapse: false,
				fx: { height: "toggle", duration: 200 },
				children:resourceTree,
				onActivate: function(dtnode) {
				}
			});
		}
		
		function reload_tree_1 (resourceTree){	
			$("#tree_1").dynatree({
				activeVisible:true,
				checkbox: true,
				selectMode: 3,
				autoCollapse: false,
				fx: { height: "toggle", duration: 200 },
				children:resourceTree,
				onActivate: function(dtnode) {
				}
			});
			 $("#tree_1").dynatree("getTree").reload();
		}
		$(function(){	
			var resourceTree = ${resourceTree};
			var userResourceTree= ${userResourceTree};
			init_tree_0(userResourceTree);
			init_tree_1(resourceTree);
			rootNode = $("#tree_0").dynatree("getRoot");
			$("#editNodeDiv").dialog({
				autoOpen: false
			});
			$("#targetUserDiv").dialog({
				autoOpen: false,
				height:380,
				width:600
			});
		});
		function openUserDialog(){
			window.location.href = "${ctx}/bussiness/usertree!beforeCopyUserTreeToUser.do?uid=${param.uid}";
		}

		function synUserTree(){
			$.post("${ctx}/bussiness/usertree!synUserTree.do?uid=${param.uid}",function(data){
				if(data=="ok"){
					alert("同步成功！");
				} else {
					alert("同步失败！");
				}
			});

		}

		var targetObjects = null;var nextTargetObjects = [];var currIndex = 0;
		function nextTarget(){
			if(0 == nextTargetObjects.length){
				queryResource ();
			}
			if(nextTargetObjects.length > currIndex){
				$(nextTargetObjects[currIndex]).click();
				currIndex++;
			} else if(nextTargetObjects.length > 0){
				currIndex = 0;
				nextTarget();
			}
		}
		function queryResource () {			
			var queryValue = $("#queryObject").val();
			$.post("${ctx}/bussiness/usertree!findResourceTree.do?uid=${param.uid}&name="+queryValue,null,function(data){					
					reload_tree_1(data);
			});			
		}
	</script>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">业务管理 &gt; 用户管理 &gt; 自定义目录树</span>
</div>
<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/usertree!save.do" method="post">
		<input type="hidden" id="userTree.userId" name="userTree.userId" value="${param.uid}">
		<input type="hidden" id="userTree.name" name="userTree.name" value="">
		<input type="hidden" id="userTree.resId" name="userTree.resId" value="">
		<input type="hidden" id="userTree.parentId" name="userTree.parentId" value="">
		<input type="hidden" id="userTree.previousId" name="userTree.previousId" value="">
		<table width="100%" class="inputTable" border="0">
			<tr>
				<td>
					<fieldset style="width: 97%;height: 790px;">
						<legend>&nbsp;&nbsp;自定义目录树&nbsp;&nbsp;</legend>
						<table class="inputTable" style="width: 100%" border="0">
							<tr>
								<td align="center" style="padding-left: 5px">
									<div id="tree_0" style="padding-left: 5px;height: 720px;overflow:auto;"></div>
								</td>
							</tr>
							<tr>
								<td style="text-align:center;">
									<a class="a_button" href="javascript:openUserTreeDialog(1)"><span>添加主节点</span></a>&nbsp;&nbsp;
									<a class="a_button" href="javascript:openUserTreeDialog(2)"><span>添加节点</span></a>&nbsp;&nbsp;
									<a class="a_button" href="javascript:openUserTreeDialog(0)"><span>编辑节点</span></a>&nbsp;&nbsp;
									<a class="a_button" href="javascript:nodeRemove()"><span>删除节点</span></a>&nbsp;&nbsp;
									<a class="a_button" href="javascript:nodeRemoveChilds()"><span>删除子节点</span></a>&nbsp;&nbsp;
									<a class="a_button" href="javascript:nodeMoveDown(-1,$('#nodeUp'))" id="nodeUp"><span>上移节点</span></a>&nbsp;&nbsp;
									<a class="a_button" href="javascript:nodeMoveDown(1,$('#nodeDown'))" id="nodeDown"><span>下移节点</span></a>&nbsp;&nbsp;
									<%--<a class="a_button" href="javascript:synUserTree()"><span>同步目录树到客户端</span></a>&nbsp;--%>&nbsp;
									<a class="a_button" href="javascript:openUserDialog()"><span>复制目录树</span></a>&nbsp;&nbsp;
									<a class="a_button" href="${ctx}/security/user!findPage.do"><span>返回</span></a>
								</td>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
				<td width="54px">
					<a class="a_button" href="javascript:nodeSave(3)"><span>&lt;&lt;</span></a>
				</td>
		<!--		<td>
					<fieldset style="width: 96%;height: 790px;">
						<legend>&nbsp;&nbsp;分配资源&nbsp;&nbsp;</legend>
						<table width="100%" class="inputTable" border="0">
							<tr>
								<td align="center">
									<div id="tree_1" style="padding-left: 5px;height: 720px;overflow:auto;"></div>
								</td>
							</tr>
							<tr>
								<td style="text-align:left;padding-left: 50px;vertical-align: middle">
									<input type="text" name="queryObject" id="queryObject" value="">&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="a_button" href="javascript:queryResource()"><span>查询</span></a>&nbsp;&nbsp;
									<a class="a_button" href="javascript:nextTarget()"><span>下一个</span></a>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>   -->
			</tr>
		</table>
	</form>
</div>
<ul id="myMenu" class="contextMenu">
	<li class="add"><a href="#添加主节点">添加主节点</a></li>
	<li class="add separator"><a href="#添加节点">添加节点</a></li>
	<li class="edit"><a href="#编辑节点">编辑节点</a></li>
	<li class="delete"><a href="#删除节点">删除节点</a></li>
	<li class="delete"><a href="#删除子节点">删除子节点</a></li>
	<li class="up separator"><a href="#上移节点">上移节点</a></li>
	<li class="down"><a href="#下移节点">下移节点</a></li>
	<li class="copy separator"><a href="#复制目录树">复制目录树</a></li>
	<li class="quit separator"><a href="#返回">返回</a></li>
</ul>
<div id="editNodeDiv" align="center">
	<br><br>
	节点名称：<input type="text" id='editNodeInput' name="editNodeInput" value="" style="height: 23px;width: 200px;">
	<br><br>
	<input type="button" id="editNodeButton" name="editNodeButton_2" class="a_button" value="保存" onclick="handleSave($(this).attr('name').split('_')[1])">
</div>
</body>
</html>