<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/head/head.jsp"%>
<script type="text/javascript">
	<c:choose>
		<c:when test="${empty SPRING_SECURITY_CONTEXT}">
			window.location.href='${ctx}/login.jsp';
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${!SPRING_SECURITY_CONTEXT.authentication.principal.selfManager}">
					top.location.href='${ctx}/common/module!index.do?parentId=1';
				</c:when>
				<c:otherwise>
					top.location.href='${ctx}/common/module!selfManageIndex.do?parentId=1';
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</script>