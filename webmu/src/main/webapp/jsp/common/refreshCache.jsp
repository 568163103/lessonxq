<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.beyeon.common.config.ResourceUtil" %>
<%@ page import="com.beyeon.common.scheduling.SLSchedulerFactoryBean" %>
<%@ page import="com.beyeon.common.util.HttpClientUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String jobName = request.getParameter("jobName");
	if (StringUtils.isNotBlank(jobName)){
		try {
			String refresh = request.getParameter("refresh");
			if(StringUtils.isNotBlank(refresh)){
				SLSchedulerFactoryBean.runJob(jobName);
			} else {
				String[] routeIds = ResourceUtil.getPublicConf("route.id").split(",");
				for (String routeId : routeIds) {
					Map params = new HashMap();
					params.put("jobName", jobName);
					params.put("refresh", "true");
					try {
						HttpClientUtil.get(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() + "/jsp/common/refreshCache.jsp;jsessionid=1234567890." + routeId, params);
					} catch (Exception e){
					}
				}
			}
			out.print("ok");
		} catch (Exception e){
			out.print("fail");
		}
	} else {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/head.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
function refreshCache(jobName){
	$.post("${ctx}/common/module!refreshCache.do",{"jobName":jobName},function(data){
		if(typeof(data)=="string" && data.indexOf( "ok" ) > -1){
			alert("刷新成功！")
		} else {
			alert("刷新失败！");
		}
	});
}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">基础信息管理 &gt; 缓存刷新 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/common/module!refreshCache.do" method="post">
		<input type="hidden" id="amid" name="amid" value="${param.amid}">
		<fieldset title="缓存管理" style="width: 98%">
			<legend>&nbsp;&nbsp;缓存列表&nbsp;&nbsp;</legend>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			    <c:forEach var="role" items="${listJob}" varStatus="status">
					<c:if test="${(status.count % 6) == 1}">
					<tr style="line-height: 36px;">
					</c:if>
						<td style="text-align:right;">${role.value}&nbsp;</td>
						<td style="text-align:left;">
							<a href="javascript:void(0)" onclick="refreshCache('${role.key}');" >刷新</a>
						</td>
					<c:if test="${status.last && ((status.count%6) != 0)}">
						<td colspan="${(6-(status.count%6))*2}">&nbsp;</td>
					</c:if>
					<c:if test="${(status.count % 6) == 0 || status.last}">
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</fieldset>
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;操作&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr>
					<td colspan="6" style="text-align: center;vertical-align: top;height: 35px">
						<a class="a_button" href="javascript:void(0);" onclick="refreshCache('all')"><span>全部刷新</span></a></a>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	</div>
</body>
</html>
<%
	}
%>