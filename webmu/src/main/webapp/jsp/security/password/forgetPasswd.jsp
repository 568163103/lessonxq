<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  dir="ltr" lang="zh-CN">
<head>
	<title>${global_app_name}</title>
	<%@ include file="/jsp/common/head/headForm.jsp"%>
    <link type="text/css" href="${ctx}/css/common/pwd.css" rel="stylesheet" />
	<script type="text/javascript">
		$(document).ready(function(){
			$("#pageForm").validationEngine();
			$("input[name='verifyType']").click(function (){
				if($(this).val()==0){
					$("#mobile").validationEngine("hidePrompt");
					$("#mobile").removeClass("validate[required,custom[phone]]");
					$("#email").addClass("validate[required,custom[email]]");
					$("#pageForm").validationEngine("validate");
				} else if($(this).val()==1){
					$("#email").validationEngine("hidePrompt");
					$("#email").removeClass("validate[required,custom[email]]");
					$("#mobile").addClass("validate[required,custom[phone]]");
					$("#pageForm").validationEngine("validate");
				}
			});
		});
	</script>
</head>
	<body  class="main_bg">
	<form id="pageForm" name="pageForm" action="${ctx}/security/password!forgetPasswd.do" method="post">
		<div id="main" class="clearfix">
            <div id="main1" class="clearfix">
            	<ul class="m1_one clearfix" id="m1_one">
                	<li class="li_frist li_hover1">1</li>
                    <li id="li_two">2</li>
                    <li>3</li>
					<li>4</li>
                </ul> 
            	<ul class="m1_two clearfix" id="m1_two">
                    <li class="li_frist li_hover2" id="t1">选择认证途径</li>
                    <li id="t2">输入验证码</li>
					<li id="li_three t3">重新设置密码</li>
					<li id="t4">成功获取密码</li>
                </ul> 
            </div>			
            <div id="main2">
				<div class="m2_user">
                	<span class="name">用户名 ：</span><input type="text" class="validate[required]" name="username" id="username"/>
                </div>
              	<div class="m2_check">
                	<dl class="clearfix">
                    	<dt>
							<input type="hidden" id="radio2" name="verifyType" value="0"/>
							邮箱地址：<input type="text" name="email" id="email" class="validate[required,custom[email]]"/>
                        </dt>
                        <dd><h3>您的邮箱将收到一条验证码，该验证码的有效期限是15分钟</h3></dd>
                        <dd><h4>若超过5分钟没有收到，请重新获取</h4></dd>
                    </dl>
              	</div>
				<a class="a_button btn" href="javascript:void(0)" onclick="$('#pageForm').submit()"><span>下一步</span></a>
				<a class="a_button btn1" href="javascript:void(0)" onclick="window.location.href='../../../';return false;"><span>返回</span></a>
            </div>
		</div>
        
		<div id="footer">
		  <div id="copyright">${app_copyright_name}</div>
		</div>
	</form>
	</body>
</html>


