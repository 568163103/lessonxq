package com.xq.service.impl;

import com.xq.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldServiceImpl {

    @Autowired
    private HelloWorldService helloWorldService;

    @RequestMapping("/hello")
    public String sayHello(@RequestParam("name") String name){
        return helloWorldService.sayHello(name);
    }
}
