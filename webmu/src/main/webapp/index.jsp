<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common/style.css"/>
<script type="text/javascript">
	<c:choose>
	<c:when test="${empty SPRING_SECURITY_CONTEXT}">
	window.location.href = '${ctx}/login.jsp';
	</c:when>
	<c:otherwise>
	<c:if test='${empty parentId || "" eq parentId}'>
	window.location.href = '${ctx}/welcome.jsp';
	</c:if>
	
	</c:otherwise>
	</c:choose>
</script>
<script type="text/javascript">
	var f = ${flag};
	var a = ${available};
	$( function() {
		if (a!=0){
			window.location.href = '${ctx}/jsp/common/systemsn/changeSn.do'
		}else if (f != 0){
			window.location.href = '${ctx}/jsp/security/password/changePasswd.do?flag='+f;
		}
	})
</script>
<title>${global_app_name}</title>
</head>
<frameset rows="74,*" name="winFrameSet" id="winFrameSet"	class="outFrame" frameborder="no" border="0" framespacing="0">
	<frame name="topFrame" id="topFrame" title="topFrame" src="${ctx}/common/module!top.do?parentId=${parentId}" scrolling="No" noresize="noresize" />
	<frameset cols="149,10,*" name="mainFrameSet" id="mainFrameSet" frameborder="no" border="0" framespacing="0">
	
		<frame name="leftFrame" id="leftFrame" title="leftFrame" src="${ctx}/common/module!left.do?parentId=${parentId}" scrolling="yes" noresize="noresize" />
		<frame name="midleFrame" id="midleFrame" title="midleFrame" src="${ctx }/middle.jsp" scrolling="yes" noresize="noresize" />
		<frame name="rightFrame" id="rightFrame" title="rightFrame" src="${ctx }/right.jsp" scrolling="yes" noresize="noresize" />
	</frameset>
</frameset>
<noframes>
	<body>
	
	</body>
</noframes>
</html>