<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormPage.jsp"%>
<script type="text/javascript">
	function edit() {
		if (confirm("确认要更新？")) {
			$("#pageForm").submit();
		}
	}
	function editChannel(id,encoderId) {
		$("#pageForm").attr("action","${ctx}/device/channel!beforeUpdate.do?id="+id+"&encoderId="+encoderId);
		$("#pageForm").submit();
	}
	function syncChannelName(encoderId) {
		window.location = "${ctx}/device/encoder!syncChannelName.do?encoderId="+encoderId;
	}
	function deleteAllPlatformRes(){
		window.location = "${ctx}/device/encoder!deleteAllPlatformRes.do?id=${encoder.id}";
	}
	function syncAllPlatformRes(){
		window.location = "${ctx}/device/encoder!syncAllPlatformRes.do?id=${encoder.id}";
	}
	$(function(){
		$("#pageForm").validationEngine();
		$("#channelCount").change(function(){
			if($("[name='encoder\\.name']").val() == null || $("[name='encoder\\.name']").val() == ""){
				$("[name='encoder\\.name']").validationEngine("validate");
				return;
			}
			if(!$("[name='encoder\\.updateChannel']").validationEngine("validate")){
				return;
			}
			if($("[name='encoder\\.channelCount']").validationEngine("validate")){
				$("#channlePageDiv").load("${ctx}/device/encoder!createChannel.do #channlePageTable",
						{"id":$("#id").val(),"encoder.name":$("[name='encoder\\.name']").val(),"encoder.channelCount":$("[name='encoder\\.channelCount']").val(),"encoder.updateChannel":$("[name='encoder\\.updateChannel']").val()},
						function(){
							$("#channlePageDiv").divInitSet();
						}
				);
			}
		});
		$("[name='encoder\\.name']").change(function(){
			$("#channelCount").change();
		});
		$("[name='encoder\\.updateChannel']").change(function(){
			$("#channelCount").change();
		});
		var hasAudios = $("[name='encoder.hasAudio']");
		hasAudios.each(function(){
			if($(this).val()=='${encoder.hasAudio}') {
				$(this).attr("checked",true);
			} else {
				$(this).attr("checked",false);
			}
		});
		var enables = $("[name='encoder.enabled']");
		enables.each(function(){
			if($(this).val()=='${encoder.enabled}') {
				$(this).attr("checked",true);
			} else {
				$(this).attr("checked",false);
			}
		});
		var emodels = {"hik":"8000-admin-12345","tvt":"8000-admin-admin","dahua":"37777-admin-admin","lc":"3000-888888-888888","onvif":"80--"}
		$("[name='encoder.model']").change(function(){
			if($(this).val()=="platform"){
				$("[name='encoder.channelCount']").val(0);
				$("[name='encoder.channelCount']").attr("disabled",true);
			} else {
				$("[name='encoder.channelCount']").attr("disabled",false);
			}
			var emodel = emodels[($(this).val())];
			if(emodel){
				$("[name='encoder.port']").val(emodel.split("-")[0]);
				$("[name='encoder.port']").removeClass("validate[required,custom[integer]]");$("[title=port]").html("");
				if (emodel.split("-")[0] && ""!=emodel.split("-")[0]){
					$("[name='encoder.port']").addClass("validate[required,custom[integer]]");$("[title=port]").html("* ");
				}
				$("[name='encoder.username']").val(emodel.split("-")[1]);
				$("[name='encoder.username']").removeClass("validate[required]");$("[title=username]").html("");
				if (emodel.split("-")[1] && ""!=emodel.split("-")[1]){
					$("[name='encoder.username']").addClass("validate[required]");$("[title=username]").html("* ");
				}
				$("[name='encoder.password']").val(emodel.split("-")[2]);
				$("[name='encoder.password']").removeClass("validate[required]");$("[title=password]").html("");
				if (emodel.split("-")[2] && ""!=emodel.split("-")[2]){
					$("[name='encoder.password']").addClass("validate[required]");$("[title=password]").html("* ");
				}
			} else {
				$("[name='encoder.port']").removeClass("validate[required,custom[integer]]").val("");$("[title=port]").html("");
				$("[name='encoder.username']").removeClass("validate[required]").val("");$("[title=username]").html("");
				$("[name='encoder.password']").removeClass("validate[required]").val("");$("[title=password]").html("");
			}
		});
		if($("[name='encoder.model']").val()=="platform"){
			$("#channlePageDiv").find("a").attr("disabled", true);
			$("[name='encoder.channelCount']").attr("disabled",true);
		}
		//$("[name='encoder.model']").change();
	});
</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 编码器管理 &gt; 编辑编码器</span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/device/encoder!update.do" method="post">
			<input type="hidden" id="id" name="id" value="${encoder.id}">
			<table width="90%" class="inputTable">
				<tr>
					<td style="text-align:right;" width="10%"><label class="field-request">* </label>编码器类型：</td>
					<td width="20%">
						<select class="validate[required]" name="encoder.model" id="model">
							<option value="">请选择</option>
							<c:forEach var="encoderModel" items="${encoderModels}">
								<option value="${encoderModel.key}" <c:if test="${encoder.model eq encoderModel.key}">selected</c:if>>${encoderModel.value}</option>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right;" width="10%"><label class="field-request">* </label>编码器名称：</td>
					<td width="20%"><input type="text" value="${encoder.name}" class="validate[required,ajax[checkUnique]]" name="encoder.name" id="encoder_name_${encoder.id}" maxlength="64"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">ip：</td>
					<td><input type="text" value="${encoder.ip}" class="validate[custom[ipv4]]" name="encoder.ip" id="ip" /></td>
					<td style="text-align: right;"><label title="port" class="field-request">* </label>端口：</td>
					<td><input type="text" value="${encoder.port}" class="validate[required,custom[integer]]" name="encoder.port" id="port" /></td>
				</tr>
				<tr>
					<td style="text-align: right;"><label title="username" class="field-request">* </label>用户名：</td>
					<td><input type="text" value="${encoder.username}" class="validate[required]" name="encoder.username" id="username" /></td>
					<td style="text-align: right;"><label title="password" class="field-request">* </label>密码：</td>
					<td><input type="text" value="${encoder.password}" class="validate[required]" name="encoder.password" id="password" /></td>
				</tr>
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>接入服务器：</td>
					<td>
						<select  name="serverEncoder.serverId" id="mduId">
							<option value="">请选择</option>
							<c:forEach var="mdu" items="${mdus}">
						<option value="${mdu.key}" <c:if test="${serverEncoder.serverId eq mdu.key}">selected</c:if>>${mdu.value}</option>
					</c:forEach>
						</select>
					</td>
					<td style="text-align:right;">存储服务器：</td>
					<td>
						<select name="msuChannel.serverId" id="msuId">
							<option value="">请选择</option>
							<c:forEach var="msu" items="${msus}">
								<option value="${msu.key}" <c:if test="${msuChannel.serverId eq msu.key}">selected</c:if>>${msu.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			<!--	<tr>
					<td style="text-align: right;"><label class="field-request">* </label>输入通道数：</td>
					<td><input type="text" value="${encoder.inputCount}" class="validate[required,custom[integer]]" name="encoder.inputCount" id="inputCount" /></td>
					<td style="text-align: right;"><label class="field-request">* </label>输出通道数：</td>
					<td><input type="text" value="${encoder.outputCount}" class="validate[required,custom[integer]]" name="encoder.outputCount" id="outputCount" /></td>
				</tr>  -->
				<tr>
					<td style="text-align: right;"><label class="field-request">* </label>视频通道数：</td>
					<td><input type="text" value="${encoder.channelCount}" class="validate[required,custom[integer]]" name="encoder.channelCount" id="channelCount" readonly="readonly"/></td>
					<td style="text-align:right;">物理位置：</td>
					<td>
						<input type="text" name="encoder.address" id="address" value="${encoder.address}">
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">语音是否启用：</td>
					<td><input type="radio" name="encoder.hasAudio" id="hasAudio1" value="true"/> 是 <input type="radio" name="encoder.hasAudio" id="hasAudio2" value="false"/> 否 </td>
					<td style="text-align: right;">是否启用：</td>
					<td><input type="radio" name="encoder.enabled" id="enabled1" value="true"/> 是 <input type="radio" name="encoder.enabled" id="enabled2" value="false"/> 否</td>
				</tr>
				<tr>
					<td style="text-align:right;">厂家：</td>
					<td colspan="3">
						<input type="text" name="encoderExtra.corp" id="corp" value="${encoderExtra.corp}">
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">设备型号：</td>
					<td>
						<input type="text" name="encoderExtra.version" id="version" value="${encoderExtra.version}">
					</td>
					<td style="text-align:right;">MAC地址：</td>
					<td>
						<input type="text" name="encoderExtra.mac" id="mac" value="${encoderExtra.mac}">
					</td>
				</tr>
				<tr>
					<td style="text-align:right;"><label class="field-request">* </label>是否更新通道名：</td>
					<td colspan="3">
						<select id="updateChannel"  name="encoder.updateChannel" class="validate[required]">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">备注(最多256字符)：</td>
					<td colspan="3">
						<textarea name="encoder.description" id="description" value="${encoder.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${encoder.description}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0)" onclick="syncChannelName('${encoder.id}')">同步摄像头名称</a>
						<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a></td>
				</tr>
			</table>
		</form>
		<fieldset title="摄像机通道编辑">
			<legend>&nbsp;&nbsp;视频通道编辑&nbsp;&nbsp;</legend>
			<div id="channlePageDiv" name="pageDiv" class="pageDiv">
				<table id="channlePageTable" align="center" width="98%" border="0" cellspacing="0" cellpadding="0">
					<thead>
					<tr>
						<th width="15%">视频通道ID</th>
						<th width="15%">视频通道名称</th>
						<th width="10%">编码器名称</th>
						<th width="10%">存储服务器</th>
						<th width="6%">存储方案</th>
						<th width="5%">云台启用</th>
						<th width="5%">音视频启用</th>
						<th width="5%">流媒体个数</th>
						<th width="5%">启用</th>
						<th width="18%">描述</th>
						<th width="3%">操作</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="channel" items="${channels}">
						<tr>
							<td>${channel.id}</td>
							<td>${channel.name}</td>
							<td>${encoder.name}</td>
							<td>${msuChannel.serverName}</td>
							<td>${channel.recordPlanName}</td>
							<td>${channel.hasPtzZh}</td>
							<td>${channel.hasAudioZh}</td>
							<td>${channel.streamCount}</td>
							<td>${channel.enabledZh}</td>
							<td>${channel.description}</td>
							<td>
								<a href="javascript:editChannel('${channel.id}','${encoder.id}')">编辑</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
					<c:if test="${encoder.model eq 'platform'}">
						<tfoot>
						<tr>
							<td colspan="11" style="text-align: center;vertical-align: middle;height: 35px">
								<a class="a_button" href="javascript:void(0);" onclick="return deleteAllPlatformRes();"><span>清空摄像头</span></a>&nbsp;&nbsp;
								<a class="a_button" href="javascript:void(0);" onclick="return syncAllPlatformRes();"><span>同步子级摄像头</span></a>
							</td>
						</tr>
						</tfoot>
					</c:if>
				</table>
			</div>
		</fieldset>
	</div>
</body>
</html>