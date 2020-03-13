$(function(){
    var userid = $.cookie("userid")
    var cityid = $.cookie("cityid")
    console.log(userid,cityid)
    var cityids = cityid.split(",")
    console.log(cityids)
    
    url = accountLink
    var $schoolname = $("#schoolname").val();
    $("#pro").val(cityids[0])
    selPro(cityids[0],cityids[1],cityids[2]);   
    

    ahtml = '<tr>'+
            '<th width="20">序号</th>'+
            '<th width="80">姓名</th>'+
            '<th width="80">手机号</th>'+
            '<th width="60">性别</th>'+
            '<th width="60">登录次数</th>'+
            '<th width="60">园所数</th>'+
            // '<th width="80">操作</th>'+
        '</tr>'
    $(".table-account").html('')
    $(".table-account").html(ahtml)

    //ajax
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
                "selQuery": selQuery,
            },
            success: function(res) {
                if( res.code == 200 ){
                    $(".table-account").html('')
                    $(".table-account").html(ahtml)
                    if( res.list.length > 0){
                        for( var i =0; i< res.list.length;i++){
                            var qhtml = ''
                                qhtml += '<tr>'+
                                    '<td><div class="td-bg">'+((currentPage-1)*10+i+1)+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].realname+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].phone+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].sex+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].logintimes+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].schoolnum+'</div></td>'+
                                    // '<td><div class="td-bg"><span class="btn-edit">编辑</span>'+ 
                                    // '<span class="btn-del">删除</span></div></td>'+
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
                        pagination(currentPage)
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

    ajax(url,userid,cityid,1,$schoolname)

})