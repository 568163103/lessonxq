package com.mingsoft.nvssauthor.controller.channle;

import com.mingsoft.nvssauthor.domain.Channel;
import com.mingsoft.nvssauthor.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-29 16:45
 * @Version
 **/

@RestController
@RequestMapping(value = "api/v1/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @PostMapping(value = "/getChannelList",produces = "application/json;charset=utf-8")
    public Map<String, Object> getChannelList() {
        HashMap<String, Object> result = new HashMap<>();
        List<Channel> channels = channelService.getChannelInfoList();
        if (channels != null && channels.size() > 0) {
            result.put("code", 200);
            result.put("data", channels);
        } else {
            result.put("code", 500);
            result.put("mag", "获取摄像机失败");
        }
        return result;
    }
}
