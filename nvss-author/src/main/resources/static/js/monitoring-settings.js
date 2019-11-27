$(function(){

    var userid = $.cookie("userid")
    var cityid = $.cookie("cityid")
    console.log(userid,cityid)
    
    url = updateCameraLink

    var $time = $.cookie("time");
    $(".switching").val($time);

    $(".form-button").click(function(){
        var timeSwitch = $(".switching").val();
        $.ajax({
            url: url,
            type: "post",
            dataType: "json",
            data: {
                "userid": userid,
                "time": timeSwitch
            },
            success: function(res) {
                layer.msg('修改成功', {time: 1000});
                $.cookie("timeSwitch",timeSwitch)
                $(".switching").val(timeSwitch);
            }
        })
    })
    $(document).keypress(function(event){  
        var keynum = (event.keyCode ? event.keyCode : event.which);  
        if(keynum == '13'){  
            $(".form-button").click();   
        }  
    })


})