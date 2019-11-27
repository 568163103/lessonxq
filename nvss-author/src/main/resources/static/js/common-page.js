$(function(){
    var userid = $.cookie("userid")
    var cityid = $.cookie("cityid")
    var urls = url
    var ahtmls = ahtml


    //模糊搜索
    $(".search-res").click(function(){
        var $schoolname = $("#schoolname").val().replace(/\s*/g,'');
        inputVal();
        ajax(url,userid,$cityCode,1,$schoolname)
        $.cookie("cityCodeCommon",$cityCode)
    })


    //第一页
    $(".firstpage").click(function(){
        $(".pageul-page li").each(function(){
            if( $(this).attr('attr')==1 ){
                $(this).addClass("on").siblings("li").removeClass("on")
                $(this).parents(".pageul").siblings(".pageul").children("li").removeClass("on")
            }
        });
        $(".table-account").html(ahtmls)
        inputVal();
        var $schoolname = $("#schoolname").val();
        $(".page-now").text('当前第1页')
        ajax(url,userid,$cityCode,1,$schoolname)
    })


    //上一页
    $(".prepage").click(function(){
        var $prepage = parseInt($(".currentpage").val()) - 1;
        if( $prepage == 0 ){
            layer.msg('当前已是第一页！', {time: 1000});
            return false
        }else{
            $(".table-account").html(ahtmls)
            inputVal();
            var $schoolname = $("#schoolname").val();
            $(".page-now").text('当前第'+$prepage+'页')
            ajax(url,userid,$cityCode,$prepage,$schoolname)
            $(".pageul-page li").each(function(){
                if( $(this).attr('attr')==$prepage ){
                    $(this).addClass("on").siblings("li").removeClass("on")
                    $(this).parents(".pageul").siblings(".pageul").children("li").removeClass("on")
                }
            });
        }
    })


    //下一页
    $(".nextpage").click(function(){
        var $nextpage = parseInt($(".currentpage").val()) + 1;
        if( $nextpage-1 == $(".totalpage").val() ){
            layer.msg('当前已是最后一页！', {time: 1000});
            return false
        }else{
            $(".table-account").html(ahtmls)
            inputVal();
            var $schoolname = $("#schoolname").val();
            $(".page-now").text('当前第'+$nextpage+'页')
            ajax(url,userid,$cityCode,$nextpage,$schoolname)
            $(".pageul-page li").each(function(){
                if( $(this).attr('attr')==$nextpage ){
                    $(this).addClass("on").siblings("li").removeClass("on")
                    $(this).parents(".pageul").siblings(".pageul").children("li").removeClass("on")
                }
            });
        }
    })


    //最后一页
    $(".lastpage").click(function(){
        $(".pageul-page li").each(function(){
            if( $(this).attr('attr')==$(".totalpage").val() ){
                $(this).addClass("on").siblings("li").removeClass("on")
                $(this).parents(".pageul").siblings(".pageul").children("li").removeClass("on")
            }
        });
        var $total = $(".totalpage").val();   
        $(".table-account").html(ahtmls)
        inputVal();
        var $schoolname = $("#schoolname").val();
        $(".page-now").text('当前第'+$total+'页')
        ajax(url,userid,$cityCode,$total,$schoolname)
    })


    //点击某一页
    $(".pageul-page").on("click","li",function(){
        if( $(this).hasClass("disabled") ){
            return false
        }else{
            var $thispage = $(this).attr('attr');
            $(".page-now").text('当前第'+$thispage+'页')
            $(this).addClass("on").siblings("li").removeClass("on")
            $(this).parents(".pageul").siblings(".pageul").children("li").removeClass("on")
            $(".table-account").html(ahtmls)
            inputVal();
            var $schoolname = $("#schoolname").val();
            ajax(url,userid,$cityCode,$thispage,$schoolname)
        }
    })


})