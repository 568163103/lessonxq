$(function(){

    var studentid = GetQueryString("studentid");

    url = studentssLink
    ahtml = '<tr>'+
            '<th width="20">序号</th>'+
            '<th width="100">父母姓名</th>'+
            '<th width="80">关系</th>'+
            '<th width="100">手机号</th>'+
        '</tr>'
    $(".table-account").html('')
    $(".table-account").html(ahtml)


    var tabelQua = function(studentid){
        $.ajax({
            url: url,
            type: "post",
            dataType: "json",
            data: {
                "studentid": studentid
            },
            success: function(res) {
                console.log(res)
                $(".table-account").html('')
                $(".table-account").html(ahtml)
                if( res.code == 200 ){
                    if( res.list.length > 0){
                        for( var i =0; i< res.list.length;i++){
                            var qhtml = ''
                                qhtml += '<tr>'+
                                    '<td><div class="td-bg">'+(i+1)+'</div></td>'+
                                    '<td><div class="td-bg">'+res.list[i].nickname+'</div>'+
                                    '</td><td><div class="td-bg">'+res.list[i].relation+'</div>'+
                                    '</td>'+
                                    '<td><div class="td-bg">'+res.list[i].username+'</div></td>'+
                                    '</tr>';
                            $(".table-account").append(qhtml)
                            $(".totalpage").val(res.page_count)
                            $(".currentpage").val(res.page_no)
                            if( res.page_count >= 1){
                                $(".pageul").show();
                            } 
                        }
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
    tabelQua(studentid)

})