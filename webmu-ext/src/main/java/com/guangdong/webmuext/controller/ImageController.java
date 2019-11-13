package com.guangdong.webmuext.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-13 17:01
 * @Version
 **/
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {


    @PostMapping("/upload")
    public String uploadImage(){
        

        return "";
    }

}
