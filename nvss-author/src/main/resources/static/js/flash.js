//Powered By smvv @hi.baidu.com/smvv21  
    function flashChecker() {  
        var hasFlash = 0;         //是否安装了flash  
        var flashVersion = 0; //flash版本  
        var isIE = /*@cc_on!@*/0;      //是否IE浏览器  
  
        if (isIE) {  
            var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');  
            if (swf) {  
                hasFlash = 1;  
                VSwf = swf.GetVariable("$version");  
                flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);  
            }  
        } else {  
            if (navigator.plugins && navigator.plugins.length > 0) {  
                var swf = navigator.plugins["Shockwave Flash"];  
                if (swf) {  
                    hasFlash = 1;  
                    var words = swf.description.split(" ");  
                    for (var i = 0; i < words.length; ++i) {  
                        if (isNaN(parseInt(words[i]))) continue;  
                        flashVersion = parseInt(words[i]);  
                    }  
                }  
            }  
        }  
        return { f: hasFlash, v: flashVersion };  
    }  
  
    var fls = flashChecker();  
    var s = "";
    if (fls.f) {
        // alert("您安装了flash,当前flash版本为: " + fls.v + ".x");
    }else{
        // var no = confirm("您未安装flash，点击确定开始安装。");
        // if( no == true ){
            // var aa = window.open()
            // aa.location.href = "https://get2.adobe.com/cn/flashplayer/"
            // window.open("https://get2.adobe.com/cn/flashplayer/");
            $('#playBox').css('display','none')
            $(".noFlash").append('<p>您未安装Flash插件，<a href="https://get2.adobe.com/cn/flashplayer/" target="_blank">点击此处</a>安装。</p>')
        // }else{
        //     window.close();
        // }
    }
//     function isFlash() {
//     if (fls.f) {
//         return true
//     }else{
//         return false
//     }
// }