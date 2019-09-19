<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/table/table.css"/>
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.scrollLoading.js"></script>
	<script type="text/javascript">
		function openImg(url){
			$("#open_img_div").find("img").attr("src","${ctx}/device/channel!viewSnapshot.do?url="+url);
			$("#open_img_div").dialog("open");
		}
		$(function(){
			$("#pageObject\\.params\\.isNew").change(function(){
				if($(this).val()=="true"){
					$("#pageObject\\.params\\.beginTime").attr("disabled",true);
					$("#pageObject\\.params\\.endTime").attr("disabled",true);
				} else {
					$("#pageObject\\.params\\.beginTime").attr("disabled",false);
					$("#pageObject\\.params\\.endTime").attr("disabled",false);
				}
			});
			$("#open_img_div").dialog({
				title: "摄像头截屏",
				autoOpen: false,
				width : "auto",
				height : "auto",
				modal: true,
				center: true
		});
		})
	</script>
	<title>${global_app_name}</title>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">业务管理 &gt; 通道截屏 </span>
</div>
<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/channel!findPageSnapshot.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">摄像头id：</td>
					<td>
						<input type="text" id="pageObject.params.resId" name="pageObject.params.resId" value="${pageObject.params.resId}">
					</td>
					<td style="text-align: right">最新截屏：</td>
					<td>
						<select id="pageObject.params.isNew" name="pageObject.params.isNew">
							<option value="false" <c:if test="${pageObject.params.isNew eq 'false'}">selected</c:if>>否</option>
							<option value="true" <c:if test="${pageObject.params.isNew eq 'true'}">selected</c:if>>是</option>
						</select>
					</td>
					<td style="text-align: right">上传时间：</td>
					<td>
						<input type="text" id="pageObject.params.beginTime" name="pageObject.params.beginTime" value="${pageObject.params.beginTime}" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate">
						至<input type="text" id="pageObject.params.endTime" name="pageObject.params.endTime" value="${pageObject.params.endTime}" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate">
					</td>
				</tr>
				<tr>
					<td style="text-align: right">备注关键字：</td>
					<td colspan="4">
						<input type="text" id="pageObject.params.remark" name="pageObject.params.remark" value="${pageObject.params.remark}">
					</td>
					<td style="text-align: center"><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tbody>
				<c:forEach var="channel" items="${pageObject.resultList}" varStatus="status">
					<c:if test="${(status.count % 4) == 1}">
						<tr>
					</c:if>
					<td width="25%" style="text-align:left;">
						<img width="100%" src="${ctx}/device/channel!viewSnapshot.do?url=${channel.url}" onclick="openImg('${channel.url}')">
						<br>
						<div style="width: 100%;text-align: center;font-size:large;">${channel.name}(${channel.encoderName})</div>
						<br>
					</td>
					<c:if test="${status.last && ((status.count%4) != 0)}">
						<td colspan="${4-(status.count%4)}">&nbsp;</td>
					</c:if>
					<c:if test="${(status.count % 4) == 0 || status.last}">
						</tr>
					</c:if>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr>
					<td colspan="4">
						<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
					</td>
				</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<div id="open_img_div">
	<img src="${ctx}/device/channel!viewSnapshot.do?url=${channelSnapshot.url}" >
</div>
</body>
</html>