<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="ctx" value='${"/" eq pageContext.request.contextPath ? "" : pageContext.request.contextPath}'/>

<meta name="renderer" content="webkit" >
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<link type="text/css" rel="stylesheet" href="${ctx}/css/common/global.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/common/global-ext.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery/jquery.ui.theme.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery/jquery.ui.core.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery/jquery.ui.resizable.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery/jquery.ui.button.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery/jquery.ui.dialog.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery/jquery.ui.tooltip.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/alerts/jquery.alerts.css" />

<link rel="shortcut icon" href="${ctx}/images/favicon.ico" type="image/x-icon"/>

<script type="text/javascript">
	var context_path = '${ctx}';var jsessionid = '${pageContext.session.id}';
	var app_smp_type_name = '${app_smp_type_name}';var app_grp_type_name = '${app_grp_type_name}';
</script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.core.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.widget.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.mouse.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.resizable.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.position.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.draggable.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.droppable.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.button.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.dialog.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.ui.tooltip.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.bgiframe.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.alerts.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.common.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		<c:if test="${not empty ACTIONMESSAGE}">
		alert("${ACTIONMESSAGE}");
		</c:if>
		$( document ).tooltip();
		if($(".main_bg").length>0) {
			$(window).unload(function () {
				$(window.document.body).loading("加载中，请稍后");
			});
		}
	})
</script>