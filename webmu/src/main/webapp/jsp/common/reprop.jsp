<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.beyeon.common.config.ResourceUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${global_app_name}－资源文件管理</title>
<style type="text/css">
.sortable * {margin:0; padding:0; outline:none}
.sortable thead tr {text-align:left; color:#0ccccc; border:1px solid #fff; border-right:none}
.sortable thead tr {font-size:15px; padding:1px 2px 2px}
.sortable {width:980px;font:12px Verdana,Arial;color:red;  border-left:1px solid #c6d5e1; border-top:1px solid #c6d5e1; border-bottom:none; margin:0 auto 15px}
.sortable td {padding:4px 6px 6px; border-bottom:1px solid #c6d5e1; border-right:1px solid #c6d5e1}
.sortable .evenrow td {background:#fff}
.sortable .oddrow td {background:#ecf2f6}
.sortable td.evenselected {background:#ecf2f6}
.sortable td.oddselected {background:#dce6ee}
</style>
</head>
<body class="main_bg">
<table class="sortable">
<thead>
<tr>
<td colspan="5"><font size="5">资源文件</font>(点击链接可重新加载相应的配制文件,点击<a href="reprop.jsp?refreshAll=true">这里</a>重新加载所有配制文件)</td>
</tr>
</thead>
<tr><td colspan="5">
<%
String manualConfig = ResourceUtil.getManualConfig();
if(StringUtils.isNotBlank(request.getParameter("fileName"))){
	if(ResourceUtil.isModified(request.getParameter("fileName"))){
		out.println("刷新成功！");
	}else{
		out.println("文件无变化！");
	}
}
if(StringUtils.isNotBlank(request.getParameter("refreshAll"))){
	if (StringUtils.isNotBlank(manualConfig)) {
		StringBuffer changeFiles = new StringBuffer("有变化文件:");
		StringBuffer unChangeFiles = new StringBuffer("无变化文件:");
		String[] manualConfigs = ResourceUtil.getFileList(manualConfig);
		for (int i = 0; i < manualConfigs.length; i++) {
			if(ResourceUtil.isModified(manualConfigs[i])){
				changeFiles.append(manualConfigs[i]).append("&nbsp;&nbsp;");
			}else{
				unChangeFiles.append(manualConfigs[i]).append("&nbsp;&nbsp;");
			}
		}
		out.println(changeFiles.toString());
		//out.println("<br/>");
		//out.println(unChangeFiles.toString());
	}
}
%>
</td></tr>
<%
if (StringUtils.isNotBlank(manualConfig)) {
	String[] manualConfigs = ResourceUtil.getFileList(manualConfig);
	for (int i = 1; i < manualConfigs.length+1; i++) {
		if(i%5==1){
			out.print("<tr>");
		}
		out.print("<td><a href='reprop.jsp?fileName="+manualConfigs[i-1]+"'>"+manualConfigs[i-1]+"</a></td>");
		if(i==manualConfigs.length){
			for(int j =0;j<(5-i%5) && (i%5)!=0;j++){
				out.print("<td>---</td>");
			}
		}
		if(i%5==0 || i==manualConfigs.length){
			out.print("</tr>");
		}
	}
}
%>
</table>
</body>
</html>