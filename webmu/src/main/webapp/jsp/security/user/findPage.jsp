<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.beyeon.common.web.control.action.BaseAction"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
<c:if test="${not empty param.isAdmin}">
	<c:if test="${'1' eq param.isAdmin}">
	<c:set var="isAdmin" value="${param.isAdmin}" scope="session"/>
	</c:if>
	<c:if test="${'0' eq param.isAdmin}">
		<c:set var="isAdmin" value="" scope="session"/>
	</c:if>
</c:if>
<title>${global_app_name}</title>
<script type="text/javascript">

	function checkedItems() {
		var flag = false;
		$("#pageForm").find("[name=items]").each(function() {
			if (this.checked) {
				flag = true;
			}
		});
		return flag;
	}
	
	function deleteAll() {
		if (!checkedItems()) {
			alert("要删除管理员，请至少选择其中一个。");
			return false;
		}
		msg = "确认要删除所选管理员吗？";
		if (window.confirm(msg)) {
			showSecValidation("${ctx}/security/user!delete.do");
		}
	}
	
	function editUser(id) {
		$("#pageForm").find("[name=amid]").val(id);
		showSecValidation("${ctx}/security/user!beforeUpdate.do");
	}
	function beforeAuthForUser(id,username) {
		$("#pageForm").find("[name=amid]").val(id);
		$("#pageForm").find("[name=username]").val(username);
		$("#pageForm").attr("action","${ctx}/security/user!beforeAuthForUser.do");
		$("#pageForm").submit();
	}
	
	function unfreezeUser(username) {
		$.post("${ctx}/security/user!unfreezeUser.do?username="+username,null,function(data){
			if("true" == data){
				alert("用户已解冻");
			} else {
				alert("用户解冻失败");
			}
		});
	}

	function initFilaLineBusTree(){
		window.location.href="${ctx}/bussiness/usertree!beforeInitUserResourceTree.do";
	}
		
	function showSecValidation(path) {
		jPrompt('验证密码:', '', '二次身份验证', function(r) {
			if(r == "") {
				alert("提示：验证密码不能为空！");
				return false;
			}else if(r == null){
				return true;
			}else{
				$.ajax({  
                    type : "POST",  //提交方式  
                    url : "${ctx}/security/user!checkPsw.do",//路径  
                    data : {  
                        "password" : r  
                    },//数据，这里使用的是Json格式进行传输  
                    success : function(result) {//返回数据根据结果进行相应的处理  
                        if ( result.code=='200' ) {  
                        	document.pageForm.action=path;
            				document.pageForm.submit();
            	   			return true; 
                        } else {  
                        	alert("提示：密码错误！");
                        }  
                    }  
                });  
				
			}
		});
	}
	
	function prohibited(id,proStatus) {
		$('#userInfo_id').val(id);
		if (proStatus == 1){
			proStatus = 0;
		}else if(proStatus == 0){
			proStatus = 1;
		}
		$("#userInfo_prohibited").val(proStatus);
		var url = "${ctx}/security/user!getProStatus.do?userInfo_id="+id+'&user_userInfo.prohibited_status='+proStatus;
		$.get(url,function (result) {});
		$("#pageForm").attr("action","${ctx}/security/user!updateProStatus.do");
		$("#pageForm").submit();
		$("#pageForm").attr("action","${ctx}/security/user!findPage.do");
	}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title"><c:if test="${not empty isAdmin}">权限管理</c:if><c:if test="${empty isAdmin}">业务管理</c:if> &gt; 用户管理 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/security/user!findPage.do" method="post">
		<input type="hidden" name="amid">
		<input type="hidden" name="username">
		
		<fieldset style="width: 98%">
			<legend>&nbsp;&nbsp;查询&nbsp;&nbsp;</legend>
			<table width="100%" class="inputTable">
				<tr align="right">
					<td style="text-align: right">账号：</td>
					<td><input type="text" id="pageObject.params.userName" name="pageObject.params.userName" value="${pageObject.params.userName}"></td>
					<td style="text-align: right">位置：</td>
					<td>
						<select name="pageObject.params.position" id="pageObject.params.position">
							<option value="">全部</option>
							<c:forEach var="type" items="${positions}">
								<option value="${type.key}" <c:if test="${pageObject.params.position eq type.key}">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
					<td><a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();"><span>查询</span></a></td>
				</tr>
			</table>
		</fieldset>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="10%"><div>管理员ID</div></th>
						<th>管理员帐号</th>
						<th>管理员姓名</th>
						<th>位置</th>
						<th width="5%">云台级别</th>
						<th width="5%">用户级别</th>
						<th><div>电话号码</div></th>
						<th width="10%">邮箱</th>
						<th width="10%">活跃时间</th>
						<th width="5%">是否在线</th>
						<th width="18%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="userInfo" items="${pageObject.resultList}">
			    	<tr>
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${userInfo.name}"/>
				    	</td>
				    	<td>${userInfo.id}</td>
				    	<td>${userInfo.name}</td>
				    	<td>${userInfo.alias}</td>
						<td>${userInfo.positionZH}</td>
						<td>${userInfo.ptzLevel}</td>
						<td>${userInfo.userLevel}</td>
				    	<td>${userInfo.phone}</td>
				    	<td>${userInfo.mail}</td>
						<td>${userInfo.activeBeginTime}~${userInfo.activeEndTime}</td>
						<td>${userInfo.statusZh}</td>
				    	<td>
					    	<a href="javascript:editUser('${userInfo.id}')">编辑</a>&nbsp;&nbsp;
							<c:if test="${not empty isAdmin}">
					    	<a href="javascript:beforeAuthForUser('${userInfo.id}','${userInfo.name}')">授权</a>&nbsp;&nbsp;
					    	<a href="javascript:unfreezeUser('${userInfo.name}')">解冻</a>&nbsp;&nbsp;
							</c:if>
							<c:if test="${empty isAdmin}">
							<a href="${ctx}/bussiness/usertree!beforeUpdateUserGroup.do?amid=${userInfo.id}"><span>用户分组</span></a>&nbsp;&nbsp;
							<a href="${ctx}/bussiness/usertree!beforeAuthResourceForUser.do?uid=${userInfo.id}">自定义目录树</a>&nbsp;&nbsp;
							<a href="${ctx}/bussiness/usertree!beforeCopyUserTreeToUser.do?uid=${userInfo.id}">复制自定义目录树</a>&nbsp;&nbsp;
							<a href="${ctx}/bussiness/usertree!exportList.do?uid=${userInfo.id}"><span>导出目录资源</span></a>
							</c:if>
							<c:if test="${userInfo.prohibited_status ==1}">
								<a id="prohibited${userInfo.id}" href="#" onclick="prohibited('${userInfo.id}','${userInfo.prohibited_status}')">禁止外设</a>

							</c:if>
							<c:if test="${userInfo.prohibited_status ==0}">
								<a id="prohibited${userInfo.id}"  href="#" onclick="prohibited('${userInfo.id}','${userInfo.prohibited_status}')">取消禁止</a>
							</c:if>
						</td>

			    	</tr>
				</c:forEach>
				<input type="hidden" id="userInfo_id" name="userInfo_id" value="">
				<input type="hidden" id="userInfo_prohibited"  name="user_userInfo.prohibited_status" value="">
				</tbody>
				<tfoot>
					<tr>
						<td colspan="12">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="12" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="#" onclick="showSecValidation('${ctx}/security/user!beforeSave.do')"><span>添加</span></a>&nbsp;&nbsp;
							<a class="a_button" href="javascript:void(0);" onclick="return deleteAll();"><span>删除</span></a>&nbsp;&nbsp;
							<c:if test="${empty isAdmin}">
								<a class="a_button" href="${ctx}/bussiness/usertree!findPageUserForAuthGroups.do"><span>授权资源组</span></a>&nbsp;&nbsp;
							</c:if>
							<c:if test="${empty isAdmin}">
								<a class="a_button" href="${ctx}/bussiness/usertree!findPageUserForAuthAlarmRes.do"><span>告警资源订阅</span></a>
							</c:if>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>