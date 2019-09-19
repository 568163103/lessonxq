<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
	<script type="text/javascript">
		function edit() {
			if (confirm("确认要更新？")) {
				var longitude = $("#baiduLongitude").val();
				var latitude = $("#baiduLatitude").val();
				  var longitude1 = changeToDu(longitude.substring(1));
				  var latitude1 = changeToDu(latitude.substring(1));
            //转换为百度坐标
				  var lng = parseFloat(longitude1);
				  var lat = parseFloat(latitude1);
				   $("#longitude").val(lng);
				   $("#latitude").val(lat);
			$("#pageForm").submit();
			}
		}
		
		
		$(function(){
			var hasPtzs = $("[name='channel.hasPtz']");
			hasPtzs.each(function(){
				if($(this).val()=='${channel.hasPtz}') {
					$(this).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}
			});
			var hasAudios = $("[name='channel.hasAudio']");
			hasAudios.each(function(){
				if($(this).val()=='${channel.hasAudio}') {
					$(this).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}
			});
			var enables = $("[name='channel.enabled']");
			enables.each(function(){
				if($(this).val()=='${channel.enabled}') {
					$(this).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}
			});
			$("[name=pageForm]").validationEngine();
			
			var longitude = $("#longitude").val();
			var latitude = $("#latitude").val();
			var pointLng = cacuLonLat(longitude);
			var pointLat = cacuLonLat(latitude);
			$("#baiduLongitude").val("E"+pointLng);
			$("#baiduLatitude").val("N"+pointLat);
			getEncoderInfo();
		});
		function getEncoderInfo() {
			var encoderId = $('#encoderId').val();
			console.log("encoderId="+encoderId);
			// $.ajax({
			// 	type: "POST",
			// 	dataType: "json",
			// 	url: "",
			// 	data: {},
			// 	success: function(data){
			//
			// 	},
			// 	error: function(msg){
			//
			// 	}
			// });

		}
			
			//度分秒转经纬度
        function changeToDu(dfm) {
            var arr1 = dfm.split('°');
            var d = arr1[0];
            var arr2 = arr1[1].split("'")
            var f = arr2[0] || 0;
            var m = arr2[1] || 0;
            f = parseFloat(f) + parseFloat(m / 60);
            var du = parseFloat(f / 60) + parseFloat(d);
            return du;
        }
		
		//经纬度转换为度分秒
		function cacuLonLat(a) {
			var degree = parseInt(a);
			var min = parseInt((a - degree) * 60);
			var second = parseInt((a - degree) * 3600 - min * 60);
			//var secondfix = (a - degree) * 3600 - min * 60;
			//ar lonlat = degree + '°' + min + '′' + secondfix + '″';
			//console.log(lonlat,strlen(lonlat))
			var secLen = 7 - strlen(String(degree)) - strlen(String(min))
			if( secLen <= 0 ){
				var sec = parseInt((a - degree) * 3600 - min * 60);
			}else{
				var second =  parseInt((a - degree) * 3600 - min * 60);
				var secLens = secLen - strlen(String(second))
				if( secLens-1  <= 0  ){
					var sec = parseInt((a - degree) * 3600 - min * 60);
				}else{
					var sec = ((a - degree) * 3600 - min * 60).toFixed(secLens-1);
				}
			}
			//var sec = ((a - degree) * 3600 - min * 60).toFixed(6);
			return degree + '°' + min + '′' + sec + '″';
		}
		
		//坐标转换
		//var pointGcj = coordtransform.bd09togcj02(point.lng,point.lat)
		//alert(pointGcj)
		//var pointWgs = coordtransform.gcj02towgs84(pointGcj[0],pointGcj[1])
		//alert(pointWgs)

		 function strlen(str){
        var len = 0;
        for (var i=0; i<str.length; i++) { 
         var c = str.charCodeAt(i); 
        //单字节加1
         if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
           len++; 
         } 
         else { 
          len+=2; 
         } 
        } 
        return len;
    }

	</script>
<title>${global_app_name}</title>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 摄像机通道管理 &gt; 编辑摄像机通道</span>
	</div>
	<div class="win-bodyer" align="center">
		<form id="pageForm" name="pageForm" action="${ctx}/device/channel!update.do" method="post">
			<input type="hidden" id="id" name="id" value="${channel.id}">
			<input type="hidden" value="${imsGisInfo.longitude}"  name="imsGisInfo.longitude" id="longitude" >
			<input type="hidden" value="${imsGisInfo.latitude}"  name="imsGisInfo.latitude" id="latitude">
			<input type="hidden" id="encoderId" name="encoderId" value="${param.encoderId}">
			<fieldset style="width: 80%;text-align: center">
				<legend>&nbsp;&nbsp;基础信息&nbsp;&nbsp;</legend>
				<table width="90%" class="inputTable">
					<tr>
						<td style="text-align: right;" width="10%"><label class="field-request">* </label>摄像机通道名称：</td>
						<td width="20%"><input type="text" value="${channel.name}" class="validate[required]" name="channel.name" id="name" maxlength="64"/></td>
						<td style="text-align: right;" width="10%">存储服务器：</td>
						<td width="20%">
							<input type="hidden" name="msuChannel.channelId" value="${channel.id}">
							<select name="msuChannel.serverId" id="serverId">
								<option value="">请选择</option>
								<c:forEach var="msu" items="${msus}">F
									<option value="${msu.key}" <c:if test="${msuChannel.serverId eq msu.key}">selected</c:if>>${msu.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">存储方案：</td>
						<td>
							<input type="hidden" name="channelRecordPlan.channelId" value="${channel.id}">
							<select name="channelRecordPlan.planName" id="planName">
								<option value="">请选择</option>
								<c:forEach var="recordPlanName" items="${recordPlanNames}">
									<option value="${recordPlanName}" <c:if test="${channelRecordPlan.planName eq recordPlanName}">selected</c:if>>${recordPlanName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">语音是否启用：</td>
						<td><input type="radio" name="channel.hasAudio" id="hasAudio1" value="1"/> 是 <input type="radio" name="channel.hasAudio" id="hasAudio2" value="0"/> 否 </td>
						<td style="text-align: right;">云台是否启用：</td>
						<td><input type="radio" name="channel.hasPtz" id="hasPtz1" value="1"/> 是 <input type="radio" name="channel.hasPtz" id="hasPtz2" value="0"/> 否</td>
					</tr>
					<tr>
						<td style="text-align:right;">流媒体数：</td>
						<td>
							<input type="text" name="channel.streamCount" id="streamCount" value="${channel.streamCount}" class="validate[custom[integer]]">
						</td>
						<td style="text-align: right;">是否启用：</td>
						<td><input type="radio" name="channel.enabled" id="enabled1" value="true"/> 是 <input type="radio" name="channel.enabled" id="enabled2" value="false"/> 否</td>
					</tr>
					<tr>
						<td style="text-align:right;">备注(最多256字符)：</td>
						<td >
							<textarea name="channel.description" id="description" value="${channel.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${channel.description}</textarea>
						</td>
					</tr>
				</table>
				<input type="hidden" name="imsGisInfo.channelId" value="${channel.id}" id="imsGisInfo.channelId">
				<table width="100%" class="inputTable">
					<tr>
						<td style="text-align: right;" width="18%">经：</td>
						<td width="30%"><input type="text" value=""  name="baiduLongitude" id="baiduLongitude" /></td>
						<td style="text-align: right;">纬：</td>
						<td><input type="text" value=""  name="baiduLatitude" id="baiduLatitude" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">最大视距：</td>
						<td><input type="text" value="${imsGisInfo.curRange}" class="validate[custom[number]]" name="imsGisInfo.curRange" id="curRange" /></td>
						<td style="text-align: right;">最大视角：</td>
						<td><input type="text" value="${imsGisInfo.angle}" class="validate[custom[number]]" name="imsGisInfo.angle" id="angle" /></td>
					</tr>
					<tr>

						<td style="text-align: right;">高度：</td>
						<td><input type="text" value="${imsGisInfo.height}" class="validate[custom[number]]" name="imsGisInfo.height" id="height" /></td>

						<td style="text-align: right;">IP地址：</td>
						<td><input type="text" value="${encoder.ip}"   readonly/></td>
					</tr>
					<tr>

					<td style="text-align: right;">安装位置：</td>
					<td><input type="text" value="${encoder.address}" readonly/></td>

					<td style="text-align: right;">厂家 ：</td>
					<td><input type="text" value="${encoderExtra.corp}"  readonly/></td>
				</tr>
					<tr>

						<td style="text-align: right;">型号：</td>
						<td colspan="3"><input type="text" value="${encoderExtra.version}"  readonly/></td>
					</tr>
					<tr>
						<td colspan="4" style="text-align: center;">
							<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
							<c:if test="${empty param.encoderId}">
								<a class="a_button" href="javascript:history.back(-1);"><span>返回</span></a>
							</c:if>
							<c:if test="${not empty param.encoderId}">
								<a class="a_button" href="${ctx}/device/encoder!beforeUpdate.do?id=${channel.encoderId}"><span>返回</span></a>
							</c:if>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
		<fieldset style="width: 80%;text-align: center">
			<legend>&nbsp;&nbsp;预置位设置&nbsp;&nbsp;</legend>
			<c:set var="newNum" value="1"/>
			<table width="100%" class="inputTable">
				<c:forEach var="preset" items="${presets}" varStatus="status">
				<c:set var="newNum" value="${status.count+1}"/>
				<form id="presetPageForm-${status.count}" name="pageForm" action="${ctx}/device/channel!updatePreset.do" method="post">
					<input type="hidden" name="preset.channelId" value="${channel.id}" id="preset.channelId-${status.count}">
					<input type="hidden" name="preset.num" value="${preset.num}" id="preset.num-${status.count}">
					<tr>
						<td style="text-align: right;" width="18%"><label class="field-request">* </label>名称：</td>
						<td width="30%"><input type="text" value="${preset.name}" name="preset.name" id="preset.name-${status.count}" class="validate[required]" /></td>
						<td style="text-align: right;"><label class="field-request">* </label>类型：</td>
						<td colspan="3">
							<select class="validate[required]" name="preset.flag" id="preset.flag-${status.count}" >
								<option value="">请选择</option>
								<c:forEach var="presetFlag" items="${presetFlags}">
									<option value="${presetFlag.value}" <c:if test="${preset.flag eq presetFlag.value}">selected</c:if>>${presetFlag.label}</option>
								</c:forEach>
							</select>
						</td>
						<td style="text-align: center;"><a class="a_button" href="javascript:void(0)" onclick="$('#presetPageForm-${status.count}').submit()"><span>更新</span></a></td>
					</tr>
				</form>
				</c:forEach>
				<form id="presetPageForm-${newNum}" name="pageForm" action="${ctx}/device/channel!updatePreset.do" method="post">
					<input type="hidden" name="preset.channelId" value="${channel.id}" id="preset.channelId-${newNum}">
					<input type="hidden" name="preset.num" value="${newNum}" id="preset.num-${newNum}">
					<tr>
						<td style="text-align: right;" width="18%"><label class="field-request">* </label>名称：</td>
						<td width="30%"><input type="text" value="" name="preset.name" id="preset.name-${newNum}" class="validate[required]" /></td>
						<td style="text-align: right;"><label class="field-request">* </label>类型：</td>
						<td colspan="3">
							<select class="validate[required]" name="preset.flag" id="preset.flag-${newNum}" >
								<option value="">请选择</option>
								<c:forEach var="presetFlag" items="${presetFlags}">
									<option value="${presetFlag.value}">${presetFlag.label}</option>
								</c:forEach>
							</select>
						</td>
						<td style="text-align: center;">
							<a class="a_button" href="javascript:void(0)" onclick="$('#presetPageForm-${newNum}').submit()"><span>添加</span></a>
						</td>
					</tr>
				</form>
			</table>
		</fieldset>
	</div>
</body>
</html>