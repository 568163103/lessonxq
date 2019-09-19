<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/uploadify/uploadify.css" />
	<style type="text/css">
		#impSnFile{
			margin:auto;
		}
	</style>
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.uploadify.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#impSnFile").uploadify({
				'height'        	:27,
				'width'        		:78,
				'fileObjName'		:'fileSource',
				'uploader'			:'${ctx}/common/module!importSn.do;jsessionid='+jsessionid,
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
				'onUploadSuccess'	:function(file,data,response) {
					if(data == "ok"){
						alert(file.name + " 导入成功！");
					}else
						alert(data);
				}
			});
			$(".a_button_blue").button();
		});
	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">系统管理 &gt; 许可管理 </span>
	</div>
	<div class="win-bodyer">
		<table width="100%">
			<tr style="height: 100px">
				<td style="text-align: center;vertical-align: middle;">
					<input class="a_button" type="button" id="expSnFile" name="expSnFile" value="导出许可" onclick="location.href='${ctx}/common/module!exportSn.do'">
				</td>
				<td style="text-align: center;vertical-align: middle;">
					<input type="file" id="impSnFile" name="impSnFile">
				</td>
				<sec:authorize url="/common/module!cteateSn.do">
					<td style="text-align: center;vertical-align: middle;">
						<a class="a_button" href="${ctx}/jsp/common/systemsn/editSystemSn.jsp"><span>编辑许可</span></a>
					</td>
				</sec:authorize>
			</tr>
		</table>
	</div>
</body>
</html>