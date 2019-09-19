<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	function checkedItems() {
		var flag = false;
		$("#pageForm").find("[name=items]").each(function() {
			if (this.checked) {
				flag = true;
			}
		});
		return flag;
	}
	
	function deleteAll() {
		if (!checkedItems()) {
			alert("请至少选择要删除服务器。");
			return false;
		}
		msg = "确认要删除所选服务器？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/dmu/dmuEquipment!delete.do");
		}
		$("#pageForm").submit();
	}
	
	function exportList(){
		$("#pageForm").attr("action","${ctx}/dmu/dmuEquipment!exportList.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/dmu/dmuEquipment!findPage.do");
	}
	
	function queryState(id,type,master) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").find("[name=type]").val(type);
		$("#pageForm").find("[name=master]").val(master);
		$("#pageForm").attr("action","${ctx}/dmu/dmuEquipment!queryState.do");
		$("#pageForm").submit();
	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">网管设备 &gt; 设备列表 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/dmu/dmuEquipment!findPage.do" method="post">
		<input type="hidden" name="id">
		<input type="hidden" name="type">
		<input type="hidden" name="master">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">设备ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">设备名称：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>
					<td style="text-align: right">设备ip：</td>
					<td><input type="text" id="pageObject.params.ip" name="pageObject.params.ip" value="${pageObject.params.ip}"></td>
					
					<td style="text-align: right">是否在线：</td>
					<td>
						<select id="pageObject.params.status" name="pageObject.params.status">
							<option value="">全部</option>
							<option value="1" <c:if test="${pageObject.params.status eq '1'}">selected</c:if>>是</option>
							<option value="0" <c:if test="${pageObject.params.status eq '0'}">selected</c:if>>否</option>
						</select>
					</td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="2%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="10%">设备资源ID</th>
						<th width="8%">设备资源名称</th>
						<th width="7%">设备类型</th>
						<th width="7%">设备型号</th>
						<th width="7%">ip</th>
						<th width="7%">端口</th>
						<th width="7%">MAC地址</th>
						<th width="10%">设备物理位置</th>
						<th width="15%">描述</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="dmuEquipment" items="${pageObject.resultList}">
					<tr <c:if test="${dmuEquipment.status =='0'}">class="font-red" </c:if>>
			    	
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${dmuEquipment.id}"/>
				    	</td>
				    	<td>${dmuEquipment.id}</td>
				    	<td>${dmuEquipment.name}</td>
						<td>${dmuEquipment.type}</td>
						<td>${dmuEquipment.version}</td>
				    	<td>${dmuEquipment.ip}</td>
				    	<td>${dmuEquipment.port}</td>
						<td>${dmuEquipment.mac}</td>
				    	<td>${dmuEquipment.pos}</td>
						<td>${dmuEquipment.remark}</td>
				    	<td>
					    	<a href="javascript:queryState('${dmuEquipment.id}','${dmuEquipment.type}','${dmuEquipment.master}')">查看当前状态</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="11">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="11" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return exportList();"><span>导出</span></a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>