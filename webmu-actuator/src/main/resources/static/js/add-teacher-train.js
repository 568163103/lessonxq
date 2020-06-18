$(function(){
    var userid = $.cookie("userid")

    url = addTrainLink


    var userAgent = navigator.userAgent; //用于判断浏览器类型
    //上传图片到服务器之前，在页面显示图片
    
    var formData = new FormData();
    //将图片数据放到formData中传递后台
    $("#fileimg").change(function(){
        //获取选择图片的对象
        var docObj = $(this)[0];
        var picDiv = $(this).parents(".upload");
        //得到所有的图片文件
        var fileList = docObj.files;
        //console.log(fileList)

        for(var i=0; i < fileList.length;i++){
            formData.append("file"+i,fileList[i]);
            console.log(formData.get('file'+i))
        }

        //循环遍历
        for (var i = 0; i < fileList.length; i++) {
            //动态添加html元素
            var picHtml = "<div class='upload-pic'><img id='img" + fileList[i].name + "' /> <div class='cover'><i class='delbtn'>删除</i></div></div>";
            
            $(".upload-add").before(picHtml);
            $(".upload-add").hide();
            //获取图片imgi的对象
            var imgObjPreview = document.getElementById("img" + fileList[i].name);

            if (fileList && fileList[i]) {
                //图片属性
                imgObjPreview.style.display = 'block';
                imgObjPreview.style.width = '80px';
                imgObjPreview.style.height = '80px';
                //imgObjPreview.src = docObj.files[0].getAsDataURL();
                //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要以下方式
                if (userAgent.indexOf('MSIE') == -1) {
                    //IE以外浏览器
                    imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]); //获取上传图片文件的物理路径;
                    //console.log(imgObjPreview.src);
                    // var msgHtml = '<input type="file" id="fileInput" multiple/>';
                } else {
                    //IE浏览器
                    if (docObj.value.indexOf(",") != -1) {
                        var srcArr = docObj.value.split(",");
                        imgObjPreview.src = srcArr[i];
                    } else {
                        imgObjPreview.src = docObj.value;
                    }
                }
            }
        }
        /*删除功能*/
        // $(".delbtn").click(function() {
        //     var _this = $(this);
        //     _this.parents(".upload-pic").remove();
        // });

    });

    //提交到服务器
    $(".add-train-btn").click(function(){
        var fileimg = $("#fileimg").val();
        var mgcontent = $("#mgContent").val();
        console.log(fileimg,mgcontent)
        if( fileimg == null || fileimg == ''){
            layer.msg('请上传缩略图', {time: 1000}); 
            return false
        }else if( mgcontent == null || mgcontent == ''){
            layer.msg('内容不能为空', {time: 1000}); 
            return false
        }else{
            $.ajax({
                url: url,
                type: "post",
                headers: {
                    "userid": userid,
                    "mgcontent": mgcontent,
                    "mgtype": "LSPX"
                },
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: formData,
                success: function(res) {
                    console.log(res)
                    if( res.code == 200 ){
                        layer.msg('添加成功',{
                            time: 1000
                        },function(){
                            window.history.go(-1);
                        });
                    }else{
                        layer.msg(''+res.code+'', {time: 1000});
                    }
                }
            })
        }
        
    })



})