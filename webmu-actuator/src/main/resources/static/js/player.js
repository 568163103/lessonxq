//---------------------------------播放器设置
var timeout = '1';
var step = 0;
var buffer = document.getElementById('buffer');
var wrong = document.getElementById('error');
var switcher = document.getElementById('switcher');
var cleanEle = document.getElementById('clean');
var smoothEle = document.getElementById('smooth');
var hint = document.getElementById('hint');
var player = ksplayer('example-video', {
    language: 'zh-CN',
    preload: 'auto',
    fluid: true,
    bigPlayButton: false,
    autoplay: true,
    controlBar: {
        volumeMenuButton: {
            inline: false,
            vertical: true
        }
    }
});
var bigBtn = player.addChild("BigPlayButton");
startTo(theUrl);
getPic(capblity);
getNowUrl(theUrl);
again();
toEnd();
//console.log('默认播放地址' + theUrl);

//------------------------------事件触发

player.on("ready", function (event, data) {
    //console.log('播放器准备好了');
    clearTimeout(timeEnd);
    toEnd();
    wrong.style.display = 'none'
});

player.on("error", function (event) {
    buffer.style.display = 'block';
    // if (switcherUrl.indexOf('http') == 0 && switcherUrl.endWith('.m3u8')) {
    //     timeout = '0';
    // }
    //console.log('出现了错误')
});

// player.on("pause", function (event, data) {
//     //console.log('视频暂停了')
// });

player.on("loadstart", function (event, data) {
    //console.log('播放器开始加载数据了');
    buffer.style.display = 'block'
});
player.on("seeking", function (event, data) {
    //console.log('开始寻规');
    buffer.style.display = 'block';
});
player.on("seeked", function (event) {
    //console.log('结束寻规');
    buffer.style.display = 'none';
});
player.on("waiting", function (event) {
    //console.log('出现了卡顿');
    if (switcherUrl.indexOf('http') == 0 && switcherUrl.indexOf('.flv') != -1) {
        timeout = '0'
    }
});
player.on("loadeddata", function (event) {
    //console.log('获取到视频的第一帧');
    wrong.style.display = 'none';
    buffer.style.display = 'none';
    timeout = '1';
    step = 0
});
player.on("play", function (event) {
    //console.log('开始播放');
    if (switcherUrl.indexOf('http') == 0 && switcherUrl.indexOf('.flv') != -1) {
        timeout = '0'
    }
});
player.on("useractive", function (event) {
    switcher.style.opacity = 1;
    switcher.style.transition = 'opacity 0.25s'
});
player.on("userinactive", function (event) {
    switcher.style.opacity = 0;
    switcher.style.transition = 'opacity 1s'
});


//--------------------------------清晰度切换
//切换时的点击事件
function clean() {
    buffer.style.display = 'block';
    changePic(0)
}
function smooth() {
    buffer.style.display = 'block';
    changePic(1)
}

function getPic(sub) {
    //console.log('这是表示清晰度的代号的长度'+sub.length)
    if (sub.length == 1) {
        switcher.style.display = 'none'
    } else if (sub.length == 3) {
        getNowUrl(theUrl)
    }
}

function getNowUrl(nowUrl) {
    switcherUrl = nowUrl;
    //console.log('当前最新的URL'+switcherUrl)
    var liveNu = nowUrl.split('/')[4].split('.')[0].split('_')[2];
    if (liveNu == 0) {
        cleanEle.style.display = 'none';
        smoothEle.style.display = 'block'
    }
    if (liveNu == 1) {
        cleanEle.style.display = 'block';
        smoothEle.style.display = 'none'
    }
}

//------------------------------播放接口
function startTo(toUrl) {
    // if (toUrl.indexOf('http') == 0 && toUrl.indexOf('.flv') != -1) {
        //console.log('进入了默认播放的接口--flv：'+toUrl)
        player.reset();
        player.src({"type": "video/x-flv", "src": toUrl});
        player.play();
        player.load();
    // } 
    // else if (toUrl.indexOf('http') == 0 && toUrl.endWith('.m3u8')) {
    //     hint.innerText = '提示：手机端播放视频时可能要缓冲二十秒左右';
    //     if (isIE()) {
    //         alert("IE浏览器以及主流浏览器兼容模式不能播放这个视频");
    //     }
    //     player.reset();
    //     player.src({"type": "application/x-mpegURL", "src": toUrl});
    //     player.play();
    //     player.load();
    // }
}
// function isIE() {
//     //console.log('判断了IE浏览器')
//     var userAgent = navigator.userAgent;
//     var isOpera = userAgent.indexOf("Opera") > -1;
//     if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
//         return true;
//     }
// }

//--------------------------------倒计一小时结束播放
function toEnd() {
    //console.log('开始一小时停播倒计时');
    timeEnd = setTimeout(function () {
        player.pause();
    }, 3600000)
}

//--------------------------------断网重连
function again() {
    if (timeout == '0') {
        if (step < 6) {
            startTo(switcherUrl);
            getNowUrl(switcherUrl);
            //console.log('重连地址：' + switcherUrl);
        }
        step++;
        if (step > 6) {
            buffer.style.display = 'none';
            wrong.style.display = 'block';
            player.pause()
        }
    }
    //console.log('step' + step);
    setTimeout(again, 5000);
}
