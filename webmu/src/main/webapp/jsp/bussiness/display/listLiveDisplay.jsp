<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${global_app_name}</title>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<style type="text/css">
		.playtd{
			padding: 5px;
			text-align: center;
			vertical-align: middle;
		}
		<c:choose>
		<c:when test="${param.num == 1}">
		.playerIframe{width:1045px;height:630px;}
		</c:when>
		<c:when test="${param.num == 9}">
		.playerIframe{width:440px;height:260px;}
		</c:when>
		<c:when test="${param.num == 4}">
		.playerIframe{width:660px;height:390px;}
		</c:when>
		</c:choose>
	</style>
	<script type="text/javascript">

		function childOpenPlayInIframe(channelId,channelName) {
			window.open ("${ctx}/jsp/bussiness/display/player.jsp?mduIp=${mduIp}&mduPort=${mduPort}&channelId="+channelId+"&channelName="+channelName+"&childOpen=true", "singleplayer" ,
				'width='+ (window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes' ) ;
		}

		function operateChannel(channelId,action,step){
			var url = "${ctx}/thirdparty/formInterface.do?cmd=120104&channelId="+channelId+"&action="+action+"&step="+step;
			$.post(url,function(data){
				if("OK" != data){
					alert("操作失败！");
				}
			});
		}

		$(function(){
			$(".playtd").find("img").css("cursor","pointer");
		});
	</script>
</head>
<body class="main_bg">
<div class="win-bodyer" style="min-width: 1660px">
	<table border="0" style="margin: auto">
		<c:forEach var="device" items="${devices}" varStatus="status">
			<c:if test="${(param.num == 9 && status.count%3==1) || (param.num== 4 && status.count%2==1)  || (param.num== 1)}">
				<tr>
			</c:if>
			<td class="playtd">
				<table  border="0" style="border:1px solid #9A9A9A;">
					<tr>
						<td style="padding: 5px;">
							<span style="font-size: 16px;font-weight:bold;">${device.devname}</span><br>
							<span  style="line-height: 18px">${device.groupname}</span></td>
					</tr>
					<tr>
						<td>
							<iframe name="playerIframe" class="playerIframe" frameBorder="0" scrolling="auto" allowtransparency=yes src="${ctx}/jsp/bussiness/display/player.jsp?mduIp=${mduIp}&mduPort=${mduPort}&channelId=${device.devid}"></iframe>
						</td>
					</tr>
					<tr>
						<td style=" background-color: #EEEEEE" valign="middle">
							<img src="${ctx}/css/player/image/arrow_hover01.png" title="向上" onmousedown="operateChannel('${device.devid}','UP',5)" onmouseup="operateChannel('${device.devid}','STOP','UP')">
							<img src="${ctx}/css/player/image/arrow_hover02.png" title="向下" onmousedown="operateChannel('${device.devid}','DOWN',5)" onmouseup="operateChannel('${device.devid}','STOP','DOWN')">
							<img src="${ctx}/css/player/image/arrow_hover03.png" title="向左" onmousedown="operateChannel('${device.devid}','LEFT',5)" onmouseup="operateChannel('${device.devid}','STOP','LEFT')">
							<img src="${ctx}/css/player/image/arrow_hover04.png" title="向右" onmousedown="operateChannel('${device.devid}','RIGHT',5)" onmouseup="operateChannel('${device.devid}','STOP','RIGHT')">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<img src="${ctx}/css/player/image/aperture_plus_hover02.png" title="放大图象" onmousedown="operateChannel('${device.devid}','ZOOMIN',5)" onmouseup="operateChannel('${device.devid}','STOP','ZOOMIN')">
							<img src="${ctx}/css/player/image/aperture_minus_hover01.png" title="缩小图象" onmousedown="operateChannel('${device.devid}','ZOOMOUT',5)" onmouseup="operateChannel('${device.devid}','STOP','ZOOMOUT')">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<img src="${ctx}/css/player/image/bright_near_hover01.png" title="放大光圈" onmousedown="operateChannel('${device.devid}','LARGE',5)" onmouseup="operateChannel('${device.devid}','STOP','LARGE')">
							<img src="${ctx}/css/player/image/bright_far_hover02.png" title="缩小光圈" onmousedown="operateChannel('${device.devid}','SMALL',5)" onmouseup="operateChannel('${device.devid}','STOP','SMALL')">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<img src="${ctx}/css/player/image/focus_near_hover01.png" title="放大焦点" onmousedown="operateChannel('${device.devid}','FOCUSOUT',5)" onmouseup="operateChannel('${device.devid}','STOP','FOCUSOUT')">
							<img src="${ctx}/css/player/image/focus_far_hover02.png" title="缩小焦点" onmousedown="operateChannel('${device.devid}','FOCUSIN',5)" onmouseup="operateChannel('${device.devid}','STOP','FOCUSIN')">
						</td>
					</tr>
				</table>
			</td>
			<c:if test="${status.last}">
				<c:if test="${(param.num==9 && status.count%3!=0)}">
					<td style="text-align: center;vertical-align: middle;" colspan="${3-status.count%3}">&nbsp;</td>
				</c:if>
				<c:if test="${(param.num==4 && status.count%2!=0)}">
					<td style="text-align: center;vertical-align: middle;" colspan="${2-status.count%2}">&nbsp;</td>
				</c:if>
			</c:if>
			<c:if test="${status.last || (param.num==9 && status.count%3==0) || (param.num==4 && status.count%2==0) }">
				</tr>
			</c:if>
		</c:forEach>
	</table>
</div>
</body>
</html>