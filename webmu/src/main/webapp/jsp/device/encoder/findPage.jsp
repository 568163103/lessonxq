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

	function openExcelDiv() {
		if (isSetFlash){
			$('#excelDiv').dialog('open');
		} else {
			alert("您未安装FLASH控件，无法从excel导入数据！如需从excel导入数据请先安装FLASH控件。");
		}
	}
	function openExcelDiv2() {
		if (isSetFlash){
			$('#excelDiv2').dialog('open');
		} else {
			alert("您未安装FLASH控件，无法从excel导入数据！如需从excel导入数据请先安装FLASH控件。");
		}
	}
	$(function(){
		$("#excelDiv").dialog({
			title:'从excel导入单位初始化数据',
			autoOpen: false
		});
		$("#excelDiv2").dialog({
			title:'从excel导入批量修改编码器名称',
			autoOpen: false
		});
		$("#impFromExcelFile").uploadify({
			'height'        	:31,
			'width'        		:102,
			'fileObjName'		:'fileSource',
			'uploader'			:'${ctx}/device/encoder!impFromExcel.do;jsessionid='+jsessionid,
			'swf'				:context_path+'/css/uploadify/uploadify.swf',
			'fileTypeDesc'		:'请选择',
			'fileTypeExts'		:'*.xlsx',
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

		$("#impFromExcelFile2").uploadify({
			'height'        	:32,
			'width'        		:102,
			'fileObjName'		:'fileSource',
			'uploader'			:'${ctx}/device/encoder!impFromExcelToUpdate.do;jsessionid='+jsessionid,
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
		<span class="win-header-title">设备管理 &gt; 编码器管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/encoder!findPage.do" method="post">
		<input type="hidden" name="id">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">编码器ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">编码器名称：</td>
					<td><input type="text" id="pageObject.params.name" name="pageObject.params.name" value="${pageObject.params.name}"></td>
					<td style="text-align: right">ip：</td>
					<td><input type="text" id="pageObject.params.ip" name="pageObject.params.ip" value="${pageObject.params.ip}"></td>
					<td style="text-align: right">接入服务器：</td>
					<td>
						<select id="pageObject.params.mduId" name="pageObject.params.mduId">
							<option value="">全部</option>
							<c:forEach var="mdu" items="${mdus}">
								<option value="${mdu.key}" <c:if test="${pageObject.params.mduId eq mdu.key}">selected</c:if>>${mdu.value}</option>
							</c:forEach>
						</select>
					</td>
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
					<td style="text-align: right">型号：</td>
					<td>
						<select name="pageObject.params.model" id="pageObject.params.model">
							<option value="">全部</option>
							<c:forEach var="encoderModel" items="${encoderModels}">
								<option value="${encoderModel.key}" <c:if test="${pageObject.params.model eq encoderModel.key}">selected</c:if>>${encoderModel.value}</option>
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
						<th width="4%">位置</th>
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
				<c:forEach var="encoder" items="${resultList}">
			    	<tr <c:if test="${!encoder.status}">class="font-red" </c:if>>
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${encoder.id}"/>
				    	</td>
				    	<td><c:if test="${encoder.model=='platform'}">${fn:substring(encoder.id, 0, 6)}2008${fn:substring(encoder.id, 10, 16)}</c:if>
							<c:if test="${encoder.model!='platform'}">${encoder.id}</c:if>
						</td>
				    	<td>${encoder.name}</td>
						<td>${encoder.positionZH}</td>
						<td>${encoder.modelName}</td>
						<td>
						<c:if test="${not empty encoder.ip}">
							<a target="_blank" href="http://${ encoder.ip}"><span>${ encoder.ip}</span></a>
						</c:if>
						</td>
				    	<td>${encoder.port}</td>
				    	<td>${encoder.username}</td>
						<td>${encoder.password}</td>
						<td>${encoder.serverName}</td>
						<td>${encoder.channelCount}</td>
				    	<td>${encoder.inputCount}</td>
						<td>${encoder.outputCount}</td>
						<td>${encoder.address}</td>
						<td>${encoder.enabledZh}</td>
						<td>${encoder.statusZh}</td>
						<td title="${encoder.description}">
							<c:if test="${fn:length(encoder.description)>12}">${fn:substring(encoder.description, 0, 12)}...</c:if>
							<c:if test="${fn:length(encoder.description)<=18}">${encoder.description}</c:if>
						</td>
				    	<td>
					    	<a href="javascript:editEncoder('${encoder.id}')">编辑</a>
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
							<a class="a_button" href="javascript:location.href='${ctx}/device/encoder!beforeSave.do'"><span>添加</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return deleteSelect();"><span>删除</span></a>&nbsp;&nbsp;
						<!--	<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>清空初始化数据</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:location.href='${ctx}/device/encoder!beforeInitUserResourceTree.do'"><span>从外部系统导入</span></a>&nbsp;&nbsp;     -->
							<a class="a_button" href="javascript:void(0);" onclick="return exportList();"><span>导出</span></a>
							<a  class="a_button" href="javascript:void(0);" onclick="openExcelDiv2();"><span>批量修改</span></a>
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
			<tr style="height: 100px">
				<td style="text-align: center;vertical-align: middle;">
					<a class="a_button" href="javascript:location.href='${ctx}/device/encoder!downExcelTempl.do'"><span>下载模板</span></a></td>
			</tr>
			<tr >
				<td style="text-align: center;vertical-align: middle;">
					<input type="file" id="impFromExcelFile" name="impFromExcelFile"></td>
			</tr>
		</table>
	</div>
	<div id="excelDiv2" style="width: 300px;display: none;">
		<table width="100%">
			<tr >
				<td style="text-align: center;vertical-align: middle;">
					<input type="file" id="impFromExcelFile2" name="impFromExcelFile2"></td>
			</tr>
		</table>
	</div>
</body>
</html>