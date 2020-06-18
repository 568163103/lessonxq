$(function(){
    var userid = $.cookie("userid")
    var cityid = $.cookie("cityid")
    console.log(userid,cityid)
    var cityids = cityid.split(",")
    console.log(cityids)
    
    url = quaLink
    var $schoolname = $("#schoolname").val();
    $("#pro").val(cityids[0])
    selPro(cityids[0],cityids[1],cityids[2]);  

    ahtml = '<tr>'+
            '<th width="20">序号</th>'+
            '<th width="100">幼儿园名称</th>'+
            '<th width="100">所属区域</th>'+
            '<th width="60">机构性质</th>'+
            '<th width="50">占地面积</th>'+
            '<th width="50">绿化面积</th>'+
            '<th width="50">运动面积</th>'+
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
                console.log(res)
                if( res.code == 200 ){
                    $(".table-account").html('')
                    $(".table-account").html(ahtml)
                    if( res.list.length > 0){
                        for( var i =0; i< res.list.length;i++){
                            var qhtml = ''
                                qhtml += '<tr>'+
                                    '<td><div class="td-bg">'+((currentPage-1)*10+i+1)+'</div></td>'+
                                    '<td><div class="td-bg al" title='+res.list[i].schoolname+'>'+
                                    ''+res.list[i].schoolname+'</div>'+
                                    '</td><td><div class="td-bg">'+res.list[i].province_name+res.list[i].city_name+res.list[i].district_name+'</div>'+
                                    '</td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    '<td><div class="td-bg"></div></td>'+
                                    // '<td><div class="td-bg">详细查看</div></td>'+
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


    $("#pro").val(cityids[0])
    selPro(cityids[0],cityids[1],cityids[2]); 
    var $schoolname = $("#schoolname").val();
    ajax(url,userid,cityid,1,$schoolname)


})