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
	
	$( function() {
			selectSupp();	
			
			$("#excelDiv").dialog({
			title:'从excel导入单位初始化数据',
			autoOpen: false
		});
		
		$("#impFromExcelFile").uploadify({
			'height'        	:31,
			'width'        		:102,
			'fileObjName'		:'fileSource',
			'uploader'			:'${ctx}/bussiness/usertree!impFromExcel.do;jsessionid='+jsessionid,
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
		})
	function updateAllPlan() {
		if (!checkedItems()) {
			alert("请至少选择要查询的通道。");
			return false;
		}
		$("#pageForm").attr("action","${ctx}/bussiness/usertree!updateMenu.do");
		$("#pageForm").submit();
	}
	
	
	
	function selectSupp(){
		
		var objS = document.getElementById("userid");//获取配送员的信息
		var userid = objS.options[objS.selectedIndex].value;//获取配送员下拉选定的数据
		var codec = $("#muluid").val();//获取配送员下拉选定的数据
		$("#corp").empty();
		$("#corp2").empty();
		 //根据地址和配送员信息发送一个ajax查询，获取相应的配送员信息
		 $.ajax({
			 url:'${ctx}/bussiness/usertree!findMenu.do?method=selectBdSupp',
			 data:{
					userid:userid
					},
			success : function(data) {//将查询到的供应商信息放在供应商的select标签框中
				if(data.length!=0){//绑定的供应商显示
					$("#corp").append("<option value = ''>取消分组</option>");
					for(var i=0;i<data.length;i++){
						$("#corp").append("<option value = "+data[i].resId+">"+data[i].name+"</option>");
					}
					$("#corp2").append("<option value = ''>全部</option>");
					if ( codec == '未分组'){
						$("#corp2").append("<option value = '未分组' selected>未分组</option>");
					}else{
						$("#corp2").append("<option value = '未分组'>未分组</option>");
					}					
					for(var i=0;i<data.length;i++){
						var resId = data[i].resId;
						if (resId == codec){
							$("#corp2").append('<option value = '+data[i].resId+' selected >'+data[i].name+'</option>');
						}else{
							$("#corp2").append('<option value = '+data[i].resId+' >'+data[i].name+'</option>');
						}
						
					}
				}else{
					$("#corp").append("<option value = ''>取消分组</option>");
					$("#corp2").append("<option value = ''>全部</option>");
					return;
				}
			}
		 }); 

	 } 
	 
	 
	 function openExcelDiv() {
		if (isSetFlash){
			$('#excelDiv').dialog('open');
		} else {
			alert("您未安装FLASH控件，无法从excel导入数据！如需从excel导入数据请先安装FLASH控件。");
		}
	}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
	   <c:if test="${not empty serverId}">
	   	   <span class="win-header-title">外域设备管理 &gt; 接入网关  &gt; 目录资源信息</span>
	   </c:if>
	   <c:if test="${empty serverId}">
	   	   <span class="win-header-title">外域设备管理 &gt; 摄像机通道管理 </span>
	   </c:if>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/bussiness/usertree!findPage.do" method="post">
		<input type="hidden" name="serverId" value="${serverId }">
		<input type="hidden" name="muluid" id="muluid" value="${pageObject.params.codec }">
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td style="text-align: right">通道ID：</td>
					<td><input type="text" id="pageObject.params.id" name="pageObject.params.id" value="${pageObject.params.id}"></td>
					<td style="text-align: right">通道名称：</td>
					<td><input type="text" id="pageObject.params.channelName" name="pageObject.params.channelName" value="${pageObject.params.channelName}"></td>
					<td style="text-align: right">目录分组名：</td>
					<td>
					<select class="validate[required]" name="pageObject.params.codec" id="corp2" >		
							<option value="">请先选择目录组</option>					
						</select>
					</td>
				 </tr>
				 <tr>
					<td style="text-align: right">接入网关：</td>
					<td>
					<select id="pageObject.params.encodeName" name="pageObject.params.encodeName">
							<option value="">全部</option>
							<c:forEach var="mdu" items="${platforms}">
								<option value="${mdu.key}" <c:if test="${pageObject.params.encodeName eq mdu.key}">selected</c:if>>${mdu.value}</option>
							</c:forEach>
					</select>	</td>				
					<td style="text-align: right">是否在线：</td>
					<td>
						<select id="pageObject.params.status" name="pageObject.params.status">
							<option value="">全部</option>
							<option value="1" <c:if test="${pageObject.params.status eq '1'}">selected</c:if>>是</option>
							<option value="0" <c:if test="${pageObject.params.status eq '0'}">selected</c:if>>否</option>
						</select>
					</td>
					<td style="text-align: right">用户：</td>
					<td>
					<select id="userid" name="pageObject.params.userid" onchange="selectSupp()">
							<option value=""></option>
							<c:forEach var="mdu" items="${users}">
								<option value="${mdu.key}" <c:if test="${pageObject.params.userid eq mdu.key}">selected</c:if>>${mdu.value}</option>
							</c:forEach>
					</select>	</td>	
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
					<th width="10%">在线</th>
					<th width="19%">目录树分组名</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="channel" items="${pageObject.resultList}">
					<tr <c:if test="${channel.status  eq '0'}">class="font-red" </c:if>>
						<td class="center">
							<input type="checkbox" name="items" class="items" value="${channel.id}|${channel.name}"/>
						</td>
						<td>${channel.id}</td>
						<td>${channel.name}</td>
						<td>${channel.encoderName}</td>
						<td><c:if test="${channel.status  eq '0'}">离线</c:if><c:if test="${channel.status  eq '1'}">在线</c:if></td>
						<td>${channel.description}</td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr>
					<td colspan="6">
						<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
					</td>
				</tr>
				<tr>
						<td colspan="6" style="text-align: center;vertical-align: top;height: 35px;border-right: 0px solid #cfcfcf;">
							
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;操作&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td colspan="6" style="text-align: center;vertical-align: top;height: 35px">
						<select class="validate[required]" name="pageObject.params.menu" id="corp">		
							<option value="">请先选择目录组</option>					
						</select>
						
						<a class="a_button" href="javascript:void(0);" onclick="return updateAllPlan();"><span>调整</span></a></a>&nbsp;&nbsp;
						
						<a  class="a_button" href="javascript:void(0);" onclick="openExcelDiv();"><span>从Excel导入</span></a>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	</div>
		<div id="excelDiv" style="width: 300px;display: none;">
		<table width="100%">			
			<tr >
				<td style="text-align: center;vertical-align: middle;">
					<input type="file" id="impFromExcelFile" name="impFromExcelFile"></td>
			</tr>
		</table>
	</div>
</body>
</html>