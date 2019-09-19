<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>

<title>${global_app_name}</title>
<script type="text/javascript">
	var isSetFlash = true;
	function checkedItems() {
		var flag = false;
		$("#pageForm").find("[name=items]").each(function() {
			if (this.checked) {
				flag = true;
			}
		});
		return flag;
	}

	function deleteSelect() {
		if (!checkedItems()) {
			alert("请选择要删除编码器。");
			return false;
		}
		msg = "确认要删除所选编码器？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/device/encoder!delete.do");
			$("#pageForm").submit();
		}
	}

	function findSelect(flag) {
		var i=0;
		$("#pageForm").find("[name=items]").each(function() {
			if (this.checked) {
				i++;
			}
		});
		if (i>1) {
			alert("不能选择多个查看。");
			return false;
		}else if(i==0){
			alert("请选择要查看的编码器。");
			return false;
		}

		if (flag=='1') {
			$("#pageForm").attr("action","${ctx}/device/channel!findExternalPage.do");
			$("#pageForm").submit();
		}else if (flag=='2') {
			$("#pageForm").attr("action","${ctx}/security/user!findExternalPage.do");
			$("#pageForm").submit();
		}else if (flag=='3') {
			$("#pageForm").attr("action","${ctx}/bussiness/usertree!beforeSubExternalAlarmRes.do");
			$("#pageForm").submit();
		}else if (flag=='4') {
			$("#pageForm").attr("action","${ctx}/external/shieldplan!findPage.do");
			$("#pageForm").submit();
		}else if (flag=='5') {
			$("#pageForm").attr("action","${ctx}/external/channelDistribute!findPage.do");
			$("#pageForm").submit();
		}
	}

	function deleteAll() {
		var msg = "确认清空初始化数据？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/device/encoder!deleteInitUserTree.do");
			$("#pageForm").submit();
		}
	}

	function exportList(){
		$("#pageForm").attr("action","${ctx}/device/encoder!exportList.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/device/encoder!findPage.do");
	}

	function editEncoder(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/device/encoder!beforeUpdate.do");
		$("#pageForm").submit();
	}


</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">外域设备管理 &gt; 接入网关 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/encoder!findPlatformPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">编码器ID：</td>
					<td>
						<select id="pageObject.params.id" name="pageObject.params.id">
							<option value="">全部</option>
							<c:forEach var="mdu" items="${platforms}">
								<option value="${mdu.key}" <c:if test="${pageObject.params.id eq mdu.key}">selected</c:if>>${mdu.key}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right">编码器名称：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>
					<td style="text-align: right">ip：</td>
					<td><input type="text" id="pageObject.params.ip" name="pageObject.params.ip" value="${pageObject.params.ip}"></td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">接入服务器：</td>
					<td>
						<select id="pageObject.params.mduId" name="pageObject.params.mduId">
							<option value="">全部</option>
							<c:forEach var="mdu" items="${mdus}">
								<option value="${mdu.key}" <c:if test="${pageObject.params.mduId eq mdu.key}">selected</c:if>>${mdu.value}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right">位置：</td>
					<td>
						<select name="pageObject.params.position" id="pageObject.params.position">
							<option value="">全部</option>
							<c:forEach var="type" items="${positions}">
								<option value="${type.key}" <c:if test="${pageObject.params.position eq type.key}">selected</c:if>>${type.value}</option>
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
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="9%"><div>ID</div></th>
						<th width="14%">名称</th>
						<th width="6%">位置</th>
						<th width="4%">型号</th>
						<th width="8%">ip</th>
						<th width="3%">端口</th>
						<th width="8%">用户名</th>
						<th width="5%">密码</th>
						<th width="7%">接入服务器</th>
						<th width="5%">视频通道数</th>
						<th width="5%">输入通道数</th>
						<th width="5%">输出通道数</th>
						<th width="9%">地址</th>
						<th width="2%">启用</th>
						<th width="3%">在线</th>
						<th width="8%">描述</th>
						<th width="2%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="encoder" items="${pageObject.resultList}">
			    	<tr <c:if test="${!encoder[0].status}">class="font-red" </c:if>>
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${encoder[0].id}"/>
				    	</td>
				    	<td><c:if test="${encoder[0].model=='platform'}">${fn:substring(encoder[0].id, 0, 6)}2008${fn:substring(encoder[0].id, 10, 16)}</c:if>
							<c:if test="${encoder[0].model!='platform'}">${encoder[0].id}</c:if>
						</td>
				    	<td>${encoder[0].name}</td>
						<td>${encoder[0].positionZH}</td>
						<td>${encoder[0].modelName}</td>
						<td>${encoder[0].ip}</td>
				    	<td>${encoder[0].port}</td>
				    	<td>${encoder[0].username}</td>
						<td>${encoder[0].password}</td>
						<td>${encoder[1].serverName}</td>
						<td>${encoder[0].channelCount}</td>
				    	<td>${encoder[0].inputCount}</td>
						<td>${encoder[0].outputCount}</td>
						<td>${encoder[0].address}</td>
						<td>${encoder[0].enabledZh}</td>
						<td>${encoder[0].statusZh}</td>
						<td title="${encoder[0].description}">
							<c:if test="${fn:length(encoder[0].description)>12}">${fn:substring(encoder[0].description, 0, 12)}...</c:if>
							<c:if test="${fn:length(encoder[0].description)<=18}">${encoder[0].description}</c:if>
						</td>
				    	<td>
					    	<a href="javascript:editEncoder('${encoder[0].id}')">编辑</a>
				    	</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="18">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="18" style="text-align: center;vertical-align: top;height: 35px;border-right: 0px solid #cfcfcf;">
							<a class="a_button" href="javascript:void(0);" onclick="return findSelect('1');"><span>目录资源查看</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return findSelect('2');"><span>用户资源查看</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return findSelect('3');"><span>告警资源订阅</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return findSelect('4');"><span>资源屏蔽计划</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return findSelect('5');"><span>资源下发</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return deleteSelect();"><span>删除</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return exportList();"><span>导出</span></a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
	<div id="excelDiv" style="width: 300px;display: none;">
		<table width="100%">
			<tr style="height: 100px">
				<td style="text-align: center;vertical-align: middle;">
					<a class="a_button" href="javascript:location.href='${ctx}/device/encoder!downExcelTempl.do'"><span>下载模板</span></a></td>
			</tr>
			<tr>
				<td style="text-align: center;vertical-align: middle;">
					<input type="file" id="impFromExcelFile" name="impFromExcelFile"></td>
			</tr>
		</table>
	</div>
</body>
</html>