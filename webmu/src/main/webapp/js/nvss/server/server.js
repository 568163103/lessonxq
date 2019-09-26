$(function () {
    getServerType();
});

/**
 * 获取服务器类型
 */
function getServerType() {
    $.post(nvss_provider_url+"/server/findAllServerType",{},function (data) {
        let selectHtml = '';
        let serverTypeList = data.serverTypeList;
        selectHtml += "<td width='20%'>";
        selectHtml += "<select class='validate[required]' name='server.type' id='type'>";
        selectHtml += " <option value=''>请选择</option>";
        for (let i = 0; i < serverTypeList.length; i++) {
            selectHtml += "<option value=" + serverTypeList[i].name + ">" + serverTypeList[i].name + "</option>";
        }
        selectHtml += "</select>";
        selectHtml += "</td>";
        $('#server-type').after(selectHtml);
    }).error(function () {
        alert("获取服务器类型失败");
    });
}
