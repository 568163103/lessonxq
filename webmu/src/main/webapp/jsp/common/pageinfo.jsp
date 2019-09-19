<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.beyeon.common.web.model.util.PageObject"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value='${"/" eq pageContext.request.contextPath ? "" : pageContext.request.contextPath}'/>
<%
	String objectName = "pageObject";
	if(null != request.getParameter("objectName") && !"".equals(request.getParameter("objectName"))){
		objectName = request.getParameter("objectName");
	}
	request.setAttribute("objectName",objectName);
	PageObject pageObject = (PageObject)request.getAttribute(objectName);
	if(null != pageObject){
		request.setAttribute("pageObject",pageObject);
	}
%>

<input type="hidden" name="${objectName}.currentPageNum" class="pageObject.currentPageNum" value="${pageObject.currentPageNum}"/>
<input type="hidden" name="${objectName}.pageNum" class="pageObject.pageNum" value="${pageObject.pageNum}"/>
<input type="hidden" name="${objectName}.totalNum" class="pageObject.totalNum" value="${pageObject.totalNum}"/>
<input type="hidden" name="${objectName}.pageSizeTemp" class="pageObject.pageSizeTemp" value="${pageObject.pageSize}"/>
<input type="hidden" name="${objectName}.sortName" class="pageObject.sortName" value="${pageObject.sortName}"/>
<input type="hidden" name="${objectName}.sortOrder" class="pageObject.sortOrder" value="${pageObject.sortOrder}"/>
<input type="hidden" name="${objectName}.pageClick" class="pageObject.pageClick" value="0"/>
<input type="hidden" name="randomNum" class="randomNum" value="0"/>
<input type="hidden" name="objectName" class="objectName" value="${objectName}"/>
<div name="pager" class="pager_c"></div>