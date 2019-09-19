<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="/jsp/common/head/head.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/dynatree/ui.dynatree.css" />
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.dynatree.js"></script>
	<script type="text/javascript" src="${ctx}/js/bmap/bdmap.js?ak=A4749739227af1618f7b0d1b588c0e85"></script>
	<style type="text/css">
		#BaiduMap {
			width:100%;
			height:100%;
			overflow: hidden;
			margin:auto;
		}
	</style>
	<title>地图模式</title>
</head>
<body class="main_bg">
<div class="win-bodyer" style="min-width: 800px;height: 96%;">
	<table style="width: 100%;height: 100%;" class="inputTable" border="0">
		<tr>
			<c:if test="${param.a eq 'edit'}">
				<td align="center" style="width: 355px;vertical-align: top;padding-right: 10px;">
					<fieldset style="width: 355px;height: 100%;">
						<legend>&nbsp;&nbsp;自定义目录树&nbsp;&nbsp;</legend>
						<div id="tree_0" style="padding-left: 2px;width: 350px;height: 760px;overflow:auto;"></div>
					</fieldset>
				</td>
			</c:if>
			<td style="text-align: center;height: 100%;">
				<div id="BaiduMap"></div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
<script type="text/javascript">
	var baiduMap = null,imsGisInfos={},onLineChannelMarkers={};
	var tipsMarkerOptions = {
		icon: new BMap.Icon("${ctx}/images/maptips.png", new BMap.Size(32, 32))
	};
	var channelOnlineMarkerOptions = {
		icon: new BMap.Icon("${ctx}/images/channel_online.png", new BMap.Size(32, 32))
	};
	var channelOfflineMarkerOptions = {
		icon: new BMap.Icon("${ctx}/images/channel_offline.png", new BMap.Size(32, 32))
	};
	function initMarker(gisInfos,isDisplay,isDragend) {
		var minLongitude = 0,maxLongitude = 0,minLatitude = 0,maxLatitude = 0;
		$.each(gisInfos,function(i,gisInfo){
			imsGisInfos[gisInfo["channelId"]]=gisInfo;

			var id=gisInfo["channelId"],title=gisInfo["channelName"],
					longitude=gisInfo["longitude"], latitude=gisInfo["latitude"];
			if(0 == minLongitude || longitude < minLongitude){
				minLongitude = longitude;
			}
			if(longitude > maxLongitude){
				maxLongitude = longitude;
			}
			if(0 == minLatitude || latitude < minLatitude){
				minLatitude = latitude;
			}
			if(latitude > maxLatitude){
				maxLatitude = latitude;
			}
			var markerOptions = gisInfo["markerOptions"];
			if(!markerOptions){
				markerOptions = gisInfo["status"]?channelOnlineMarkerOptions:channelOfflineMarkerOptions;
			}

			var point = new BMap.Point(longitude, latitude);
			var channelMarker = new BMap.Marker(point, markerOptions);
			channelMarker.setAnimation(BMAP_ANIMATION_DROP);
			var label = new window.BMap.Label(title+" ("+longitude+" , "+latitude+")", {offset: new window.BMap.Size(33, 0)});
			channelMarker.setLabel(label);
			if(id.indexOf("http") < 0) {
				label.setStyle({ display : "none" });
			}
			<c:if test="${param.a eq 'edit'}">
			channelMarker.enableDragging();
			</c:if>
			channelMarker.addEventListener("click", function(){
				if(id.indexOf("http")>=0){
					window.open(id,'new');
				} else if('${param.h}'== "1"){
					window.open("${ctx}/bussiness/display!beforeHistoryDisplay.do?m=1&id="+id+"&recordPath=${param.p}",'flash');
				} else {
					window.open("${ctx}/bussiness/display!beforeLiveDisplay.do?m=1&id="+id,'flash');
				}
			});
			channelMarker.addEventListener("mouseover", function(e){
				this.setTop(true);
				this.getLabel().setStyle({ display : "block" });
			});
			channelMarker.addEventListener("mouseout", function(e){
				if(id.indexOf("http") < 0) {
					this.getLabel().setStyle({ display : "none" });
				}
				this.setTop(false);
			});
			channelMarker.addEventListener("dragging", function(e){
				this.getLabel().setContent(title+" ("+e.point.lng+" , "+e.point.lat+")");
			});
			channelMarker.addEventListener("dragend", function(e){
				$.post("${ctx}/bussiness/display!updateChannelPosition.do?id="+id+"&lng="+e.point.lng+"&lat="+e.point.lat,function(data){
					if(data != "ok"){
						alert("保存经纬度失败！");
						return;
					}
					imsGisInfos[id]["longitude"] = e.point.lng;imsGisInfos[id]["latitude"] = e.point.lat;
				});
			});
			if(isDisplay || id.indexOf("http") >= 0) {
				onLineChannelMarkers[id] = channelMarker;
				baiduMap.addOverlay(channelMarker);
			}
		});
		if(isDragend == undefined || !isDragend) {
			var point = new BMap.Point(113.606589, 34.799814);
			if (gisInfos.length > 0) {
				point = new BMap.Point((minLongitude + maxLongitude) / 2, (minLatitude + maxLatitude) / 2);
			}
			baiduMap.centerAndZoom(point, 14);
		}
	}
	function removeMarker(id) {
		if(id.indexOf("http") < 0) {
			if(onLineChannelMarkers[id]) {
				baiduMap.removeOverlay(onLineChannelMarkers[id]);
				onLineChannelMarkers[id] = null;
			}
		}
	}
	function initBaiduMap(){
		var isDisplay = false;
		<c:if test="${param.a ne 'edit'}">isDisplay = true;</c:if>
		var gisInfos = ${gisInfos};
		baiduMap = new BMap.Map("BaiduMap", {enableMapClick:false});
		baiduMap.enableScrollWheelZoom();
		baiduMap.addControl(new BMap.NavigationControl());
		
		/*
		<c:if test="${empty param.u}">
		gisInfos[gisInfos.length]={"channelId":"http://www.beyeon.com","channelName":"郑州蓝视科技有限公司",
			"longitude":113.606589,"latitude":34.799814,"markerOptions":tipsMarkerOptions};
		</c:if>
		*/
		
		initMarker(gisInfos,isDisplay);
		baiduMap.addEventListener("dragend", function(e){
			if(isDisplay){
				var bs = baiduMap.getBounds();   //获取可视区域
				var bssw = bs.getSouthWest();   //可视区域左下角
				var bsne = bs.getNorthEast();   //可视区域右上角
				$.post("${ctx}/bussiness/display!moveMapForPlay.do?longitude="+bssw.lng+";"+bsne.lng+"&latitude="+bssw.lat+";"+bsne.lat,function(data){
					if(data != "ok"){
						for (var key in onLineChannelMarkers) {
							removeMarker(key);
						}
						initMarker(data,true,true);
					}
				});
			}
		});
	}
	$(function(){
		initBaiduMap();
		<c:if test="${param.a eq 'edit'}">
		$("#tree_0").dynatree({
			checkbox: false,
			selectMode: 3,
			autoCollapse: false,
			fx: { height: "toggle", duration: 200 },
			imagePath:"${ctx}/css/dynatree/images/",
			children:${userResourceTree},
			onClick: function(dtnode) {
				var cnodes = [];
				if(null != dtnode.getChildren() && dtnode.getChildren().length > 0){
					cnodes = dtnode.getChildren();
				}
				var returnMarkers = [];
				$.each(cnodes,function(i,e){
					var keys = e.data.key.split("---");
					if(keys[keys.length-1].substring(6,9)==${channelValue}){
						if(dtnode.isExpanded()){
							removeMarker(keys[keys.length-1]);
						} else {
							returnMarkers[returnMarkers.length]=imsGisInfos[(keys[keys.length-1])];
						}
					}
				});
				initMarker(returnMarkers,true);
			}
		});
		</c:if>
	});
</script>
