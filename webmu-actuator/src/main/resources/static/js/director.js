$(function(){
    var userid = $.cookie("userid")
    var cityid = $.cookie("cityid")
    var cityids = cityid.split(",")
    console.log(cityids)

    url = directorLink
    var $schoolname = $("#schoolname").val();

    ahtml = '<tr>'+
            '<th width="20">序号</th>'+
            '<th width="60">姓名</th>'+
            '<th width="40">性别</th>'+
            '<th width="80">手机号</th>'+
            '<th width="120">所属机构</th>'+
            '<th width="80">区县</th>'+
            '<th width="80">地区</th>'+
            '<th width="60">园长资格证</th>'+
            '<th width="50">年龄</th>'+
            // '<th width="80">操作</th>'+
        '</tr>'
    $(".table-account").html('')
    $(".table-account").html(ahtml)


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
                    $(".table-account").html(' ')
                    $(".table-account").html(ahtml)
                    console.log(res)
                    if( res.list.length > 0){
                        for( var i =0; i< res.list.length;i++){
                            var qhtml = ''
                                qhtml += '<tr>'+
                                    '<td><div class="td-bg">'+((currentPage-1)*10+i+1)+'</div></td>'+
                                    '<td><div class="td-bg al">'+
                                    ''+res.list[i].nickname+'</div>'+
                                    '</td><td><div class="td-bg">'+res.list[i].sex+'</div>'+
                                    '</td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    '<td title='+res.list[i].schoolname+'><div class="td-bg">'+res.list[i].schoolname+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].city_name+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].district_name+'</div></td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    '<td><div class="td-bg"></div></td>'+
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
    $("#pro").val(cityids[0])
    selPro(cityids[0],cityids[1],cityids[2]);  
    ajax(url,userid,cityid,1,$schoolname)

})