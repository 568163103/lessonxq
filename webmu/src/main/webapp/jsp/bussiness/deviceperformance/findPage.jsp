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
	
	function viewServer(id,status) {
		if (status=='false') {
			alert("服务器离线");
			return false;
		}
		$("#pageForm").find("[name=serverId]").val(id);
		$("#pageForm").attr("action","${ctx}/bussiness/devicePerformance!viewServerStatus.do");
		$("#pageForm").submit();
	}
	function operateServer(id,status) {
		if (status=='false') {
			alert("服务器离线");
			return false;
		}
		$("#pageForm").find("[name=serverId]").val(id);
		$("#pageForm").attr("action","${ctx}/bussiness/devicePerformance!operateServer.do");
		$("#pageForm").submit();
	}
	function getReqServerRes(id,status) {
		if (status=='false') {
			alert("服务器离线");
			return false;
		}
		$("#pageForm").find("[name=serverId]").val(id);
		$("#pageForm").attr("action","${ctx}/bussiness/devicePerformance!getReqServerRes.do");
		$("#pageForm").submit();
	}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">性能管理 &gt; 性能监视 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/devicePerformance!findPage.do" method="post">
		<input type="hidden" name="serverId">
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
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">用户名：</td>
					<td><input type="text" id="pageObject.params.username" name="pageObject.params.username" value="${pageObject.params.username}"></td>
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
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="2%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="10%">服务器ID</th>
						<th width="15%">服务器名称</th>
						<th width="5%">服务器类别</th>
						<th width="7%">ip</th>
						<th width="3%">端口</th>
						<th width="5%">用户名</th>
						<th width="5%">密码</th>
						<th width="12%">上级cmu</th>
						<th width="5%">最大连接数</th>
						<th width="10%">物理位置</th>
						<th width="3%">在线</th>
						<th width="18%">操作</th>
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
						<td>${server.typeName}</td>
						<td>${server.ip}</td>
				    	<td>${server.port}</td>
				    	<td>${server.username}</td>
						<td>${server.password}</td>
						<td>${server.cmuName}</td>
				    	<td>${server.maxConnection}</td>
						<td>${server.address}</td>
						<td>${server.statusZh}</td>
				    	<td>
					    	<a href="javascript:viewServer('${server.id}','${server.status}')">查看性能</a>&nbsp;&nbsp;&nbsp;
							<a href="javascript:getReqServerRes('${server.id}','${server.status}')">查看使用资源</a>&nbsp;&nbsp;&nbsp;
							<a href="javascript:operateServer('${server.id}','${server.status}')">重启服务</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="13">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>