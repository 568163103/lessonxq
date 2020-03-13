$(function(){
	var userid = $.cookie("userid")
    var personnelLinks = personnelLink

	$.ajax({
        url: personnelLinks,
        type: "post",
        dataType: "json",
        data: {
            "userid": userid,
        },
        success: function(res) {
        	console.log(res)
        	if( res.code == 200 ){
                for( var i =0; i< res.schoolList.length;i++){
                    var qhtml = ''
                        qhtml += '<td><div class="person-main">'+
                            '<div class="person-number">'+res.schoolList[i].number+'</div>'+
                            '<div class="person-name">'+res.schoolList[i].name+'</div>'+
                            '<div class="person-info">'+
                            '<p>与去年同期相比：'+res.schoolList[i].change+'</p>'+
                            '<p>上月增加：'+res.schoolList[i].lastIncrease+' 名</p>'+
                            '<p>本月增加：'+res.schoolList[i].increase+' 名</p></div>'+
                            '</div></td>';
                    $(".personnel-table tr").append(qhtml)
                    $(".person-main").eq(0).addClass("person-main-1")
                    $(".person-main").eq(1).addClass("person-main-2")
                    $(".person-main").eq(2).addClass("person-main-3")
                    $(".person-main").eq(3).addClass("person-main-4")
                }
            }else{
                layer.msg(''+res.code+'', {time: 1000});
            }
    	}
	})

})