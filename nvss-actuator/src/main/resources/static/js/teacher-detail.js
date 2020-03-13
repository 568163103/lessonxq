$(function(){

    var schoolid = GetQueryString("schoolid");

    url = teachersLink
    ahtml = '<tr>'+
            '<th width="20">序号</th>'+
            '<th width="100">用户</th>'+
            '<th width="80">角色</th>'+
            '<th width="100">视频到期日期</th>'+
            '<th width="100">视频状态</th>'+
            // '<th width="60">操作</th>'+
        '</tr>'
    $(".table-account").html('')
    $(".table-account").html(ahtml)

    var tabelQua = function(schoolid,currentpage,schoolname){
        $.ajax({
            url: teachersLink,
            type: "post",
            dataType: "json",
            async: false,
            data: {
                "schoolid": schoolid,
                "currentpage": currentpage,
                "selQuery": schoolname
            },
            success: function(res) {
                console.log(res)
                if( res.code == 200 ){
                    $(".table-account").html('')
                    $(".table-account").html(ahtml)
                    if( res.list.length > 0){
                        for( var i =0; i< res.list.length;i++){
                            var qhtml = ''
                                qhtml += '<tr>'+
                                    '<td><div class="td-bg">'+((currentpage-1)*10+i+1)+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].nickname+'</div>'+
                                    '</td><td><div class="td-bg">'+res.list[i].sex+'</div>'+
                                    '</td>'+
                                    '<td><div class="td-bg">'+res.list[i].endtime+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].video_flag+'</div></td>'+
                                    // '<td><div class="td-bg">查看详情</div></td>'+
                                    '</tr>';
                            $(".table-account").append(qhtml)
                            $(".totalpage").val(res.page_count)
                            $(".currentpage").val(res.page_no)
                            $(".total-num").text('共'+res.count+'条数据')
                            $(".total-page").text('共'+res.page_count+'页')
                            if( res.page_count >= 1){
                                $(".pageul").show();
                            } 
                        }
                        pagination(currentpage)
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
    tabelQua(schoolid,1,$schoolname)

    $(".search-res").click(function(){
        var $schoolname = $("#schoolname").val().replace(/\s*/g,'');
        tabelQua(schoolid,1,$schoolname)
    })


    $(".firstpage").click(function(){
        var $schoolname = $("#schoolname").val();
        $(".page-now").text('当前第1页')
        tabelQua(schoolid,1,$schoolname)
    })

    $(".prepage").click(function(){
        var $prepage = parseInt($(".currentpage").val()) - 1;
        if( $prepage == 0 ){
            layer.msg('当前已是第一页！', {time: 1000});
            return false
        }else{
            var $schoolname = $("#schoolname").val();
            $(".page-now").text('当前第'+$prepage+'页')
            tabelQua(schoolid,$prepage,$schoolname)
        }
    
    })

    $(".nextpage").click(function(){
        var $prepage = parseInt($(".currentpage").val()) + 1;
        
        if( $prepage-1 == $(".totalpage").val() ){
            layer.msg('当前已是最后一页！', {time: 1000});
            return false
        }else{
            var $schoolname = $("#schoolname").val();
            $(".page-now").text('当前第'+$prepage+'页')
            tabelQua(schoolid,$prepage,$schoolname)
        }
        
    })

    $(".lastpage").click(function(){
        var $schoolname = $("#schoolname").val();
        var $total = $(".totalpage").val();
        $(".page-now").text('当前第'+$total+'页')
        tabelQua(schoolid,$total,$schoolname)
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
            $(".table-account").html(ahtml)
            inputVal();
            var $schoolname = $("#schoolname").val();
            tabelQua(schoolid,$thispage,$schoolname)
        }
    })

})