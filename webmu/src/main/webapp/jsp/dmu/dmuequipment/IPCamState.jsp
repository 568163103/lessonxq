<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormPage.jsp"%>
<script type="text/javascript">
	function exportList(id,type){
		$("#pageForm").attr("action","${ctx}/dmu/dmuEquipment!exportList.do?id="+id+"&type="+type);
		$("#pageForm").submit();
	}
</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">网管设备 &gt; 设备状态 &gt; IP摄像机</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm"  method="post">
			<input type="hidden" id="id" name="id" value="${encoderStateRecord.encoderid}">
			<table width="90%" class="inputTable">
				<tr>
					<td style="text-align: right;">设备资源ID：</td>
					<td><input type="text" value="${encoderStateRecord.encoderid}" class="validate[required]" name="encoderStateRecord.encoderid" id="encoderid" readonly="true"/></td>
					<td style="text-align: right;">视频编码方式：</td>
					<td><input type="text" value="${encoderStateRecord.codeType}" class="validate[required]" name="encoderStateRecord.codeType" id="codeType" readonly="true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">当前码流类型：</td>
					<td><input type="text" value="${encoderStateRecord.streamType}" class="validate[required]" name="encoderStateRecord.streamType" id="streamType" readonly="true"/></td>
					<td style="text-align: right;">主码流码率类型：</td>
					<td><input type="text" value="${encoderStateRecord.mainStreamRateType}" class="validate[required]" name="encoderStateRecord.mainStreamRateType" id="mainStreamRateType" readonly="true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">主码流分辨率：</td>
					<td><input type="text" value="${encoderStateRecord.mainStreamResolution}" class="validate[required]" name="encoderStateRecord.mainStreamResolution" id="mainStreamResolution" readonly="true"/></td>
					<td style="text-align: right;">主码流帧率：</td>
					<td><input type="text" value="${encoderStateRecord.mainStreamFrameRate}" class="validate[required]" name="encoderStateRecord.mainStreamFrameRate" id="mainStreamFrameRate" readonly="true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">主码流GOP：</td>
					<td><input type="text" value="${encoderStateRecord.mainStreamGOP}" class="validate[required]" name="encoderStateRecord.mainStreamGOP" id="mainStreamGOP" readonly="true"/></td>
					<td style="text-align: right;">子码流分辨率：</td>
					<td><input type="text" value="${encoderStateRecord.childStreamResolution}" class="validate[required]" name="encoderStateRecord.childStreamResolution" id="childStreamResolution" readonly="true"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">状态记录时间：</td>
					<td><input type="text" value="${encoderStateRecord.recordTime}" class="validate[required]" name="encoderStateRecord.recordTime" id="recordTime" readonly="true"/></td>
					<td </td>
					<td></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0);" onclick="return exportList('${encoderStateRecord.encoderid}','2');"><span>导出</span></a>
						<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
		
	</div>
</body>
</html>