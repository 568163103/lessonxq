<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headFormTree.jsp"%>
	<title>${global_app_name}</title>
	<script type="text/javascript">
		var tempNode = null;
		function isNullNode(){
			if(tempNode==null){
				alert("请选择树节点！");
				return true;
			}
			return false;
		}
		function menuEdit(){
			if(isNullNode())
				return;
			$.post("${ctx}/security/menu!beforeUpdate.do?menu.mid="+tempNode.data.key,null,function(data){
				$("#menuForm").JSONToForm(data,"menu");
			});
		}
		function menuSave(){
			if(isNullNode())
				return;
			$("#menu\\.mid").val("");
			$("#menu\\.preid").val(tempNode.data.key);
			$("#menu\\.fullMid").val(tempNode.data.fullId);
			$("#menu\\.lel").val(tempNode.data.level+1);
			if ($("#menuForm").validationEngine("validate")){
				$.post("${ctx}/security/menu!save.do",$("#menuForm").serializeArray(),function(data){
					if(typeof(data)=="object" && data[0] && data[0]["title"]){
						tempNode.addChild(data[0]);
						tempNode.expand(true);
						//$("#menuForm").JSONToForm(data[0]);
						$("#menuForm")[0].reset();
					} else {
						alert("保存数据失败！");
					}
				});
			}
		}
		function menuRemove(){
			if(isNullNode())
				return;
			if(tempNode.data.level != 0){
				$.post("${ctx}/security/menu!delete.do",{"menu.fullMid":tempNode.data.fullId},function(data){
					if(typeof(data)!="undefined" && data=="ok"){
						tempNode.remove();
						tempNode = null;
				        $("#menuForm")[0].reset();
					} else {
						alert("删除数据失败！");
					}
				});
			} else {
				alert("顶级菜单不允删除！");
			}
		}
		function menuUpdate(){
			if(isNullNode())
				return;
			if ($("#menuForm").validationEngine("validate")){
				$.post("${ctx}/security/menu!update.do",$("#menuForm").serializeArray(),function(data){
					if(typeof(data)=="object" && data[0] && data[0]["title"]){
						tempNode.data.title=data[0].title;
						tempNode.data.isFolder=data[0].isFolder;
						tempNode.render(false);
						$("#menuForm")[0].reset();
					} else {
						alert("更新数据失败！");
					}
				});
			}
	    }
		function beforeAuthForMenu() {
			if(isNullNode())
				return;
			$("#menu\\.mid").val(tempNode.data.key);
			$("#menuForm").attr("action","${ctx}/security/menu!beforeAuthForMenu.do");
			$("#menuForm").submit();
		}
		function cleanForm(){
	        $("#menuForm").find("input").val("");
		}
		function menuMove(i){
			if(isNullNode())
				return;
			var siblingNode = null;var mode = "before";
	        if(i>0){
	        	siblingNode = tempNode.getNextSibling();
	        	mode = "after";
	        } else {
	        	siblingNode = tempNode.getPrevSibling();
	        }
	        if(siblingNode){
	        	$.post("${ctx}/security/menu!menuMove.do",[{"name":"siblingIds","value":tempNode.data.key},{"name":"siblingIds","value":siblingNode.data.key}],function(data){
					if(data=="ok"){
						tempNode.move(siblingNode,mode);
					} else {
						alert("移动结点失败！");
					}
				});
	        } else {
	        	alert("没有结点可移动！")
	        }
		}
		$(function() {
			$("#tree").dynatree({
				autoCollapse: true,
				fx: { height: "toggle", duration: 200 },
				children:${json},
				onActivate: function(dtnode) {
					tempNode = dtnode;
					menuEdit();
			    }
			});
		});
	</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">权限管理 &gt; 功能管理 </span>
	</div>
	<div class="win-bodyer">
		<table width="100%" cellspacing="0" cellpadding="0" >
			<tr>
				<td style="vertical-align: top" width="150">
					<div id="tree" height="200"/>
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td valign="top">
					<form id="menuForm" name="menuForm" action="${ctx}/security/menu!save.do" method="post">
						<input type="hidden" id="menu.mid" name="menu.mid" value="${menu.mid}"/>
						<input type="hidden" id="menu.preid" name="menu.preid" value="${menu.preid}"/>
						<input type="hidden" id="menu.fullMid" name="menu.fullMid" value="${menu.fullMid}"/>
						<input type="hidden" id="menu.lel" name="menu.lel" value="${menu.lel}"/>
						<table>
							<tr height="30">
								<td style="text-align:right;">名称：</td><td><input class="validate[required]" id="menu.name" type="text" name="menu.name" value="${menu.name}" /></td>
							</tr>
							<tr height="30">
								<td style="text-align:right;">URL：</td><td><input id="menu.url" type="text" name="menu.url" value="${menu.url}" size="56"></td>
							</tr>
							<tr height="30">
								<td style="text-align:right;">是否菜单：</td><td><select id="menu.isFunc" name="menu.isFunc"><option value="0">是</option><option value="1">否</option></select></td>
							</tr>
							<tr height="30">
								<td style="text-align:right;">状态：</td><td><select id="menu.status" name="menu.status"><option value="1">有效</option><option value="0">无效</option></select></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr height="30">
				<td></td>
				<td colspan="2" style="text-align:left;">
					<a class="a_button" href="javascript:cleanForm()"><span>清空</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:menuSave()"><span>添加</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:menuEdit()"><span>编辑</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:menuUpdate()"><span>更新</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:menuRemove()"><span>删除</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:menuMove(-1)"><span>上移</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:menuMove(+1)"><span>下移</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:beforeAuthForMenu()"><span>授权</span></a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>