<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
 <link rel="stylesheet" href="${ctx}/css/uploadify/uploadify.css" />
<style type="text/css">
	#impFromExcelFile{
		margin:auto;
	}
</style>
<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.uploadify.js"></script>
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

	function deleteAll() {
		if (!checkedItems()) {
			alert("请至少选择要删除通道。");
			return false;
		}
		msg = "确认要删除所选通道？";
		if (window.confirm(msg)) {
			$("#pageForm").attr("action","${ctx}/device/channel!delete.do");
		}
		$("#pageForm").submit();
	}

	function updateAllPlan() {
		if (!checkedItems()) {
			alert("请至少选择要修改的通道。");
			return false;
		}
		$("#pageForm").attr("action","${ctx}/device/channel!beforeUpdatePlan.do");
		$("#pageForm").submit();
	}

	function exportList(){
		$("#pageForm").attr("action","${ctx}/device/channel!exportList.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/device/channel!findPage.do");
	}

	function editChannel(id) {
		$("#pageForm").find("[name=id]").val(id);
		$("#pageForm").attr("action","${ctx}/device/channel!beforeUpdate.do");
		$("#pageForm").submit();
	}
	function openExcelDiv() {
		if (isSetFlash){
			$('#excelDiv').dialog('open');
		} else {
			alert("您未安装FLASH控件，无法从excel导入数据！如需从excel导入数据请先安装FLASH控件。");
		}
	}
	$(function(){
		$("#excelDiv").dialog({
			title:'从excel导入批量修改通道名',
			autoOpen: false
		});
		$("#impFromExcelFile").uploadify({
			'height'        	:42,
			'width'        		:142,
			'fileObjName'		:'fileSource',
			'uploader'			:'${ctx}/device/channel!impFromExcel.do;jsessionid='+jsessionid,
			'swf'				:context_path+'/css/uploadify/uploadify.swf',
			'fileTypeDesc'		:'请选择',
			'fileTypeExts'		:'*.xls',
			'buttonText'		:'导入数据',
			'fileSizeLimit'		:'30MB',
			'onFallback'		:function(){
				isSetFlash = false;
			},
			'onSelectError'		:function(file, errorCode, errorMsg){
				switch(errorCode) {
					case -100:
						alert("上传的文件数量已经超出系统限制的" + o.uploadify('settings','queueSizeLimit') + "个文件！");
						break;
					case -110:
						alert("附件： ["+file.name+"] 大小超出系统限制的" + o.uploadify('settings','fileSizeLimit') + "大小！");
						break;
					case -120:
						alert("附件： ["+file.name+"] 大小异常！");
						break;
					case -130:
						alert("附件： ["+file.name+"] 类型不正确！");
						break;
				}
			},
			'onSelect'			:function(){
			},
			'onUploadSuccess'	:function(file,data,response) {
				if(data == "ok"){
					alert(file.name + " 导入成功！",function(){
						$("#pageForm").submit();
					});
				}else
					alert(data);
			}
		});
		$(".a_button_blue").button();
	});
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 摄像机通道管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/channel!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">通道ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">通道名称：</td>
					<td><input type="text" id="pageObject.params.channelName" name="pageObject.params.channelName" value="${pageObject.params.channelName}"></td>
					<td style="text-align: right">编码器名称：</td>
					<td><input type="text" id="pageObject.params.encodeName" name="pageObject.params.encodeName" value="${pageObject.params.encodeName}"></td>
					<td></td>

				</tr>
				<tr>
					<td style="text-align: right">存储服务器：</td>
					<td>
						<select id="pageObject.params.msuId" name="pageObject.params.msuId">
							<option value="">全部</option>
							<c:forEach var="msu" items="${msus}">
								<option value="${msu.key}" <c:if test="${pageObject.params.msuId eq msu.key}">selected</c:if>>${msu.value}</option>
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
					<td style="text-align: right">接入服务器：</td>
					<td>
						<select id="pageObject.params.mduId" name="pageObject.params.mduId">
							<option value="">全部</option>
							<c:forEach var="mdu" items="${mdus}">
								<option value="${mdu.key}" <c:if test="${pageObject.params.mduId eq mdu.key}">selected</c:if>>${mdu.value}</option>
							</c:forEach>
						</select>
					</td>
					<td></td>
				</tr>
				<tr>

					<td style="text-align: right">是否有云台：</td>
					<td>
						<select id="pageObject.params.hasPtz" name="pageObject.params.hasPtz">
							<option value="">全部</option>
							<option value="true" <c:if test="${pageObject.params.hasPtz eq 'true'}">selected</c:if>>是</option>
							<option value="false" <c:if test="${pageObject.params.hasPtz eq 'false'}">selected</c:if>>否</option>
						</select>
					</td>
					<td style="text-align: right">是否有音频：</td>
					<td>
						<select id="pageObject.params.hasAudio" name="pageObject.params.hasAudio">
							<option value="">全部</option>
							<option value="true" <c:if test="${pageObject.params.hasAudio eq 'true'}">selected</c:if>>是</option>
							<option value="false" <c:if test="${pageObject.params.hasAudio eq 'false'}">selected</c:if>>否</option>
						</select>
					</td>

					<td></td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="channlePageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
				<tr>
					<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
					<th width="9%"><div>通道ID</div></th>
					<th width="16%">通道名称</th>
					<th width="12%">编码器名称</th>
					<th width="5%">位置</th>
					<th width="3%">通道</th>
					<th width="8%">存储服务器</th>
					<th width="6%">存储方案</th>
					<th width="4%">云台启用</th>
					<th width="5%">音视频启用</th>
					<th width="5%">流媒体个数</th>
					<th width="5%">启用</th><th width="19%">描述</th>
					<th width="2%">操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="channel" items="${pageObject.resultList}">
					<tr>
						<td class="center">
							<input type="checkbox" name="items" class="items" value="${channel.id}"/>
						</td>
						<td>${channel.id}</td>
						<td>${channel.name}</td>
						<td>${channel.encoderName}</td>
						<td>${channel.positionZH}</td>
						<td>${channel.num+1}</td>
						<td>${channel.serverName}</td>
						<td>${channel.recordPlanName}</td>
						<td>${channel.hasPtzZh}</td>
						<td>${channel.hasAudioZh}</td>
						<td>${channel.streamCount}</td>
						<td>${channel.enabledZh}</td>
						<td>${channel.description}</td>
						<td>
							<a href="javascript:editChannel('${channel.id}')">编辑</a>
						</td>
					</tr>
				</c:forEach>

				</tbody>
				<tfoot>
				<tr>
					<td colspan="15">
						<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
					</td>
				</tr>
				<tr>
					<td colspan="15" style="text-align: center;vertical-align: top;height: 35px">
						<a class="a_button" href="javascript:void(0);" onclick="return updateAllPlan();"><span>存储方案</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0);" onclick="return exportList();"><span>导出</span></a>
						<a  class="a_button" href="javascript:void(0);" onclick="openExcelDiv();"><span>从Excel导入</span></a>
					</td>
				</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
	<div id="excelDiv" style="width: 300px;display: none;">
		<table width="100%">
			<tr>
				<td style="text-align: center;vertical-align: middle;">
					<input type="file" id="impFromExcelFile" name="impFromExcelFile"></td>
			</tr>
		</table>
	</div>
</body>
</html>