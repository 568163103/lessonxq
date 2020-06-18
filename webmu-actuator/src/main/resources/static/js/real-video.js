$(function(){

    var deviceip = GetQueryString("deviceip")
    var deviceport = GetQueryString("deviceport")
    var deviceid = GetQueryString("deviceid")
    var devicename = GetQueryString("devicename")
    var capblity = GetQueryString("capblity")
    var rtmp = GetQueryString("rtmp")

    var deviceip = $.cookie("deviceip")
    var deviceport = $.cookie("deviceport")
    var deviceid = $.cookie("deviceid")
    var devicename = $.cookie("devicename")
    var capblity = $.cookie("capblity")
    var rtmp = $.cookie("rtmp")

    console.log(devicename)

    $(".video-title").text(devicename)
})