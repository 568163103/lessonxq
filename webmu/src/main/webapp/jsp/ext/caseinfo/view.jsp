<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
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
		<span class="win-header-title">数据维护 &gt; 案件管理 &gt; 查看案件</span>
	</div>
	<div class="win-bodyer">
		<fieldset style="width: 98%">
			<legend>  案件基本信息  </legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right;" width="8%">案件id：</td>
					<td width="17%">${caseinfo.cid}</td>
					<td style="text-align: right;" width="8%">案件名称：</td>
					<td width="17%">${caseinfo.name}</td>
					<td style="text-align: right;" width="8%">案件类型：</td>
					<td width="17%">${caseinfo.caseTypeName}</td>
					<td style="text-align: right;" width="8%">案件状态：</td>
					<td width="17%">${caseinfo.detectStateZh}</td>
				</tr>
				<tr>
					<td style="text-align: right;" width="8%">案件编号：</td>
					<td width="17%">${caseinfo.codenum}</td>
					<td style="text-align: right;" width="8%">创建者：</td>
					<td width="17%">${caseinfo.creator}</td>
					<td style="text-align: right;" width="8%">创建时间：</td>
					<td width="17%">${caseinfo.name}</td>
					<td style="text-align: right;" width="8%">案件发生地：</td>
					<td width="17%">${caseinfo.happeningplace}</td>
				</tr>
				<tr>
					<td style="text-align: right;" width="8%">描述：</td>
					<td colspan="7">${caseinfo.happeningplace}</td>
				</tr>
			</table>
		</fieldset>
		<br><br>
		<fieldset style="width: 98%">
			<legend>  涉案人  </legend>
			<table width="100%" class="inputTable">
				<c:forEach var="caseinformant" items="${caseinformants}">
					<tr>
						<td style="text-align: right;" width="4%">姓名：</td>
						<td width="17%">${caseinformant.name}</td>
						<td style="text-align: right;" width="4%">性别：</td>
						<td width="7%">${caseinformant.genderZh}</td>
						<td style="text-align: right;" width="8%">生日：</td>
						<td width="17%">${caseinformant.birthdayStr}</td>
						<td style="text-align: right;" width="8%">电话：</td>
						<td width="10%">${caseinformant.tel}</td>
						<td style="text-align: right;" width="8%">籍贯：</td>
						<td width="17%">${caseinformant.nativeplace}</td>
					</tr>
				</c:forEach>
			</table>
		</fieldset>
		<br><br>
		<fieldset style="width: 98%">
			<legend>  案件视频  </legend>
			<table width="100%" class="inputTable">
				<c:forEach var="casevideofile" items="${casevideofiles}">
					<tr>
						<td style="text-align: right;" width="8%">录制开始时间：</td>
						<td width="17%">${casevideofile.filebegintimeStr}</td>
						<td style="text-align: right;" width="8%">录制结束时间：</td>
						<td width="17%">${casevideofile.fileendtimeStr}</td>
						<td style="text-align: right;" width="50%">
							<a class="a_button" href="javascript:openLiveVideo('${casevideofile.filepath}');"><span>播放</span></a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="a_button" href="${ctx}/common/practical!download.do?url=${casevideofile.filepath}"><span>下载</span></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</fieldset>
		<table width="100%" class="inputTable">
			<tr>
				<td colspan="8" style="text-align: center;">
					<a class="a_button" href="${ctx}/ext/caseinfo!findPage.do"><span>返回</span></a></td>
			</tr>
		</table>
	</div>
	<div id="live-div">
		<iframe id="playerIframe" width="100%" frameBorder="0" scrolling ="auto" height="100%" allowtransparency=yes src=""></iframe>
	</div>
</body>
</html>