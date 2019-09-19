<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.scrollLoading.js"></script>
	<title>${global_app_name}</title>
	<script type="text/javascript">
		String.prototype.replaceAll = stringReplaceAll;
		function stringReplaceAll(AFindText,ARepText){
			raRegExp = new RegExp(AFindText,"g");
			return this.replace(raRegExp,ARepText)
		}
		function openLiveVideo(playUrl){
			isOpen = true;playUrl="rtmp://"+window.location.hostname+"/lskjapp/"+playUrl.replaceAll("/","___");
			$("#playerIframe").attr("src","${ctx}/jsp/bussiness/display/player.jsp?playUrl="+encodeURIComponent(playUrl)+"&noTitle=true");
		}
		$(function(){
			$("#live-div").dialog({
				title: "查看视信息",
				autoOpen: false,
				width : 1100,
				height : 650,
				modal: true,
				close: function(event, ui) {
					if(window.playerIframe.playerInstance){window.playerIframe.playerInstance.stop();}
				}
			});
			$("#playerIframe").load(function(){
				if(isOpen){
					$("#live-div").dialog("open");
				}
			});
		});
	</script>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">刑侦管理 &gt; 视频管理 </span>
</div>
<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/ext/caseinfo!findPageVideo.do" method="post">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">起止时间：</td>
					<td>
						<input type="text" id="pageObject.params.beginTime" name="pageObject.params.beginTime" value="${pageObject.params.beginTime}" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd'})" class="Wdate">
						至<input type="text" id="pageObject.params.endTime" name="pageObject.params.endTime" value="${pageObject.params.endTime}" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd'})" class="Wdate">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tbody>
				<c:forEach var="casevideofile" items="${pageObject.resultList}" varStatus="status">
					<c:if test="${(status.count % 4) == 1}">
						<tr>
					</c:if>
					<td width="25%" style="text-align:left;padding-right: 1px;">
						<img width="100%" src="${ctx}/images/caseinfo_default_video.jpg" onclick="javascript:openLiveVideo('${casevideofile.filepath}');">
						<br>
						<div style="width: 100%;text-align: center;font-size:large;">${casevideofile.fileName}</div>
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
<div id="live-div">
	<iframe id="playerIframe" width="100%" frameBorder="0" scrolling ="auto" height="100%" allowtransparency=yes src=""></iframe>
</div>
</body>
</html>