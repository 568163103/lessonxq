package com.mingsoft.nvssauthor.controller.channle;

import com.mingsoft.nvssauthor.constant.Constant;
import com.mingsoft.nvssauthor.domain.Channel;
import com.mingsoft.nvssauthor.domain.Encoder;
import com.mingsoft.nvssauthor.service.ChannelService;
import com.mingsoft.nvssauthor.tempentiry.ChannelStatistics;
import org.apache.commons.lang3.StringUtils;
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

    @PostMapping(value = "/getChannelList", produces = "application/json;charset=utf-8")
    public Map<String, Object> getChannelList() {
        HashMap<String, Object> result = new HashMap<>();
        List<Channel> channels = channelService.getChannelInfoList();
        int fixedCameraCount = 0;
        int ptzCameraCount = 0;
        int fixedIpCameraCount = 0;
        int ptzIpCameraCount = 0;

//        int fixedCameraOnline = 0;
//        int ptzCameraOnline = 0;
//        int fixedIpCameraOnline = 0;
//        int ptzIpOnline = 0;
        for (Channel channel : channels) {
            String channelId = channel.getId();
            if (StringUtils.isNotBlank(channelId)) {
                String tempChannelId = channelId.substring(6, 10);
                if (Constant.FIXED_CAMERA.equals(tempChannelId)) {
                    fixedCameraCount++;
                } else if (Constant.PTZ_CAMERA.equals(tempChannelId)) {
                    ptzCameraCount++;
                } else if (Constant.FIXED_IP_CAMERA.equals(tempChannelId)) {
                    fixedIpCameraCount++;
                } else if (Constant.PTZ_IP_CAMERA.equals(tempChannelId)) {
                    ptzIpCameraCount++;
                }
            }
        }
        ChannelStatistics channelStatistics = new ChannelStatistics(fixedCameraCount, ptzCameraCount, fixedIpCameraCount, ptzIpCameraCount);
        List<Encoder> offOnlineChannel = channelService.getChannelStatusCount(0);
        List<Encoder> onlineChannel = channelService.getChannelStatusCount(1);
        result.put("channelStatistics", channelStatistics);
        result.put("offOnlineChannel", offOnlineChannel.size());
        result.put("onlineChannel", onlineChannel.size());
        return result;
    }

    public static void main(String[] args) {

    }


}
