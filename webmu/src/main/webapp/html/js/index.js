$(function () {
    NS.init();

    //车站时间
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var hours = date.getHours()
        var minutes = date.getMinutes()
        var seconds = date.getSeconds()

        if (month < 10) month = '0' + month;
        if (strDate < 10) strDate = '0' + strDate;
        if (hours < 10) hours = '0' + hours;
        if (minutes < 10) minutes = '0' + minutes;
        if (seconds < 10) seconds = '0' + seconds;

        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + hours + seperator2 + minutes
            + seperator2 + seconds;
        return currentdate;
    }

    setInterval(function () {
        $(".siteTime").text(getNowFormatDate())
    }, 1000)

    $('.liYangqi').click(function () {
        // 标题
        var siteName = $(this).text()
        $('.siteName').text(siteName)
        $('.choose').html('' + siteName + '<b class="caret"></b>')
    })

    $('.liYangqi').click()
    $('.home').click()
});