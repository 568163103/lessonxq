$(function(){
    var userid = $.cookie("userid")
    var cityid = $.cookie("cityid")
    var cityids = cityid.split(",")
    console.log(cityids)

    url = studentLink
    var $schoolname = $("#schoolname").val();

    ahtml = '<tr>'+
            '<th width="20">序号</th>'+
            '<th width="100">机构名称</th>'+
            '<th width="80">区县</th>'+
            '<th width="80">地区</th>'+
            '<th width="60">机构性质</th>'+
            '<th width="60">学生数量</th>'+
            '<th width="60">小班</th>'+
            '<th width="60">中班</th>'+
            '<th width="60">大班</th>'+
            '<th width="80">操作</th>'+
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
                console.log(res)
                if( res.code == 200 ){
                    $(".table-account").html(' ')
                    $(".table-account").html(ahtml)
                    if( res.list.length > 0){
                        for( var i =0; i< res.list.length;i++){
                            var qhtml = ''
                                qhtml += '<tr>'+
                                    '<td><div class="td-bg">'+((currentPage-1)*10+i+1)+'</div></td>'+
                                    '<td title='+res.list[i].schoolname+'><div class="td-bg al">'+res.list[i].schoolname+'</div>'+
                                    '<td><div class="td-bg">'+res.list[i].city_name+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].district_name+'</div></td>'+
                                    '</td><td><div class="td-bg">私立</div></td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    '<td><div class="td-bg"><a href="student-detail.html?schoolid='+res.list[i].schoolid+'">查看详情</a></div></td>'+
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