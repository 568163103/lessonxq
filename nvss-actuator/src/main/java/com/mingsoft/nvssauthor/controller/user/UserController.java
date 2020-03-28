package com.mingsoft.nvssauthor.controller.user;

import com.mingsoft.nvssauthor.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2020-03-21 23:52
 * @Version
 **/
@RestController
@RequestMapping(value = "api/v1/user")
public class UserController {

    @Autowired
    private SystemUserService systemUserService;

     @PostMapping(value = "getSystemUserInfo")
     public Map<String,Object> getSystemUserInfo(){
        Map<String,Object> result =  systemUserService.getSystemUserInfo();
        return result;
     }




}
