var buffer = document.getElementById('buffer');
var wrong = document.getElementById('error');
var videoContainer = document.getElementsByClassName('video-container')[0];
var playerBox = document.getElementById('playBox');


var LocString = window.location.href;
var GetString = function (str) {
    var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(LocString), tmp;
    if (tmp = rs) return tmp[2];
    return "no";
}


// var deviceip = GetString("deviceip")
// var deviceport = GetString("deviceport")
// var deviceid = GetString("deviceid")
// var devicename = GetString("devicename")
// var capblity = GetString("capblity")
// var rtmp = GetString("rtmp")
var deviceip = $.cookie("deviceip")
var deviceport = $.cookie("deviceport")
var deviceid = $.cookie("deviceid")
var devicename = $.cookie("devicename")
var capblity = $.cookie("capblity")
var rtmp = $.cookie("rtmp")

var streamtype = capblity;
var capblityL = capblity.split(",");
if( capblityL.length == 1 ){
    streamtype = capblity
}else{
    streamtype = 1
}
var deviceids = deviceid.replace('-','_');
//var url = 'http://' + deviceip + '/' +'v1/sms/routes?proto=flv&title='+deviceids+'_'+ streamtype;
var url = '/v1/sms/routes?proto=flv&title='+deviceids+'_'+ streamtype;
console.log(url)

$.ajax({ 
    url: url, 
    dataType: "json",
    type: "get",
    async: false,
    success: function(resurl){
        console.log(resurl)
        if(resurl.code=="200"){
            $.cookie("addrs",resurl.data.addrs[0]);
            $.cookie("port",resurl.data.port);
            $.cookie("app",resurl.data.app);   
            $.cookie("title",resurl.data.title);
            $.cookie("proto",resurl.data.proto);
        }                           
    }
})
// var capblity = $.cookie("capblity");
var addrs = $.cookie("addrs");
var port = $.cookie("port");
var app = $.cookie("app");
var title = $.cookie("title");
var proto = $.cookie("proto");

var theUrl = 'http://' + addrs + ':' + port + '/' + app + '/' + title + '.' + proto;
console.log(theUrl)
function changePic(sub) {
    //console.log('进入了切换函数'+sub)
    var liveNuP = theUrl.split('/')[4].split('.')[0].split('_')[0];
    var liveNuPv = theUrl.split('/')[4].split('.')[0].split('_')[1];
    var suffix = theUrl.split('.');
    //var turl = 'http://' + deviceip + '/' +'v1/sms/routes?proto='+suffix[suffix.length-1]+'&title=' + liveNuP + '_' + liveNuPv + '_' + sub;
    var turl = '/v1/sms/routes?proto='+suffix[suffix.length-1]+'&title=' + liveNuP + '_' + liveNuPv + '_' + sub;

    $.ajax({
        url: turl,
        dataType: "json",
        type: "get",
        async: false,
        success: function (tresurl) {
            var tuaddrs = tresurl.data.addrs;
            var tuport = tresurl.data.port;
            var tuapp = tresurl.data.app;
            var tutitle = tresurl.data.title;
            var goUrl = 'http://' + tuaddrs + ':' + tuport + '/' + tuapp + '/' + tutitle + '.'+suffix[suffix.length-1];
            $.cookie("goUrl", goUrl);
        }
    });
    var goUrl = $.cookie("goUrl");
    //console.log('这是切换后的播放地址'+goUrl)
    startTo(goUrl);
    getNowUrl(goUrl);
    clearTimeout(timeEnd)
    toEnd()
}

//--------------------------------播放器适应尺寸
playFont();
window.onresize = function () {
    playFont()
};

function playFont() {
    var screenWidth = document.body.clientWidth;
    if (screenWidth < 900) {
        videoContainer.style.width = screenWidth + 'px';
        playerBox.style.width = screenWidth + 'px';
        buffer.style.width = screenWidth / 10 + 'px';
        buffer.style.height = screenWidth / 10 + 'px';
        wrong.style.width = screenWidth / 4 + 'px';
        wrong.style.height = screenWidth / 5 + 'px'
    }
    if (screenWidth > 900) {
        videoContainer.style.width = 900 + 'px';
        playerBox.style.width = 900 + 'px';
        buffer.style.width = 70 + 'px';
        buffer.style.height = 70 + 'px';
        wrong.style.width = 200 + 'px';
        wrong.style.height = 154 + 'px'
    }
}

