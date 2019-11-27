$(function(){
    var userid = $.cookie("userid")
    var cityid = $.cookie("cityid")
    console.log(userid,cityid)
    var cityids = cityid.split(",")
    console.log(cityids)
    
    url = directorTrainLink
    delurl = delTrainLink
    
    var $schoolname = $("#schoolname").val();
    $("#pro").val(cityids[0])
    selPro(cityids[0],cityids[1],cityids[2]);  

    ahtml = '<tr>'+
            '<th width="20">序号</th>'+
            '<th width="80">用户</th>'+
            '<th width="100">缩略图</th>'+
            '<th width="60">查看人数</th>'+
            '<th width="80">发布时间</th>'+
            '<th width="80">操作</th>'+
        '</tr>'
    $(".table-account").html('')
    $(".table-account").html(ahtml)

    //ajax
    ajax = function(url,userId){
        $.ajax({
            url: url,
            type: "post",
            dataType: "json",
            async: false,
            data: {
                "userid": userId,
                "mgtype": 'LSPX'
            },
            success: function(res) {
                console.log(res)
                if( res.code == 200 ){
                    $(".table-account").html('')
                    $(".table-account").html(ahtml)
                    if( res.messagelist.length > 0){
                        for( var i =0; i< res.messagelist.length;i++){
                            var qhtml = ''
                                qhtml += '<tr mgid='+res.messagelist[i].mgid+'>'+
                                    '<td><div class="td-bg">'+(i+1)+'</div></td>'+
                                    '<td><div class="td-bg">'+res.messagelist[i].uname+'</div></td>'+
                                    '<td><img width="80px" height="80px" src='+res.messagelist[i].imgurllist[0].imgurl+'></td>'+
                                    '<td><div class="td-bg">'+res.messagelist[i].praisecount+'</div></td>'+
                                    '<td><div class="td-bg">'+res.messagelist[i].mgtime+'</div></td>'+
                                    '<td><div class="td-bg"><span class="btn-del">删除</span></div></td>'+
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
                    }else{
                        $(".table-account").html('')
                        $(".pageul").hide();
                        layer.msg('暂时没有数据！', {time: 1000});
                    }
                }else{
                    layer.msg(''+res.code+'', {time: 1000});
                }
            }
        })
    }

    ajax(url,userid)

    //删除数据
    $(".table-list").on("click",".btn-del",function(){
        var mgId = $(this).parents("tr").attr("mgid");
        console.log(mgId)
        $.ajax({
            url: delurl,
            type: "post",
            dataType: "json",
            data: {
                "userid": userid,
                "mgid": mgId
            },
            success: function(res) {
                console.log(res)
                if( res.code == 200 ){
                    layer.msg('删除成功',{
                        time: 1000
                    },function(){
                        window.location.reload();
                    }); 
                }else{
                    layer.msg(''+res.Msgs+'', {time: 1000});
                }
            }
        })
    })

})