$(function(){
    
    //省份
    var $province = '桂林';
    $(".province").text($province)

    var urlCurrent = window.location.href;
    $.cookie("urlCurrent",urlCurrent)


    function setCookie(){ //设置cookie  
        var loginUser = $("#user").val(); //获取用户名信息  
        var loginPwd = $("#pwd").val(); //获取登陆密码信息  
        var checked = $("input[name='remeber']").is(':checked');//获取“是否记住密码”复选框
        var auto = $("input[name='auto']").is(':checked');

        if( checked ){ //判断是否选中了“记住密码”复选框  
            $.cookie("login_user",loginUser, { expires: 7 });//调用jquery.cookie.js中的方法设置cookie中的用户名 
            $.cookie("login_pwd",$.base64.encode(loginPwd), { expires: 7 });
            //调用jquery.cookie.js中的方法设置cookie中的登陆密码 
        }else{   
            $.cookie("login_pwd", null);   
        }  
        if( auto ){
            $.cookie("login_user",loginUser, { expires: 7 });
            $.cookie("login_pwd",$.base64.encode(loginPwd), { expires: 7 });
            $.cookie("autoinp", 1 , { expires: 7 });
        }else if( !checked && !auto ){   
            $.cookie("login_user", '');
            $.cookie("login_pwd", '');   
            $.cookie("autoinp", '');   
        } 
    }   

    $(".login-btn").click(function(){
        var $user = $("#user").val(),
            $pwd = $("#pwd").val();
        if( $user.length === 0){
            layer.msg('用户名不能为空', {time: 500}); 
        }else if( $pwd.length === 0){
            layer.msg('密码不能为空', {time: 500}); 
        }else{
            $.ajax({
                url: url + "/ims/login.do",
                type: "post",
                dataType: "json",
                data: {
                    "username": $user,
                    "password": $pwd
                },
                success: function(res) {
                    console.log(res)
                    if( res.code == 200){
                        $.cookie("userid",res.userid)
                        $.cookie("cityid",res.cityid)
                        $.cookie("realname",res.realname)
                        $.cookie("time",res.cameraConfig.time)
                        $.cookie("province",$province)
                        if($("[name='checkbox']").attr("checked","true")){ 
                            setCookie();   
                            window.location.href = 'home.html'
                        }else{  
                            window.location.href = 'home.html' 
                        } 

                    }else{
                        layer.msg('用户名或密码错误', {time: 1000});
                    }
                }
            })
        }
    })

    function getCookie(){ //获取cookie  
        var loginUser = $.cookie("login_user"); //获取cookie中的用户名  
        var loginPwd =  $.cookie("login_pwd"); //获取cookie中的登陆密码  
        var autoInp =  $.cookie("autoinp");

        if(loginPwd){//密码存在的话把“记住用户名和密码”复选框勾选住  
            $("input[name='remeber']").attr("checked","true");  
        }  
        if(loginUser){//用户名存在的话把用户名填充到用户名文本框  
            $("#user").val(loginUser);  
        }  
        if(loginPwd){//密码存在的话把密码填充到密码文本框  
            $("#pwd").val($.base64.decode(loginPwd)); 
        }  
        if( autoInp == 1){  
            $("#user").val(loginUser); 
            $("#pwd").val($.base64.decode(loginPwd)); 
            $("input[name='auto']").attr("checked","true"); 
            $(".login-btn").click();
        }  
    } 

    getCookie();
    
    

    $(document).keypress(function(event){  
        var keynum = (event.keyCode ? event.keyCode : event.which);  
        if(keynum == '13'){  
            $(".login-btn").click();   
        }  
    })
})