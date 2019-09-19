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
		function deptEdit(){
			if(isNullNode())
				return;
			$.post("${ctx}/baseinfo/dept!beforeUpdate.do?dept.deptId="+tempNode.data.key,null,function(data){
				$("#deptForm").JSONToForm(data,"dept");
			});
		}
		function deptSave(){
			if(isNullNode())
				return;
			$("#dept\\.deptId").val("");
			$("#dept\\.preid").val(tempNode.data.key);
			$("#dept\\.fullDid").val(tempNode.data.fullId);
			$("#dept\\.lel").val(tempNode.getLevel()+1);
			if ($("#deptForm").validationEngine("validate")){
				$.post("${ctx}/baseinfo/dept!save.do",$("#deptForm").serializeArray(),function(data){
					if(typeof(data)=="object" && data[0] && data[0]["title"]){
						tempNode.addChild(data[0]);
						tempNode.expand(true);
						$("#deptForm").JSONToForm(data[1]);
						$("#deptForm")[0].reset();
					} else {
						alert("保存数据失败！");
					}
				});
			}
		}
		function deptRemove(){
			if(isNullNode())
				return;
			if(tempNode.data.level != 0){
				$.post("${ctx}/baseinfo/dept!delete.do",{"dept.fullDid":tempNode.data.fullId},function(data){
					if(typeof(data)!="undefined" && data=="ok"){
						tempNode.remove();
						tempNode = null;
				        $("#deptForm")[0].reset();
					} else {
						alert("删除数据失败！");
					}
				});
			} else {
				alert("顶级组织机构不允删除！");
			}
		}
		function deptUpdate(){
			if(isNullNode())
				return;
			if ($("#deptForm").validationEngine("validate")){
				$.post("${ctx}/baseinfo/dept!update.do",$("#deptForm").serializeArray(),function(data){
					if(typeof(data)=="object" && data[0] && data[0]["title"]){
						tempNode.data.title=data[0].title;
						tempNode.data.isFolder=data[0].isFolder;
						tempNode.render(false);
						$("#deptForm")[0].reset();
					} else {
						alert("更新数据失败！");
					}
				});
			}
	    }
		function reset(){
	        $("#deptForm")[0].reset();
		}
		$(function() {
			$("#deptForm").validationEngine();
			$("#tree").dynatree({
				autoCollapse: true,
				fx: { height: "toggle", duration: 200 },
				children:${json},
				onActivate: function(dtnode) {
					tempNode = dtnode;
					deptEdit();
			    }
			});
		});
	</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">数据维护 &gt; 组织机构管理 </span>
	</div>
	<div class="win-bodyer">
		<table width="100%" cellspacing="0" cellpadding="0" >
			<tr>
				<td valign="top" width="150">
					<div id="tree" height="200"/>
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td valign="top">
					<form id="deptForm" name="deptForm" action="${ctx}/baseinfo/dept!save.do" method="post">
						<s:token/>
						<input type="hidden" id="dept.deptId" name="dept.deptId" value="${dept.deptId}"/>
						<input type="hidden" id="dept.preid" name="dept.preid" value="${dept.preid}"/>
						<input type="hidden" id="dept.fullDid" name="dept.fullDid" value="${dept.fullDid}"/>
						<input type="hidden" id="dept.lel" name="dept.lel" value="${dept.lel}"/>
						<table>
							<tr height="30">
								<td style="text-align:right;">名称：</td><td><input class="validate[required]" id="dept.deptName" type="text" name="dept.deptName" value="${dept.deptName}" /></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr height="30">
				<td>&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td style="text-align:left;">
					<a class="a_button" href="javascript:deptEdit()"><span>编辑</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:deptSave()"><span>添加</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:deptRemove()"><span>删除</span></a>&nbsp;&nbsp;
					<a class="a_button" href="javascript:deptUpdate()"><span>更新</span></a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>