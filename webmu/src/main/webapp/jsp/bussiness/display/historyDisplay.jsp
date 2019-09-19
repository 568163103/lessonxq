<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/dynatree/ui.dynatree.css" />
	<link rel="stylesheet" href="${ctx}/css/jquery/jquery.ui.slider.css" />
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.dynatree.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.slider.min.js"></script>
	<title>${global_app_name}</title>
	<style type="text/css">
		.ui-slider .ui-slider-handle {
			height: 16px;
			width: 5px;
		}
		.div-timeline {
			height: 100%;
			width: 100%;
		}
		.div-timeline-display {
			height: 100%;background-color: #379be9;float: left;
		}
		.div-timeline-nodisplay {
			height: 100%;float: left;
		}
	</style>
	<script type="text/javascript">
		var $playerIframe = null,currNode = null,currChannelId = null,currChannelName = null,recordPath = '${recordPath}',recordType = 0,
				startTimeOfDay = ${startTimeOfDay},endTimeOfDay = ${endTimeOfDay},recordId=null,videoInfos = [],sliderValue = 0;

		function openPlayInIframe(startTime,endTime) {
			if(null != currChannelId){
				recordId=encodeURIComponent(currChannelId+"#"+startTime+"#"+endTime+"#"+recordPath+"#"+recordType);
				if(window.playerIframe.playerInstance){window.playerIframe.playerInstance.stop();}
				$playerIframe.loading("加载中，请稍后");
				$playerIframe.attr("src", "${ctx}/jsp/bussiness/display/player.jsp?mduIp=${mduIp}&mduPort=${mduPort}&channelId="+recordId+"&channelName="+currChannelName+"&noTitle=${param.noTitle}");
			} else {
				alert("请选择云镜");
			}
		}

		function openPlayInIframeOfDay(targetDay) {
			startTimeOfDay = targetDay.split("--")[0];
			endTimeOfDay = targetDay.split("--")[1];
			openPlayInIframeOfAnyTime(startTimeOfDay,endTimeOfDay);
		}

		function openPlayInIframeOfAnyTime(startTime,endTime) {
			if(null != currChannelId){
				videoInfos = [];sliderValue = -1;
				var divTimelines = $(".div-timeline").empty();
				var url = "${ctx}/thirdparty/formInterface.do?cmd=120106";
				$("body").loading("加载中，请稍后");
				$.post(url,{"displayDto.resId":currChannelId,"displayDto.recordPath":recordPath,"displayDto.recordType":recordType,"displayDto.stime":startTime,"displayDto.etime":endTime},function(data){
					if("OK" == data.errorMsg){
						$.each(data.videoInfos,function(i,videoInfo){
							var divTimeline = $('<div style="width: '+videoInfo.percent+';"/>');
							if(videoInfo.hasVideo){
								if(sliderValue == -1)
									sliderValue = videoInfo.startTime;
								videoInfos[videoInfos.length] = videoInfo;
								divTimeline.addClass("div-timeline-display");
							} else
								divTimeline.addClass("div-timeline-nodisplay");
							divTimelines.append(divTimeline);
						});
					}
					if(sliderValue == -1){
						sliderValue = 0;
						alert("该设备没有录像");
						if(window.playerIframe.playerInstance){window.playerIframe.playerInstance.stop();}
					} else {
						openPlayInIframe(startTime,endTime);
					}
					$("#playerslider").slider("option","value",sliderValue);
					$("body").loadhide();
				});
			} else {
				alert("请选择云镜");
			}
		}

		function openPlayInIframeOfDefault(channelId,channelName) {
			currChannelId = channelId;currChannelName = channelName;$("[name=radio]")[0].click();
			openPlayInIframeOfAnyTime('${currQueryDate}'.split("--")[0],'${currQueryDate}'.split("--")[1]);
		}

		function openPlayInIframeOfClickTime(clickTime) {
			clickTime = (startTimeOfDay * 1 + clickTime * 1);
			openPlayInIframe(clickTime,endTimeOfDay);
		}

		$(function(){
			<c:if test="${empty channelId}">
			$("#tree_0").dynatree({
				checkbox: false,
				selectMode: 3,
				autoCollapse: false,
				fx: { height: "toggle", duration: 200 },
				imagePath:"${ctx}/css/dynatree/images/",
				children:${userResourceTree},
				onFocus: function(dtnode) {
					var keys = dtnode.data.key.split("---");
					if(keys[keys.length-1].substring(6,9)==${channelValue}){
						if(null != currNode){
							currNode.data.icon = currNode.data.icon.replace("open","online");
							currNode.render(false);
						}
						currNode = dtnode;
						currNode.data.icon = currNode.data.icon.replace("online","open");
						currNode.render(false);
						openPlayInIframeOfDefault(keys[keys.length-1],currNode.data.title);
					}
				}
			});
			</c:if>
			$playerIframe = $("#playerIframe");
			$playerIframe.load(function(){
				$playerIframe.loadhide();
				$playerIframe.contents().find("body").click(function(){
					window.document.body.click();
				});
			});

			$( "#playerslider" ).slider({
				value:0,
				min:0,
				max: 86400,
				start: function(event, ui){
					sliderValue = ui.value;
				},
				slide: function(event, ui){
					if(null == currChannelId){
						alert("请选择云镜");
						return false;
					}
					//ui.handle.title = parseInt(ui.value/(60*60))+"."+ui.value%(60);
					var flag = false;
					$.each(videoInfos,function(i,videoInfo){
						var v = ui.value;
						if(videoInfo.startTime < v && v < videoInfo.endTime){
							flag = true;
							return false;
						}
					});
					if(!flag){
						return false;
					}
				},
				change: function( event, ui ) {
					if(ui.value != sliderValue){
						openPlayInIframeOfClickTime(ui.value);
					}
				}
			});
			<c:if test="${'' ne param.s}">
			$( "#selectDay").css('display','block').buttonset();
			</c:if>
			<c:set var="pageWidth" value="1330"/>
			<c:if test="${not empty channelId}">
			<c:set var="pageWidth" value="969"/>
			openPlayInIframeOfDefault('${channelId}','${channelName}');
			</c:if>
		});
	</script>
</head>
<body class="main_bg">
<c:if test="${empty param.noTitle}">
<div class="win-header" id="a" style="min-width: ${pageWidth}px">
	<span class="win-header-title"><c:if test="${param.m eq '1'}">地图模式</c:if><c:if test="${param.m ne '1'}">视频浏览</c:if> &gt; <c:if test="${recordPath eq '1'}">设备录像</c:if><c:if test="${recordPath eq '0'}">中心录像</c:if></span>
</div>
</c:if>
<div class="win-bodyer" style="min-width: ${pageWidth}px">
	<table width="100%" class="inputTable" border="0">
		<tr>
			<c:if test="${empty channelId}">
			<td id="leftTree" align="center" style="width: 325px;height: 98%;vertical-align: top" rowspan="2">
				<fieldset style="width: 325px;height: 100%;">
					<legend>&nbsp;&nbsp;自定义目录树&nbsp;&nbsp;</legend>
					<table class="inputTable" border="0">
						<tr>
							<td align="center" style="padding-left: 2px">
								<div id="tree_0" style="padding-left: 2px;width: 320px;height: 760px;overflow:auto;"></div>
							</td>
						</tr>
					</table>
				</fieldset>
			</td>
			</c:if>
			<td style="text-align: center;vertical-align: top">
				<iframe id="playerIframe" name="playerIframe" width="965px" height="625px" frameBorder="0" scrolling="auto" allowtransparency=yes src="${ctx }/jsp/bussiness/display/player.jsp"></iframe>
			</td>
		</tr>
		<tr>
			<td style="height: 140px;text-align: center;">
				<div style="height: 80px;" align="center">
					<div style="width: 800px;">
						<div id="playerslider">
							<div class="div-timeline"></div>
						</div>
						<img src="${ctx}/css/player/image/timeline.png" style="height: 22px;width: 800px;">
						<ul class="ul_timeline">
							<li style="float:left;width: 4%;text-align: left;">00:00</li>
							<li style="float:left;width: 20%;text-align: center;">&nbsp;</li>
							<li style="float:left;width: 4%;text-align: left;">06:00</li>
							<li style="float:left;width: 20%;text-align: center;">&nbsp;</li>
							<li style="float:left;width: 4%;text-align: center;">12:00</li>
							<li style="float:left;width: 20%;text-align: center;">&nbsp;</li>
							<li style="float:left;width: 4%;text-align: right;">18:00</li>
							<li style="float:left;width: 20%;text-align: center;">&nbsp;</li>
							<li style="float:left;width: 4%;text-align: right;">24:00</li>
						</ul>
					</div>
				</div>
				<div id="selectDay" style="display: none">
					<c:forEach var="day" items="${days}" varStatus="status">
						<input type="radio" id="radio${status.index}" name="radio" <c:if test="${status.index eq 0}">checked</c:if>>
						<label id="label${status.index}" for="radio${status.index}" onclick="openPlayInIframeOfDay('${day.value}')">${day.key}</label>
					</c:forEach>
					<input type="radio" id="radioMore" name="radio" onclick="WdatePicker({onpicking:function(dp){openPlayInIframeOfDay(dp.cal.getNewDateStr())},dateFmt:'yyyy-MM-dd'})"><label for="radioMore">更早...</label>
				</div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
