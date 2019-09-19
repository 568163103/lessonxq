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
		function dictEdit(){
			if(isNullNode())
				return;
			$.post("${ctx}/baseinfo/dict!beforeUpdate.do?dict.did="+tempNode.data.key,null,function(data){
				$("#dictForm").JSONToForm(data,"dict");
			});
		}
		function dictSave(){
			if(isNullNode())
				return;
			$("#dict\\.did").val("");
			$("#dict\\.preId").val(tempNode.data.key);
			if ($("#dictForm").validationEngine("validate")){
				$.post("${ctx}/baseinfo/dict!save.do",$("#dictForm").serializeArray(),function(data){
					if(typeof(data)=="object" && data[0] && data[0]["title"]){
						tempNode.addChild(data[0]);
						tempNode.expand(true);
						$("#dictForm").JSONToForm(data[1]);
					} else {
						alert("保存数据失败！");
					}
				});
			}
		}
		function dictRemove(){
			if(isNullNode())
				return;
			if(tempNode.data.level != 0){
				$.post("${ctx}/baseinfo/dict!delete.do",{"dict.id":tempNode.data.key},function(data){
					if(typeof(data)!="undefined" && data=="ok"){
						tempNode.remove();
						tempNode = null;
					} else {
						alert("删除数据失败！");
					}
				});
			} else {
				alert("顶级字典不允删除！");
			}
		}
		function dictUpdate(){
			if(isNullNode())
				return;
			if ($("#dictForm").validationEngine("validate")){
				$.post("${ctx}/baseinfo/dict!update.do",$("#dictForm").serializeArray(),function(data){
					if(typeof(data)=="object" && data[0] && data[0]["title"]){
						tempNode.data.title=data[0].title;
						tempNode.render(false);
					} else {
						alert("更新数据失败！");
					}
				});
			}
	    }
		$(function() {
			$("#dictForm").validationEngine();
			$("#tree").dynatree({
				autoCollapse: true,
				fx: { height: "toggle", duration: 200 },
				children:${treeNodes},
				onActivate: function(dtnode) {
					tempNode = dtnode;
					dictEdit();
			    }
			});
		});
	</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">数据维护 &gt; 字典维护</span>
	</div>
	<div class="win-bodyer">
		<table width="100%" cellspacing="0" cellpadding="0" >
			<tr>
				<td style="vertical-align: top" width="150">
					<div id="tree" height="600" style="padding-top: 0px"/>
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td valign="top">
					<form id="dictForm" name="dictForm" action="${ctx}/baseinfo/dict!save.do" method="post">
						<s:token/>
						<input type="hidden" id="dict.did" name="dict.did" value="${dict.did}"/>
						<input type="hidden" id="dict.preId" name="dict.preId" value="${dict.preId}"/>
						<table>
							<tr height="50">
								<td style="text-align:right;">名称：</td><td><input class="validate[required]" id="dict.name" type="text" name="dict.name" value="${dict.name}" size="30" /></td>
							</tr>
							<tr height="50">
								<td style="text-align:right;">值：</td><td><input class="validate[required]" id="dict.value" type="text" name="dict.value" value="${dict.value}" size="30"></td>
							</tr>
							<tr height="50">
								<td style="text-align:right;">是否有效：</td><td><input type="radio" name="dict.status" value="true" checked="checked"> 有效 &nbsp;&nbsp;<input type="radio" name="dict.status" value="false"> 无效</td>
							</tr>
							<tr height="100">
								<td style="text-align:right;">备注：</td><td><textarea id="dict.descr" type="text" name="dict.descr" cols="100" rows="3">${dict.descr}</textarea></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr height="30">
				<td>&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td style="text-align:left;">
					<a class="a_button" href="javascript:dictEdit()"><span>编辑</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:dictSave()"><span>添加</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:dictRemove()"><span>删除</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:dictUpdate()"><span>更新</span></a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>