<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/jsp/common/head/headFormDiv.jsp"%>
	<link rel="stylesheet" href="${ctx}/css/dynatree/ui.dynatree.css" />
	<script type="text/javascript" src="${ctx}/js/jquery/ext/jquery.dynatree.js"></script>
	<style type="text/css">
		textarea {
			width: 203px;
		}
	</style>
	<title>${global_app_name}</title>
	<script type="text/javascript">
		var isOpen = false;
		function openViewMenu(rid){
			isOpen = true;
			$("#main-iframe").attr("src","${ctx}/security/menu!viewMenu.do?rid="+rid);
		}
		function edit() {
			if (confirm("确认要更新？")) {
				$("#pageForm").submit();
			}
		}
		$(function(){
			console.info(${childrenCodes});
			$("#main-iframe").height(300);
			$("#pageForm").validationEngine();
			$("#viewMenu").dialog({
				title: "包含功能信息",
				autoOpen: false,
				width : 500,
				modal: true
			});

			var userRoleIds = ${userRoleIds};
			$("[name='rids']").val(userRoleIds[0]);
			var userTypes = $("[name='userInfo.userType']");
			userTypes.each(function(){
				if($(this).val()=='${userInfo.userType}') {
					$(this).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}
			});
			$("#main-iframe").load(function(){
				if(isOpen){
					$("#viewMenu").dialog("open");
				}
			});
		});
	</script>
</head>
<body class="main_bg">
<div class="win-header" id="a">
	<span class="win-header-title">权限管理 &gt; 用户管理 &gt; 编辑用户</span>
</div>
<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/security/user!update.do" method="post">
		<input type="hidden" id="amid" name="amid" value="${userInfo.id}">
		<table width="100%" class="inputTable">
			<tr>
				<td style="text-align: right;" width="22%"><label class="field-request">* </label>管理员账号：</td>
				<td width="30%"><input type="text" value="${userInfo.name}" readonly="readonly" name="userInfo.name" id="user" /></td>
				<td style="text-align: right;" width="18%"><label class="field-request">* </label>管理员姓名：</td>
				<td width="30%"><input type="text" value="${userInfo.alias}" class="validate[required]" name="userInfo.alias" id="alias" /></td>
			</tr>
			<tr>
				<td style="text-align: right;"><label class="field-request">* </label>密码：</td>
				<td><input type="password" value="abc222" class="validate[required,maxSize[64]]" name="userInfo.password" id="password" /> <input
						type="hidden" value="${userInfo.passwd}" name="userInfo.passwd" id="userInfo.passwd" /></td>
				<td style="text-align: right;"><label class="field-request">* </label>确认密码：</td>
				<td><input type="password" value="abc222" class="validate[required,equals[password]]" name="password1" id="password1" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">电话号码：</td>
				<td><input type="text" value="${userInfo.phone}" class="validate[custom[phone]]" name="userInfo.phone" id="phone" /></td>
				<td style="text-align: right;">单位：</td>
				<td><input type="text" value="${userInfo.mail}" name="userInfo.mail" id="mail" /></td>
			</tr>
			<tr>
				<td style="text-align:right;"><label class="field-request">* </label>云台级别：</td>
				<td>
					<input type="text" value="${userInfo.ptzLevel}" class="validate[required,custom[onlyNumberSp],max[63]]" name="userInfo.ptzLevel" id="ptzLevel" value="1"/>
				</td>
				<td style="text-align:right;"><label class="field-request">* </label>用户级别：</td>
				<td>
					<input type="text" value="${userInfo.userLevel}" class="validate[required,custom[onlyNumberSp],max[63]]" name="userInfo.userLevel" id="userLevel" value="1"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:right;"><label class="field-request">* </label>最大视频路数：</td>
				<td>
					<input type="text" value="${userInfo.maxCameraNum}" class="validate[required,custom[onlyNumberSp]]" name="userInfo.maxCameraNum" id="maxCameraNum" value="16"/>
				</td>
				<td style="text-align:right;"><label class="field-request">* </label>云台锁定时间：</td>
				<td>
					<input type="text" value="${userInfo.ptzLockTime}" class="validate[required,custom[onlyNumberSp],max[600],min[60]]" name="userInfo.ptzLockTime" id="ptzLockTime" value="1"/><label class="field-request">(秒) </label>
				</td>
			</tr>
			<tr>
				<td style="text-align:right;">角色类型：</td>
				<td>
					<select id="rids" name="rids">
						<c:forEach var="role" items="${roles}" varStatus="status">
							<option value="${role.rid}">${role.rname}</option>
						</c:forEach>
					</select>
				</td>
				<td style="text-align:right;">音视频级别：</td>
				<td>
					<select name="userInfo.avLevel" id="avLevel">
						<option value="">请选择</option>
						<c:forEach var="avLevel" items="${avLevels}">
							<option value="${avLevel.value}" <c:if test="${userInfo.avLevel eq avLevel.value}">selected</c:if>>${avLevel.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align:right;">活跃开始时间：</td>
				<td>
					<input type="text" value="${userInfo.activeBeginTime}" name="userInfo.activeBeginTime" id="activeBeginTime" class="Wdate" onmousedown="WdatePicker({readOnly:'true',dateFmt:'HH:mm:ss'})"/>
				</td>
				<td style="text-align:right;">活跃结束时间：</td>
				<td>
					<input type="text" value="${userInfo.activeEndTime}" name="userInfo.activeEndTime" id="activeEndTime" class="Wdate" onmousedown="WdatePicker({readOnly:'true',dateFmt:'HH:mm:ss'})"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:right;"><label class="field-request">* </label>业务部门：</td>
				<td>
					<select name="userInfo.tbDept" id="tbDept" class="validate[required]">
						<c:forEach var="tbDept" items="${tbDepts}">
							<option value="${tbDept.value}" <c:if test="${userInfo.tbDept eq tbDept.value}">selected</c:if>>${tbDept.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>

				<td style="text-align:right;">用户类型：</td>
				<td colspan="3">
					<input type="radio" name="userInfo.userType" id="userType1" value="1">默认用户
					<input type="radio" name="userInfo.userType" id="userType2" value="2"/>GIS用户
				</td>
			</tr>
			<tr>
				<c:set var="descriptionRowNum" value="3"/>
				<c:if test="${not empty corps}">
					<td style="text-align:right;">所属单位：</td>
					<td>
						<select id="corpId" name="corpId">
							<option value="">请选择</option>
							<c:forEach var="corp" items="${corps}" varStatus="status">
								<option value="${corp.cid}" <c:if test="${corpId eq corp.cid}">selected</c:if> >${corp.cname}</option>
							</c:forEach>
						</select>
					</td>
					<c:set var="descriptionRowNum" value="1"/>
				</c:if>
				<td style="text-align:right;">备注(最多256字符)：</td>
				<td colspan="${descriptionRowNum}">
					<textarea name="userInfo.description" id="description" value="${userInfo.description}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${userInfo.description}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center;">
					<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>更新</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/security/user!findPage.do"><span>返回</span></a></td>
			</tr>
		</table>
	</form>
</div>
<div id="viewMenu">
	<iframe id="main-iframe" width="100%" frameBorder="0" scrolling="auto" allowtransparency=yes src=""></iframe>
</div>
</body>
</html>