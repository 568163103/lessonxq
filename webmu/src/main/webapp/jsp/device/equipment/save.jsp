<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/headFormDiv.jsp"%>

<title>${global_app_name}</title>
<script type="text/javascript">
	function edit() {
		if (confirm("确认要新增？")) {
			$("#pageForm").submit();
		}
	}
	
	
	
	function selectSupp(){
		$("#corp").empty();
		var objS = document.getElementById("type");//获取配送员的信息
		var type = objS.options[objS.selectedIndex].value;//获取配送员下拉选定的数据
		 //根据地址和配送员信息发送一个ajax查询，获取相应的配送员信息
		 $.ajax({
			 url:'${ctx}/device/equipment!findEquipmentCorp.do?method=selectBdSupp',
			 data:{
					type:type
					},
			success : function(data) {//将查询到的供应商信息放在供应商的select标签框中
				if(data.length!=0){//绑定的供应商显示
					for(var i=0;i<data.length;i++){
						$("#corp").append("<option value = "+data[i].id+">"+data[i].name+"</option>");
					}
				}else{
					document.getElementById("type").innerHTML
					= "<option value = "+""+">"+null+"</option>" ;
					document.getElementById("corp").innerHTML
					= "<option value = "+""+">"+null+"</option>" ;
					 $.messager.alert('请选择设备类型', '该设备类型无信息','info');
					return;
				}
			}
		 }); 

	 } 
</script>
</head>
<body class="main_bg">
	<div class="win-header" id="a">
		<span class="win-header-title">设备管理 &gt; 其他设备管理 &gt; 添加其他设备</span>
	</div>
	<div class="win-bodyer">
	<form id="pageForm" name="pageForm" action="${ctx}/device/equipment!save.do" method="post">
		<s:token/>
		<table width="90%" class="inputTable">
			<tr>
				<td style="text-align: right"><label class="field-request">* </label>位置：</td>
				<td width="20%">
					<select class="validate[required]" name="equipment.position" id="position">
						
						<c:forEach var="type" items="${positions}">
							<option value="${type.key}" <c:if test="${equipment.position eq type.key}">selected</c:if>>${type.value}</option>
						</c:forEach>
					</select>
				</td>
				<td style="text-align:right;" width="10%"><label class="field-request">* </label>设备类型：</td>
				<td width="20%">
					<select class="validate[required]" name="equipment.type" id="type" onchange="selectSupp()">
						<option value="">请选择</option>
						<c:forEach var="type" items="${equipmentTypes}">
							<option value="${type.key}" <c:if test="${equipment.type eq type.key}">selected</c:if>>${type.value}</option>
						</c:forEach>
					</select>
				</td>
				
			</tr>
			<tr>
				<td style="text-align: right;" width="10%"><label class="field-request">* </label>设备名称：</td>
				<td width="20%"><input type="text" value="${equipment.name}" class="validate[required,ajax[checkUnique]]" name="equipment.name" id="equipment_name" maxlength="32"/></td>
				<td style="text-align:right;" width="10%"><label class="field-request">* </label>设备厂家：</td>
				<td width="20%">
					<select class="validate[required]" name="equipment.corp" id="corp">		
						<option value="">请先选择设备类型</option>					
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;"><label class="field-request">* </label>ip：</td>
				<td><input type="text" value="${equipment.ip}" class="validate[required,custom[ipv4]]" name="equipment.ip" id="ip" /></td>
				<td style="text-align: right;">端口：</td>
				<td><input type="text" value="${equipment.port}"  name="equipment.port" id="port" /></td>
			</tr>
			
			<tr>
				<td style="text-align:right;">物理位置：</td>
				<td>
					<input type="text" name="equipment.pos" id="pos" value="${equipment.pos}">
				</td>
				<td style="text-align:right;">设备型号：</td>
					<td>
					<input type="text" name="equipment.version" id="version" value="${equipment.version}">
				</td>
			</tr>
		
				<tr>
					
					<td style="text-align:right;">MAC地址：</td>
					<td colspan = "3">
						<input type="text" name="equipment.mac" id="mac" value="${equipment.mac}">
					</td>
					
				</tr>
			<tr>
			<td style="text-align:right;">备注(最多256字符)：</td>
					<td colspan = "3">
					<textarea name="equipment.remark" id="remark" value="${equipment.remark}" class="validate[maxSize[256]]" style="width:90%;" rows="3">${equipment.remark}</textarea>
			</td>
			</tr>
			
			<tr>
				<td colspan="4" style="text-align:center;">
					<a class="a_button" href="javascript:void(0)" onclick="edit()"><span>保存</span></a>&nbsp;&nbsp;
					<a class="a_button" href="${ctx}/device/equipment!findPage.do"><span>返回</span></a>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>