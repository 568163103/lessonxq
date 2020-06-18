$(function(){
    var userid = $.cookie("userid")
    var cityid = $.cookie("cityid")
    var cityids = cityid.split(",")
    console.log(cityids)

    url = realTimeLink
    var $schoolname = $("#schoolname").val();
    $("#pro").val(cityids[0])
    selPro(cityids[0],cityids[1],cityids[2]);  

    ahtml = ''
    $(".monitoring-con").html(' ');

 
    
    ajax = function(url,userId,cityCode,currentPage,selQuery){
        $.ajax({
            url: url,
            type: "post",
            dataType: "json",
            async: false,
            data: {
                "userid": userId,
                "city": cityCode,
                "currentpage": currentPage,
                "selQuery": selQuery
            },
            success: function(res) {
                console.log(res)
                $(".monitoring-con").html(' ');
                if( res.code == 200 ){
                    if( res.list.length > 0){
                        for( var i =0; i< res.list.length;i++){
                            var rhtml = ''
                                rhtml += '<div index='+((res.page_no-1)*10+i)+' class="monitoring-list" '+
                            'deviceip='+res.list[i].deviceip+' deviceport='+res.list[i].deviceport+' '+
                            'deviceid='+res.list[i].deviceid+' capblity='+res.list[i].capblity+' '+
                            'devicename='+res.list[i].devicename+' rtmp='+res.list[i].rtmp+'>'+
                            '<div class="monitoring-pic">'+
                            '<img src="../images/video.png">'+
                            '</div>'+
                            '<div class="monitoring-title" title='+res.list[i].schoolname+'>'+res.list[i].schoolname+'-'+res.list[i].devicename+'</div>'+
                            '</div>';
                            $(".monitoring-con").append(rhtml)
                            $(".totalpage").val(res.page_count)
                            $(".currentpage").val(res.page_no)
                            $(".totalnum").val(res.count)
                            $(".total-num").text('共'+res.count+'条数据')
                            $(".total-page").text('共'+res.page_count+'页')
                            if( res.page_count >= 1){
                                $(".pageul").show();
                            } 
                        }
                        pagination(currentPage)
                    }else{
                        $(".pageul").hide();
                        layer.msg('暂时没有数据！', {time: 1000});
                    }
                }else{
                    layer.msg(''+res.code+'', {time: 1000});
                }
            }
        });
    }

    var $schoolname = $("#schoolname").val();
    $("#pro").val(cityid.substring(0,2)+'0000')
    selPro(cityid)
    ajax(url,userid,cityid,1,$schoolname)
    
    
    var timeSwitch = $.cookie("timeSwitch")
    if( !timeSwitch ){
        var time = parseInt($.cookie("time") + '000');
    }else{
        var time = parseInt($.cookie("timeSwitch") + '000');
    }
    console.log(time)

    var num,lock;
    $(".monitoring-con").on("click",".monitoring-list",function(){
        num = $(this).attr("index")
        var nowPage = parseInt(num/10)+1;
        var deviceip = $(this).attr("deviceip")
        var deviceport = $(this).attr("deviceport")
        var deviceid = $(this).attr("deviceid")
        var devicename = $(this).attr("devicename")
        var capblity = $(this).attr("capblity")
        var rtmp = $(this).attr("rtmp")
        console.log(devicename)
        $.cookie("deviceip",deviceip)
        $.cookie("deviceport",deviceport)
        $.cookie("deviceid",deviceid)
        $.cookie("devicename",devicename)
        $.cookie("capblity",capblity)
        $.cookie("rtmp",rtmp)
        layer.open({
            type: 2,
            title: "",
            area: ['900px', '620px'],
            shadeClose: true, //点击遮罩关闭
            resize: false,
            content: ['real-video.html', 'no'],
            end: function() {  
                layer.closeAll();      
                clearInterval(lock);

            }  
        });
        lock = setInterval(timer, time); 
    })
        

    function timer(){
        num ++;
        var totalPage = parseInt($(".total-page").val());
        var nextPage = parseInt($(".currentpage").val())+1;
        var totalNum = parseInt($(".totalnum").val());
        var nowPage = parseInt(num/10) +1;
        console.log(num,nowPage,totalNum)
        $(".page-now").text("当前第"+nowPage+"页")
        if( num >= totalNum ){
            clearInterval(lock);  
        }else{
            ajax(url,userid,cityid,nowPage,$schoolname)
            $(".monitoring-list").each(function(){
                var index = $(this).attr("index")
                if( num == index ){
                    var deviceip = $(this).attr("deviceip")
                    var deviceport = $(this).attr("deviceport")
                    var deviceid = $(this).attr("deviceid")
                    var devicename = $(this).attr("devicename")
                    var capblity = $(this).attr("capblity")
                    var rtmp = $(this).attr("rtmp")
                    $.cookie("deviceip",deviceip)
                    $.cookie("deviceport",deviceport)
                    $.cookie("deviceid",deviceid)
                    $.cookie("devicename",devicename)
                    $.cookie("capblity",capblity)
                    $.cookie("rtmp",rtmp)
                    console.log(devicename)
                    layer.open({
                        type: 2,
                        title: "",
                        area: ['900px', '620px'],
                        shadeClose: true, //点击遮罩关闭
                        resize: false,
                        content: ['real-video.html', 'no'],
                        end: function () {  
                            layer.closeAll();  
                            clearInterval(lock);
                        } 
                    }); 
                }
            })
        }        
    };


})