<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${global_app_name}</title>
	<%@ include file="/jsp/common/head/headForm.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/uploadify/uploadify.css" />
	<style type="text/css">
		#impFromFile{
			margin:auto;
		}
	</style>
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.uploadify.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#pageForm").validationEngine();
			$("#impFromFile").uploadify({
				'method'			:'post',
				'height'        	:23,
				'width'        		:78,
				'fileObjName'		:'fileSource',
				'uploader'			:'${ctx}/common/module!beforeEditSn.do;jsessionid='+jsessionid,
				'swf'				:context_path+'/css/uploadify/uploadify.swf',
				'fileTypeDesc'		:'请选择',
				'fileTypeExts'		:'*.txt',
				'buttonText'		:'导入许可',
				'fileSizeLimit'		:'30MB',
				'onFallback'		:function(){
					alert("您未安装FLASH控件，无法从excel导入数据！如需从excel导入数据请先安装FLASH控件。");
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
				'onUploadStart'		: function (file) {
					$("#impFromFile").uploadify("settings", "formData", {'k': $("#k").val()});
				},
				'onUploadSuccess'	:function(file,data,response) {
					if(data != "解析失败！"){
						$("#pageForm").JSONToForm(eval('(' +data + ')'));
					}else {
						//alert(data);
					}
				}
			});
			$(".a_button_blue").button();
			$("#key").change(function(){
				var v = $(this).val();
				if(v){
					$("#k").val(v);
				}
			})
		});
	</script>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">系统管理 &gt; 许可管理 &gt; 编辑许可</span>
</div>
<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/common/module!cteateSn.do" method="post">
		<table class="inputTable" width="100%">
			<tr>
				<td style="text-align:right;">许可key：</td>
				<td>
					<input type="text" name="key" id="key" size="128"/>
					<input type="hidden" name="k" id="k" value="654d9fe1dd2a9ae6ba7315604e4a80f6"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:right;">机器标识：</td>
				<td><input type="text" name="uuid" id="uuid" class="validate[required]" size="128"/></td>
			</tr>
			<tr>
				<td style="text-align:right;">许可时间：</td>
				<td><input type="text" name="endTime" id="endTime" onmousedown="WdatePicker({readOnly:'true',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate validate[required]" maxlength="32"/></td>
			</tr>
			<tr>
				<td style="text-align:right;">许可用户数：</td>
				<td><input type="text" name="userNum" id="userNum" class="validate[required]" size="32"/></td>
			</tr>
			<tr>
				<td style="text-align:right;">许可设备数：</td>
				<td><input type="text" name="deviceNum" id="deviceNum" class="validate[required]" size="32"/></td>
			</tr>
			<tr>
				<td style="text-align:right;vertical-align: bottom">
					<input type="file" id="impFromFile" name="impFromFile">
				</td>
				<td style="text-align:left;vertical-align: middle">
					<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>生成新许可</span></a>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>