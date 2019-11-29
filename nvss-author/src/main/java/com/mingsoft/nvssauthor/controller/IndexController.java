package com.mingsoft.nvssauthor.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xq
 * @Date 2019-11-25 14:03:22
 */
@RequestMapping("index")
@Controller
public class IndexController {


    @RequestMapping("/to_index")
    public String toIndex(
            @RequestParam(value = "username",required = false) String username
    ) {
        System.out.println(username);
        return "index";
    }
}
