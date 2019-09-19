<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/jsp/common/head/headFormPage.jsp"%>
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
            min-width: 0;
        }
        
        .bodyer {
            padding:12px;
            background:#FFFFFF;
            border:0px solid #d3d3d3;
            border-top:0;
        }
        
        .libar {
            list-style:none; 
            list-style-type:none; 
            padding:0; 
            margin:0; 
            float:left; 
            height:30px;
            background:#900; 
            line-height:30px; 
            text-align:center; 
            margin-right:1px; 
            color:#fff;
        }
    </style>

    <script type="text/javascript">
        $(function() {
        	$("#selectDay").css('display','block').buttonset();
        });
    </script>
</head>
<body class="bg">
    <c:choose>
        <c:when test="${ isError }">
            <div style="max-width: 1045px; margin: 0 auto;">
                <div class="prompt">
                    <div class="wel_words">
                        <ul class="out_line">
                            <span class="in_line">${ message }</span>
                        </ul>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="bodyer">
                <form id="pageForm" name="pageForm" method="post">
                    <div id="pageDiv" name="pageDiv" class="pageDiv">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableCss">
                            <thead>
                                <tr>
                                    <th width="8%">开始时间</th>
                                    <th width="8%">结束时间</th>
									<th width="8%">存储时长</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${ storage.itemList }">
                                    <tr>
                                        <td>${ item.beginTime}</td>
                                        <td>${ item.endTime }</td>
										<td>${ item.lengthDesc}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</body>
</html>