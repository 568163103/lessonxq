<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/head.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/dynatree/ui.dynatree.css" />
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.dynatree.js"></script>
	<link rel="stylesheet" href="${ctx}/css/player/player.css" />
	<title>${global_app_name}</title>
	<script type="text/javascript">
		var currChannelId = null,currChannelName = null,$playerIframe = null,currNode = null;
		function openPlayInIframe(channelId,channelName) {
			currChannelId = channelId;currChannelName = channelName;
			$("#imgflag").attr("title","暂停").attr("src","${ctx}/css/player/image/control_close.png");
			if(window.playerIframe.playerInstance){window.playerIframe.playerInstance.stop();}
			$playerIframe.loading("加载中，请稍后");
			$playerIframe.attr("src", "${ctx}/jsp/bussiness/display/player.jsp?mduIp=${mduIp}&mduPort=${mduPort}&channelId="+currChannelId+"&channelName="+currChannelName);
		}

		function childOpenPlayInIframe(channelId,channelName) {
			currChannelId = channelId;currChannelName = channelName;
			$("#imgflag").attr("title","暂停").attr("src","${ctx}/css/player/image/control_close.png");
			window.open ("${ctx}/jsp/bussiness/display/player.jsp?mduIp=${mduIp}&mduPort=${mduPort}&channelId="+currChannelId+"&channelName="+currChannelName+"&childOpen=true", "singleplayer" ,
					'width='+ (window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes' ) ;
		}

		function operateChannel(ho,action,step){
			if(ho.attr("disabled")){
				//return false;
			}
			if(null != currChannelId){
				//ho.attr("disabled",true);
				var url = "${ctx}/bussiness/display!operateChannelPtz.do?channelId="+currChannelId+"&action="+action+"&step="+step;
				$.post(url,function(data){
					if("OK" != data){
						alert("操作失败！");
					}
					//ho.attr("disabled",false);
				});
			} else {
				alert("请选择云镜");
			}
		}

		function playOrStop(obj){
			if(null == currChannelId){
				alert("请选择云镜");
				return;
			}
			window.playerIframe.playerInstance.stop();
			if($(obj).attr("title")=="暂停"){
				$(obj).attr("title","播放").attr("src","${ctx}/css/player/image/control_nomal.png");
			} else {
				$(obj).attr("title","暂停").attr("src","${ctx}/css/player/image/control_close.png");
				$playerIframe.loading("加载中，请稍后");
				$playerIframe.attr("src", "${ctx}/jsp/bussiness/display/player.jsp?mduIp=${mduIp}&mduPort=${mduPort}&channelId="+currChannelId+"&channelName="+currChannelName);
			}
		}

		$(function(){
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
						openPlayInIframe(keys[keys.length-1],currNode.data.title);
					}
				}
			});

			$playerIframe = $("#playerIframe");
			$playerIframe.load(function(){
				$playerIframe.loadhide();
				$playerIframe.contents().find("body").click(function(){
					window.document.body.click();
				});
			});
			openPlayInIframe("${channelId}", "${channelName}");
			$(".subwdzong").find("img").css("cursor","pointer");
		});
	</script>
</head>
<body class="main_bg">
<div class="win-bodyer" style="min-width: 1340px">
		<table width="99%" class="inputTable" border="0">
			<tr>
				<td id="leftTree" align="center" style="width:355px;height:98%;">
					<fieldset style="height:60%;">
						<legend>&nbsp;&nbsp;自定义目录树&nbsp;&nbsp;</legend>
						<table class="inputTable" border="0">
							<tr>
								<td align="center" style="padding-left: 2px">
									<div id="tree_0" style="padding-left: 2px;width:350px;height:440px;overflow:auto;"></div>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset style="width:355px;height: 270px">
						<legend>&nbsp;&nbsp;云镜控制&nbsp;&nbsp;</legend>
						<div class="subwdzong">
							<div class="subwdleft">
								<div class="button7">
									<img src="${ctx}/css/player/image/focus_near_hover01.png" title="焦点放大" onmousedown="operateChannel($(this),'FOCUSOUT',5)" onmouseup="operateChannel($(this),'STOP','FOCUSOUT')"/>
								</div>
								<div class="button8">
									<img src="${ctx}/css/player/image/focus_far_hover02.png" title="焦点缩小" onmousedown="operateChannel($(this),'FOCUSIN',5)" onmouseup="operateChannel($(this),'STOP','FOCUSIN')"/>
								</div>
							</div>
							<div class="subwdcenter">
								<div class="subwdall">
									<div class="subwd">
										<div class="left">
											<div class="button3">
												<img src="${ctx}/css/player/image/arrow_03.png" title="向左" onmousedown="operateChannel($(this),'LEFT',5)" onmouseup="operateChannel($(this),'STOP','LEFT')"/>
											</div>
										</div>
										<div class="center">
											<div class="button1">
												<img src="${ctx}/css/player/image/arrow_01.png" title="向上" onmousedown="operateChannel($(this),'UP',5)" onmouseup="operateChannel($(this),'STOP','UP')"/>
											</div>
											<div class="subnell">
												<img id="imgflag" src="${ctx}/css/player/image/control_nomal.png" title="播放" onclick="playOrStop(this)">
											</div>
											<div class="button2">
												<img src="${ctx}/css/player/image/arrow_02.png" title="向下" onmousedown="operateChannel($(this),'DOWN',5)" onmouseup="operateChannel($(this),'STOP','DOWN')"/>
											</div>
										</div>
										<div class="right">
											<div class="button4">
												<img src="${ctx}/css/player/image/arrow_04.png" title="向右" onmousedown="operateChannel($(this),'RIGHT',5)" onmouseup="operateChannel($(this),'STOP','RIGHT')"/>
											</div>
										</div>
									</div>
									<div class="subwd2">
										<div class="left2">
											<div class="button5">
												<img src="${ctx}/css/player/image/bright_near_hover01.png" title="光圈放大" onmousedown="operateChannel($(this),'LARGE',5)" onmouseup="operateChannel($(this),'STOP','LARGE')"/>
											</div>
										</div>
										<div class="right2">
											<div class="button6">
												<img src="${ctx}/css/player/image/bright_far_hover02.png" title="光圈缩小" onmousedown="operateChannel($(this),'SMALL',5)" onmouseup="operateChannel($(this),'STOP','SMALL')"/>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="subwdright">
								<div class="button9">
									<img src="${ctx}/css/player/image/aperture_plus_hover02.png" title="放大" onmousedown="operateChannel($(this),'ZOOMIN',5)" onmouseup="operateChannel($(this),'STOP','ZOOMIN')"/>
								</div>
								<div class="button10">
									<img src="${ctx}/css/player/image/aperture_minus_hover01.png" title="缩小" onmousedown="operateChannel($(this),'ZOOMOUT',5)" onmouseup="operateChannel($(this),'STOP','ZOOMOUT')"/>
								</div>
							</div>
						</div>
					</fieldset>
				</td>
				<td style="text-align: center;vertical-align: top" rowspan="1">
					<iframe id="playerIframe" name="playerIframe" width="1045px" height="660px" frameBorder="0" scrolling="auto" allowtransparency=yes src="${ctx }/jsp/bussiness/display/player.jsp"></iframe>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
