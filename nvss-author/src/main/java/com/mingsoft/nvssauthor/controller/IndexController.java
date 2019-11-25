package com.mingsoft.nvssauthor.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xq
 * @Date 2019-11-25 14:03:22
 */
@RequestMapping("index")
@Controller
public class IndexController {


    @RequestMapping("/to_index")
    public String toIndex(){
        return "index";
    }
}
