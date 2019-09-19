<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<title>${global_app_name}</title>
<script type="text/javascript">
	function exportList(){
		$("#pageForm").attr("action","${ctx}/logs/userTrace!exportList.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/logs/userTrace!findPage.do");
	}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">日志管理 &gt; 操作日志 </span>
	</div>
	<div class="win-bodyer">
		<form id="pageForm" name="pageForm" action="${ctx}/logs/userTrace!findPage.do" method="post">
		<div class="win-advsearch">
			<table width="100%">
				<tr>
					<td>用户编码：
						<input type="text" class="validate[required]" id="pageObject.params.amid" name="pageObject.params.amid" value="${pageObject.params.amid }"/>
					</td>	
					<td>用户名：
						<input type="text" class="validate[required]" id="pageObject.params.userName" name="pageObject.params.userName" value="${pageObject.params.userName }"/>
					</td>	
					<td>登录IP：
						<input type="text" id="pageObject.params.terminalIp" name="pageObject.params.terminalIp" value="${pageObject.params.terminalIp }"/>
					</td>					
					<td>操作起止时间：
						<input class="Wdate" type="text" id="qssj" name="pageObject.params.openTimeOne" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'jsjs\')||\'%y-%M-%d\'}'})" value="${pageObject.params.openTimeOne }"/>&nbsp;至
						<input class="Wdate" type="text" id="jsjs" name="pageObject.params.openTimeTwo" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'qssj\')||\'%y-%M-%d\'}'})" value="${pageObject.params.openTimeTwo }"/>
					</td>
					<td>操作类型：
						<select id="pageObject.params.userTrace" name="pageObject.params.userTrace">
							<option value="">全部</option>
							<c:forEach var="type" items="${types}">
								<option value="${type.value}" <c:if test="${pageObject.params.userTrace eq type.value}">selected</c:if>>${type.label}</option>
							</c:forEach>
						</select>
					</td>
					<td align="right">
						<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>查询</span></a>
					</td>
				</tr>
			</table>
		</div>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th width="10%"><div>用户编码</div></th>
						<th width="10%">用户名</th>
						<th width="10%">登录IP</th>
						<th width="10%"><div>操作类型</div></th>
						<th width="40%"><div>操作详细信息</div></th>
						
						<th width="15%">操作时间</th>
						<th width="5%">起止时间</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="userTrace" items="${pageObject.resultList}">
			    	<tr>
				    	<td>${userTrace.amid}</td>
				    	<td>${userTrace.userName}</td>
						<td>${userTrace.terminalIp}</td>
				    	<td>${userTrace.menuName}</td>
						<td>${userTrace.userTrace}</td>
						
				    	<td><fmt:formatDate value="${userTrace.operateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td></td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="17" style="text-align: center;vertical-align: top;height: 35px;border-right: 0px solid #cfcfcf;">							
							<a class="a_button" href="javascript:void(0);" onclick="return exportList();"><span>导出</span></a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>