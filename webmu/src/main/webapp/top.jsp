<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/jsp/common/head/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/common/style.css"/>
    <style>
        body {
            background: none repeat scroll 0 0 #379be9;
        }

        .alignright {
            float: right;
        }
    </style>
    <script type="text/javascript">
        <c:if test="${SPRING_SECURITY_CONTEXT == null}">
        setInterval(function () {
            window.location.href = "${ctx}/top.do";
        }, 10000);
        </c:if>

        function logout() {
            //存在表单
            var forms = $(window.parent.frames["rightFrame"].document).find("form");
            if (forms.length == 0) {
                top.location.href = '${ctx}/j_logout.auth';
                return true;
            }
            forms.each(function (i, e) {
                var actionValue = $(e).attr("action");
                if (actionValue && (actionValue.indexOf("save") >= 0 || actionValue.indexOf("update") >= 0)) {
                    if (top.confirm("确认不处理数据就退出吗？")) {
                        top.location.href = '${ctx}/j_logout.auth';
                        return false;
                    }
                } else {
                    top.location.href = '${ctx}/j_logout.auth';
                    return false;
                }
            });
        }
    </script>

    <script type="text/javascript">
        // 倒计时
        var _ordertimer = null;
        var leftTime = ${message};

        function leftTimer() {
            if (leftTime < 1) {
                top.location.reload();
            }
            var days = parseInt(leftTime / 60 / 60 / 24, 10); //计算剩余的天数
            var hours = parseInt(leftTime / 60 / 60 % 24, 10); //计算剩余的小时
            var minutes = parseInt(leftTime / 60 % 60, 10);//计算剩余的分钟
            var seconds = parseInt(leftTime % 60, 10);//计算剩余的秒数
            days = checkTime(days);
            hours = checkTime(hours);
            minutes = checkTime(minutes);
            seconds = checkTime(seconds);
            leftTime--;
            document.getElementById("timer").innerHTML = "您的密码将于" + days + "天" + hours + "小时" + minutes + "分" + seconds + "秒后失效";
        }

        function checkTime(i) { //将0-9的数字前面加上0，例1变为01
            if (i < 10) {
                i = "0" + i;
            }
            return i;
        }


        $(function () {
            if (leftTime > 0) {
                setInterval("leftTimer()", 1000);
            } else {
                window.location.href = '${ctx}/jsp/security/password/changePasswd.do?flag=2';
            }
        })
    </script>
    <title>${global_app_name}</title>
</head>
<body style="background:url(${ctx}/images/${app_identity_code}/maintop_bg.jpg) no-repeat ;width: 100%;height: 80%;" ;>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="30" valign="top" class="cpx12hei">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="30px" width="400px">

                    </td>
                    <td height="30px" width="180px">
                        <div id="timer" style="float:left; margin-top: 40px;color:red"></div>
                    </td>

                    <td height="30px" width="80px" align="center" style="color: #ffffff;">
                        <div style="float:left; margin-top: 40px;">${version}</div>
                    </td>
                    <td height="30px" width="80px" align="center" style="color: #ffffff;">
                        <div style="float:left; margin-top: 40px;"><b
                                title="${fn:substringAfter(pageContext.session.id, '.')}">${SPRING_SECURITY_CONTEXT.authentication.principal.name}</b>
                            欢迎你!
                        </div>
                    </td>
                    <td height="30px" width="60px" align="center">
                        <a href="javascript:void(0)" target="main" style="color:#000; text-decoration:none;"
                           onclick="top.location.href='${ctx}/jsp/security/password/modifyPasswd.do'">
                            <img src="${ctx}/images/home.png" width="29" height="29" border="0" align="middle"
                                 alt="修改密码" title="修改密码" style="margin-top: 35px;">
                        </a>
                    </td>
                    <td height="30px" width="60px" align="center">
                        <a href="javascript:void(0);" target="_top" id="choseLogin" onclick="logout();">
                            <img src="${ctx}/images/exit.png" width="29" height="29" border="0" align="middle" alt="退出"
                                 title="退出" style="margin-top: 35px;">
                        </a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<!--<div id="header1" >
		<div id="wisdom_head">
			<img style="width:104px; height:48px" id="header-logo" src="${ctx}/css/common/images/logo${app_identity_code}.png" alt=""/> 
			<h1 id="site-heading">
				<a href="/" title="查看站点" target="_blank">
					<span id="site-title">${global_app_name}</span>
				</a>
			</h1>	  	
			<c:if test="${SPRING_SECURITY_CONTEXT != null}">
			<ul class="alignright" id="head_dock">
				<li>欢迎您 : <b title="${fn:substringAfter(pageContext.session.id, '.')}">${SPRING_SECURITY_CONTEXT.authentication.principal.name}</b></li>
				<li>
					<a href="javascript:void(0)" onclick="top.location.href='${ctx}/jsp/security/password/modifyPasswd.do'">
						<span class="icon icon-pen"></span>修改密码
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="logout();">
						<span class="icon icon-power"></span>退出
					</a>
				</li>
			</ul>
			</c:if>
		</div>
	</div> -->

</body>
</html>