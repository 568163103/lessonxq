<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/common/head/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common/style.css"/>
<style>
.prompt{
    background: #FFFFFF;
    position:absolute;
    width: 752px;
    height: 373px;
    top:50%;
    left:50%;
    margin-top:-187px;
    margin-left:-376px;
}
.bg {
    width: 99.3%;
    height: 98%;
    padding: 10px 0 0 0;
    background: #FFFFFF;
}
</style>

<script type="text/javascript">
</script>
<title>${global_app_name}</title>
</head>
<body class="bg">
<div style="max-width: 1045px; margin: 0 auto;">
    <div class="prompt">
        <div class="wel_words">
            <ul class="out_line">
                <span class="in_line">没有查到硬盘使用信息</span>
            </ul>
        </div>
    </div>
</div>
</body>
</html>