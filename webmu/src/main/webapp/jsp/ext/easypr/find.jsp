<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/uploadify/uploadify.css" />
	<style type="text/css">
		#easyPrFile{
			margin:auto;
		}
	</style>
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.uploadify.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#easyPrFile").uploadify({
				'height'        	:27,
				'width'        		:78,
				'fileObjName'		:'fileSource',
				'uploader'			:'${ctx}/thirdparty/formInterface.do;jsessionid='+jsessionid+"?cmd=130101",
				'swf'				:context_path+'/css/uploadify/uploadify.swf',
				'fileTypeDesc'		:'请选择',
				'fileTypeExts'		:'*.*',
				'buttonText'		:'车牌识别',
				'fileSizeLimit'		:'30MB',
				'onFallback'		:function(){
					alert("您未安装FLASH控件，无法导入！如需导入数据请先安装FLASH控件。");
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
				'onUploadSuccess'	:function(file,result,response) {
					data = eval('(' +result + ')');
					if(data.message=="success"){
						data = data.data;
						$("#uploadImage").attr("src","${ctx}/common/practical!download.do?url="+data.split("---")[0]);
						$("#carNum").html(data.split("---")[1]);
					} else {
						alert(data.message);
					}
				}
			});
			$(".a_button_blue").button();
		});
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">数据管理 &gt; 车牌识别 </span>
</div>
<div class="win-bodyer">
	<table width="100%">
		<tr style="height: 100px">
			<td style="text-align: center;vertical-align: middle;" colspan="2">
				<input type="file" id="easyPrFile" name="easyPrFile">
			</td>
		</tr>
		<tr style="height: 100px">
			<td style="text-align: right;vertical-align: middle;width: 30%">
				上传图片：
			</td>
			<td style="text-align: left;vertical-align: middle;">
				<img src="${ctx}/css/common/images/bg.png" id="uploadImage" width="600" height="400"/>
			</td>
		</tr>
		<tr style="height: 100px">
			<td style="text-align: right;vertical-align: middle;width: 30%">
				车牌号：
			</td>
			<td style="text-align: left;vertical-align: middle;">
				<span id="carNum" style="font-weight: bold;font-size: larger;color: red;">车牌号显示区</span>
			</td>
		</tr>
	</table>
</div>
</body>
</html>