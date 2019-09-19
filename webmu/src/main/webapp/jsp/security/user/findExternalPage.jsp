<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.beyeon.common.web.control.action.BaseAction"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headPage.jsp"%>
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
		
	function getUserCurState() {
		var i=0;
		var id;
		$("#pageForm").find("[name=items]").each(function() {
			if (this.checked) {
				i++;
				id=this.value;
			}
		});
		if (i>1) {
			alert("不能选择多个查看。");
			return false;
		}else if(i==0){
			alert("请选择要查看的用户。");
			return false;
		}
		$.ajax({  
                    type : "POST",  //提交方式  
                    url : "${ctx}/security/user!getUserCurState.do",//路径  
                    data : {  
                        "item" : id,"serverId" : $("#serverId").val()  
                    },//数据，这里使用的是Json格式进行传输  
                    success : function(result) {//返回数据根据结果进行相应的处理  
                        alert(result.code);
                        if (result.code=='200' ) {  
                        	jAlert(result.data,"用户状态");
                        } else {  
                        	alert("获取用户动态信息失败！");
                        }  
                    }  
                });  

	}
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">外域设备管理 &gt; 接入网关&gt; 用户资源 </span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/security/user!findExternalPage.do" method="post">
		<input type="hidden" name="serverId" value="${serverId }">
		<table width="100%">
				<tr align="right">
					<td>
					</td>
					<td style="text-align: right">
						账号：<input type="text" id="pageObject.params.userName" name="pageObject.params.userName" value="${pageObject.params.userName}">
						&nbsp;&nbsp;
						<a class="a_button" href="javascript:void(0)" onclick="$('#pageForm').submit();">
							<span>查找</span>
						</a>
					</td>
				</tr>
		</table>
		<div id="pageDiv" name="pageDiv" class="pageDiv">
			<input type="hidden" name="serverId" id="serverId" value="${serverId }">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="center" width="3%"><input type="checkbox" name="CheckedAll" class="CheckedAll"/></th>
						<th width="10%"><div>用户ID</div></th>
						<th width="10%">用户名称</th>
						<th width="10%">所属服务器</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="userInfo" items="${pageObject.resultList}">
			    	<tr>
				    	<td class="center">
				    		<input type="checkbox" name="items" class="items" value="${userInfo[0]}"/>
				    	</td>
				    	<td>${userInfo[0]}</td>
				    	<td>${userInfo[1]}</td>
				    	<td>${userInfo[3]}</td>
			    	</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
							<jsp:include page="/jsp/common/pageinfo.jsp?objectName=pageObject" />
						</td>
					</tr>
					<tr>
						<td colspan="4" style="text-align: center;vertical-align: top;height: 35px">
							<a class="a_button" href="javascript:void(0);" onclick="return getUserCurState()"><span>用户动态</span></a>&nbsp;&nbsp;
							<a class="a_button" href="${ctx}/device/encoder!findPlatformPage.do"><span>返回</span></a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
	</div>
</body>
</html>