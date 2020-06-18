$(function(){
    //获取url上对应的值
    var LocString = window.location.href;
    GetQueryString = function(str) {
        var reg = new RegExp("(^|&)"+ str +"=([^&]*)(&|$)");
        var URL =  decodeURI(window.location.search);
        var r = URL.substr(1).match(reg);
        if(r!=null){
            //decodeURI() 函数可对 encodeURI() 函数编码过的 URI 进行解码
            return  decodeURI(r[2]);
        };
        return null;
    }

    //省份
    var province = $.cookie("province");
    $(".province").text(province)

    var goUrl = $.cookie("urlCurrent")


    var realname = $.cookie("realname")
    $(".ctrl-user span").text(realname)

    if( realname == undefined ){
        window.location.href = goUrl
    }

    $(".back-btn").click(function(){
        window.history.go(-1);
    })

    $(".ctrl-out").click(function(){
        $.cookie('userid', '', { expires: -1 });
        $.cookie('cityid', '', { expires: -1 });
        $.cookie('realname', '', { expires: -1 });
        $.cookie("cityCodeCommon", '', { expires: -1 })
        window.location.href = goUrl
    })


    accountLink = url+"/ims/findUserlistPage.do";
    quaLink = url+"/ims/findSchoollistPage.do";
    realTimeLink = url+"/ims/findCameralistPage.do"; 
    personnelLink = url+"/ims/getBaseInfo.do"; 
    directorLink = url+"/ims/findMasterlistPage.do";
    teacherLink = url+"/ims/findTeacherGrouplistPage.do";
    teachersLink = url+"/ims/findTeacherBySchoolidlistPage.do";
    studentLink = url+"/ims/findStudentGrouplistPage.do";
    studentsLink = url+"/ims/findStudentBySchoolidlistPage.do";
    studentssLink = url+"/ims/findUserListByStudentid.do";
    directorTrainLink = url+"/ims/findTrainlistPage.do";
    addTrainLink = url+"/ims/upload.do";
    delTrainLink = url+"/ims/deleteMsg.do";
    updateCameraLink = url+"/ims/updateCameraConfig.do";



    //区域值
    inputVal = function(){
        var $pro = $("#pro").val();
        var $city = $("#city").val();
        var $district = $("#district").val();
        if( $pro != 0 && $city != 0 && $district != 0 ){
            $cityCode = $pro +',' + $city + ',' + $district
        }else if( $pro != 0 && $city != 0 && $district == 0 ){
            $cityCode = $pro +',' + $city
        }else if( $pro != 0 && $city == 0 && $district == 0 ){
            $cityCode = $pro
        }
        return $cityCode
        console.log($cityCode)
    }

    //分页
    pagination = function(pCurrentPage){
        $(".pageul-page").html('')
        var $totalPages = $(".totalpage").val();
        console.log($totalPages)
        if( $totalPages >= 7 && (parseInt(pCurrentPage)+5) <= $totalPages){
            var qhtml = ''
                qhtml += '<li attr='+parseInt(pCurrentPage)+'>'+parseInt(pCurrentPage)+'</li>'+
                '<li attr='+(parseInt(pCurrentPage)+1)+'>'+(parseInt(pCurrentPage)+1)+'</li>'+
                '<li attr='+(parseInt(pCurrentPage)+2)+'>'+(parseInt(pCurrentPage)+2)+'</li>'+
                '<li attr='+(parseInt(pCurrentPage)+3)+'>'+(parseInt(pCurrentPage)+3)+'</li>'+
                '<li attr='+(parseInt(pCurrentPage)+4)+'>'+(parseInt(pCurrentPage)+4)+'</li>'+
                '<li attr='+(parseInt(pCurrentPage)+5)+'>'+(parseInt(pCurrentPage)+5)+'</li>'
            $(".pageul-page").append(qhtml)
            $(".pageul-page li").each(function(){
                if( $(this).attr('attr')== parseInt(pCurrentPage) ){
                    $(this).addClass("on").siblings("li").removeClass("on")                                
                }
            });
        }else if( $totalPages >= 7 && (parseInt(pCurrentPage)+5) > $totalPages){
            var qhtml = ''
                qhtml += '<li attr='+($totalPages-5)+'>'+($totalPages-5)+'</li>'+
                '<li attr='+($totalPages-4)+'>'+($totalPages-4)+'</li>'+
                '<li attr='+($totalPages-3)+'>'+($totalPages-3)+'</li>'+
                '<li attr='+($totalPages-2)+'>'+($totalPages-2)+'</li>'+
                '<li attr='+($totalPages-1)+'>'+($totalPages-1)+'</li>'+
                '<li attr='+($totalPages)+'>'+($totalPages)+'</li>'
            $(".pageul-page").append(qhtml)
            $(".pageul-page li").each(function(){
                if( $(this).attr('attr')== parseInt(pCurrentPage) ){
                    $(this).addClass("on").siblings("li").removeClass("on")                                
                }
            });
        }else{
            for( var i = 0; i< $totalPages; i++ ){
                var qhtml = ''
                    qhtml += '<li attr='+(i+1)+'>'+(i+1)+'</li>'
                $(".pageul-page").append(qhtml)
                $(".pageul-page li").eq(pCurrentPage-1).addClass("on")
            }
        }
    }


})