<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value='${"/" eq pageContext.request.contextPath ? "" : pageContext.request.contextPath}'/>
<script type="text/javascript">var my_jwplayer_path = '${ctx}/flash/jwplayer/';</script>
<html>
<head>
	<meta name="renderer" content="webkit" >
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="${ctx}/css/alerts/jquery.alerts.css" />
	<script type="text/javascript">var context_path = '${ctx}';</script>
	<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.alerts.js"></script>
	<script type="text/javascript" src="${ctx}/flash/jwplayer/jwplayer.js" ></script>
	<script type="text/javascript">
		<c:set var="playHeight" value="98%"/>
		<c:if test="${not empty param.channelName}">
		<c:set var="channelName" value="${param.channelName}"/>
		</c:if>
		<c:if test="${not empty channelName}">
		<c:set var="playHeight" value="90%"/>
		</c:if>
		//平台、设备和操作系统
		var system = {
			win: false,
			mac: false,
			xll: false,
			ipad:false
		};
		//检测平台
		var p = navigator.platform;
		system.win = p.indexOf("Win") == 0;
		system.mac = p.indexOf("Mac") == 0;
		system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
		system.ipad = (navigator.userAgent.match(/iPad/i) != null)?true:false;
		var isPc = system.win || system.mac || system.xll||system.ipad;
		var welcomeImg = "${ctx}/css/player/image/display_mobile.png";
		if (isPc) {
			welcomeImg = "${ctx}/css/player/image/display.jpg";
		}

		var playerInstance = null,startPlay = false,playMonitor = null,playUrl = null;
		function startPlayMonitor (){
			startPlay = false;
			if(playMonitor != null){
				clearTimeout(playMonitor);
			}
			playMonitor = setTimeout(function(){
				if(!startPlay){
					playerInstance.stop();
					playerInstance.load([{file:playUrl,image:welcomeImg}]);
				}
			},10000);
		}
		function stopPlayMonitor (){
			if(playMonitor != null){
				clearTimeout(playMonitor);
			}
			//alert("播放出错");
			startPlay = false;
		}
		function play(mduIp,mduPort,channelId){
			if("" == mduIp || "" == mduPort || "" == channelId){
				return;
			}
			var isPlayed = false;
			try {
				isPlayed = Boolean(new ActiveXObject('ShockwaveFlash.ShockwaveFlash'));
			} catch(exception) {
				isPlayed = ('undefined' != typeof navigator.mimeTypes['application/x-shockwave-flash']);
			}
			if(isPlayed){
				doPlay("rtmp://"+mduIp+"/lskjapp/"+channelId);
			} else {
				var agent = navigator.userAgent;
				if(agent.indexOf("Windows") == -1){
					$.post("http://"+mduIp+":"+mduPort+"/lskjhlsapp/"+channelId+"/index.m3u8",function(data){
						doPlay(data);
					});
				} else {
					jConfirm("您没有安装flash,点击确认下载flash!","flash",function(e){
						if(e){
							window.open('https://get.adobe.com/flashplayer','flash');
						}
					});
				}
			}
		}

		function doPlay(currPlayUrl){
			playUrl = currPlayUrl;
			var div_container = $("#div_container").empty();
			var player = $("<div/>");
			$(player).attr("id", "player_id");
			div_container.append(player);
			var conf = {
				rtmp: {	bufferlength: 0.5	},
				primary: "flash",
				width: '100%',
				height: '${playHeight}',
				autostart: true,
				controls: true,
				analytics: { enabled: false,cookies: false},
				//image:welcomeImg,
				file: playUrl,
				events: {
					onComplete: function () { startPlay = false; },
					onVolume: function () {},
					onReady: function () {},
					onPlay: function () { startPlay = true; },
					onPause: function () {
						startPlay = true;
					},
					onBufferChange: function () {},
					onBufferFull: function () {startPlayMonitor();},
					onError: function (obj) {stopPlayMonitor ();},
					onFullscreen: function (obj) {
						<c:if test="${empty param.childOpen}">
						if (obj.fullscreen) {
							playerInstance.stop();
							window.parent.childOpenPlayInIframe('${param.channelId}','${param.channelName}');
							playerInstance.setFullscreen(false);
						}
						</c:if>
					},
					onMute: function (obj) {}
				}
			};
			playerInstance = jwplayer('player_id').setup(conf);
			//$("#div_container").animate({height:"540px",width:"960px"},100);
		}
		$(function(){
			$("#div_container_img").attr("src",welcomeImg);
			jwplayer.key = 'oaOBLlLUmzIIc37kmzB5kEadSJE8Zbny9SqwZQ==';
			<c:if test="${empty param.playUrl}">
			play("${empty param.mduIp ? mduIp:param.mduIp}","${empty param.mduPort ? mduPort:param.mduPort}","${empty param.channelId ? channelId:param.channelId}");
			</c:if>
			<c:if test="${not empty param.playUrl}">
			doPlay("${param.playUrl}");
			</c:if>
		});
	</script>
</head>
<body class="main_bg" style="margin:0px;">
<c:if test="${not empty channelName}">
	<div style="text-align: center;padding-bottom: 2%;font-weight: 900;font-size: 100%;height: 5%">${channelName}</div>
</c:if>
<div id="playerDiv" align="center">
	<div id="div_container">
		<img id="div_container_img" src="${ctx}/css/player/image/display_mobile.png" style="width:99%; height:${playHeight}; " />
	</div>
</div>
</body>
</html>
