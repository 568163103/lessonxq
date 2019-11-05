package com.xq.controller;

import com.xq.config.WeChatConfig;
import com.xq.domain.Video;
import com.xq.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xq
 * @Date 2019-11-05 14:52:22
 */
@RestController
public class TestController {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private VideoMapper videoMapper;

    @RequestMapping(value = "/getAppId")
    public String getAppId() {
        return weChatConfig.getAppSecret();
    }

    @RequestMapping(value = "/testDb")
    public List<Video> testDb() {
        return videoMapper.findAll();
    }
}
