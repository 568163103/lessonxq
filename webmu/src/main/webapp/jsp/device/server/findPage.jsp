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
			$("#pageForm").attr("action","${ctx}/device/server!delete.do");
		}
		$("#pageForm").submit();
	}

	function exportList(){
		$("#pageForm").attr("action","${ctx}/device/server!exportList.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/device/server!findPage.do");
	}

	function editServer(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/device/server!beforeUpdate.do");
		$("#pageForm").submit();
	}
    function prohibited(id,proStatus){
		$('#server_id').val(id);
		if (proStatus == 1){
			proStatus = 0;
		}else if(proStatus == 0){
			proStatus = 1;
		}
		$('#server_prohibited').val(proStatus);
    var url = "${ctx}/device/server!getStatus.do?serverId="+id+'&status='+proStatus;
		$.get(url,function (result) {});
		$("#pageForm").attr("action","${ctx}/device/server!updateProStatus.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/device/server!findPage.do");

	}

</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 服务器管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/server!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">服务器ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">服务器名称：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>
					<td style="text-align: right">服务器ip：</td>
					<td><input type="text" id="pageObject.params.ip" name="pageObject.params.ip" value="${pageObject.params.ip}"></td>
					<td style="text-align: right">用户名：</td>
					<td><input type="text" id="pageObject.params.username" name="pageObject.params.username" value="${pageObject.params.username}"></td>
				</tr>
				<tr>
					<td style="text-align: right">位置：</td>
					<td>
						<select name="pageObject.params.position" id="pageObject.params.position">
							<option value="">全部</option>
							<c:forEach var="type" items="${positions}">
								<option value="${type.key}" <c:if test="${pageObject.params.position eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right">服务器类型：</td>
					<td>
						<select name="pageObject.params.type" id="pageObject.params.type">
							<option value="">全部</option>
							<c:forEach var="type" items="${types}">
								<option value="${type.key}" <c:if test="${pageObject.params.type eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right">是否在线：</td>
					<td>
						<select id="pageObject.params.status" name="pageObject.params.status">
							<option value="">全部</option>
							<option value="true" <c:if test="${pageObject.params.status eq 'true'}">selected</c:if>>是</option>
							<option value="false" <c:if test="${pageObject.params.status eq 'false'}">selected</c:if>>否</option>
						</select>
					</td>
					<td></td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="2%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="9%">服务器ID</th>
						<th width="14%">服务器名称</th>
						<th width="5%">位置</th>
						<th width="5%">服务器类别</th>
						<th width="7%">ip</th>
						<th width="3%">端口</th>
						<th width="5%">用户名</th>
						<th width="5%">密码</th>
						<th width="5%">最大连接数</th>
						<th width="10%">物理位置</th>
						<th width="3%">在线</th>
						<th width="15%">描述</th>
						<th width="7%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="server" items="${pageObject.resultList}">
			    	<tr <c:if test="${!server.status}">class="font-red" </c:if>>
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${server.id}"/>
				    	</td>
				    	<td>${server.id}</td>
				    	<td>${server.name}</td>
						<td>${server.positionZH}</td>
						<td>${server.typeName}</td>
						<td>${server.ip}</td>
				    	<td>${server.port}</td>
				    	<td>${server.username}</td>
						<td>${server.password}</td>
				    	<td>${server.maxConnection}</td>
						<td>${server.address}</td>
						<td>${server.statusZh}</td>
						<td>${server.description}</td>
				    	<td>
							<a href="javascript:editServer('${server.id}')">编辑</a>
							<c:if test="${server.prohibited_status ==1}">
							<a id="prohibited${server.id}" href="#" onclick="prohibited('${server.id}','${server.prohibited_status}')">禁止外设</a>

							</c:if>
							<c:if test="${server.prohibited_status ==0}">
							<a id="prohibited${server.id}"  href="#" onclick="prohibited('${server.id}','${server.prohibited_status}')">取消禁止</a>
							</c:if>
						</td>
			    	</tr>
				</c:forEach>
				<input type="hidden" id="server_id" name="serverId" value="">
				<input type="hidden" id="server_prohibited"  name="server.prohibited_status" value="">
				</tbody>
				<tfoot>
					<tr>
						<td colspan="15">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="15" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:location.href='${ctx}/device/server!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
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