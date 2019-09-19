<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headFormPage.jsp"%>
	<title>${global_app_name}</title>
	<script type="application/javascript">
		var isOpen = false;
		function getReqResUser(serverId,vodId,type,resId){
			isOpen = true;
			if(type != 0){
				resId = vodId;
			}
			$("#main-iframe").attr("src","${ctx}/bussiness/devicePerformance!getReqResUser.do?serverId="+serverId+"&resId="+resId);
		}
		$(function(){
			$("#reqResUser").dialog({
				title: "用户信息",
				autoOpen: false,
				width : 1100,
				height : 650,
				modal: true
			});
			$("#main-iframe").load(function(){
				if(isOpen){
					$("#reqResUser").dialog("open");
				}
			});
		})
	</script>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">性能管理 &gt; 请求资源 </span>
</div>
</div>
<div class="win-bodyer">
	<form id="pageForm" name="pageForm" method="post">
		<div id="pageDiv" name="pageDiv" class="pageDiv">
            <div style="width: 100%; text-align: center; margin-bottom: 10px;font-weight: bold;">
                <font size="5">
                                                   总记录  <font size="6" color="#FF0000">${ totalCount }</font> 条，
                                                   其中正常记录 <font size="6" color="#FF0000">${ normalCount }</font> 条， 
                                                   异常记录  <font size="6" color="#FF0000">${ invalidCount }</font> 条 
                </font>
            </div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
				<tr>
					<th width="8%">资源ID</th>
					<th width="30%">资源名称</th>
					<th width="5%">视频类型</th>
					<th width="8%">开始时间</th>
					<th width="8%">结束时间</th>
					<th width="5%">操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="reqServerRes" items="${reqServerResDto.itemList}">
					<tr <c:if test="${!reqServerRes.valid}">class="font-red"</c:if> >
						<td>${reqServerRes.id}</td>
						<td>${reqServerRes.name}</td>
						<td>${reqServerRes.typeName}</td>
						<td>${reqServerRes.beginTime}</td>
						<td>${reqServerRes.endTime}</td>
						<td>
							<a href="javascript:getReqResUser('${reqServerResDto.serverId}','${reqServerRes.vodId}','${reqServerRes.type}','${reqServerRes.id}')">查看浏览用户</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
</div>
<div id="reqResUser">
	<iframe id="main-iframe" width="100%" frameBorder="0" scrolling ="auto" height="98%"
			allowtransparency=yes src=""></iframe>
</div>
</body>
</html>