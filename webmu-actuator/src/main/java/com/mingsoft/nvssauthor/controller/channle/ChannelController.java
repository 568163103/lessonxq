package com.mingsoft.nvssauthor.controller.channle;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mingsoft.nvssauthor.constant.Constant;
import com.mingsoft.nvssauthor.domain.Channel;
import com.mingsoft.nvssauthor.domain.Encoder;
import com.mingsoft.nvssauthor.domain.EncoderModel;
import com.mingsoft.nvssauthor.domain.PositionCode;
import com.mingsoft.nvssauthor.mapper.EncoderModelMapper;
import com.mingsoft.nvssauthor.mapper.PositionCodeMapper;
import com.mingsoft.nvssauthor.service.ChannelService;
import com.mingsoft.nvssauthor.entiry.ChannelStatistics;
import javafx.scene.chart.ValueAxis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mac-xq
 * @ClassName ChannelController
 * @Description
 * @Date 2019-11-29 16:45
 * @Version
 **/

@RestController
@RequestMapping(value = "api/v1/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @Autowired
    private PositionCodeMapper positionCodeMapper;

    @Autowired
    private EncoderModelMapper encoderModelMapper;

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

    /**
     * 获取本站下的摄像机总数及状态
     *
     * @param
     * @return
     */
    @PostMapping(value = "thisSiteChannelStatus")
    public Map<String, Object> thisSiteChannelStatus(@RequestParam(value = "position") String position) {
        PositionCode positionCode = positionCodeMapper.selectByPrimaryKey(position);


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
        if (channels != null && positionCode != null) {
            for (Channel channel : channels) {
                if (positionCode.getCode().equals(channel.getEncoderId().substring(0, 6))) {
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
            }
            result.put("code", 200);
        } else {
            result.put("code", 500);
        }
        ChannelStatistics channelStatistics = new ChannelStatistics(fixedCameraCount, ptzCameraCount, fixedIpCameraCount, ptzIpCameraCount);
        List<Encoder> offOnlineChannel = channelService.getChannelStatusCount(0);
        List<Encoder> onlineChannel = channelService.getChannelStatusCount(1);
//        result.put("channelStatistics", channelStatistics);
        result.put("offOnlineChannel", offOnlineChannel.size());
        result.put("onlineChannel", onlineChannel.size());
        result.put("count", onlineChannel.size() + offOnlineChannel.size());
        return result;
    }

    @PostMapping(value = "thisSiteChannels")
    public Map<String, Object> thisSiteChannels(@RequestParam(value = "position") String position,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @RequestParam(value = "channelId", required = false) String channelId,
                                                @RequestParam(value = "mode", required = false) String mode,
                                                @RequestParam(value = "status", required = false) Boolean status,
                                                @RequestParam(value = "name", required = false) String encoderName,
                                                @RequestParam(value = "ip", required = false) String ip
    ) {

        Map<String, Object> params = new HashMap<>();
        params.put("position", position);
        params.put("channelId", channelId);
        params.put("mode", mode);
        params.put("status", status);
        params.put("encoderName", encoderName);
        params.put("ip", ip);
        PageHelper.startPage(page, size);
        Map<String, Object> result = new HashMap<>();
        List<Encoder> encoders = channelService.findEncoder(params);
        if (encoders != null) {
            PageInfo<Encoder> pageInfo = new PageInfo(encoders);
            result.put("pages", pageInfo.getPages());
            result.put("encoders", pageInfo.getList());
            result.put("pageNum", pageInfo.getPageNum());
            result.put("code", 200);
        } else {
            result.put("code", 500);
        }

        return result;
    }

    @PostMapping(value = "selectionCriteria")
    public Map<String, Object> selectionCriteria() {
        Map<String, Object> result = new HashMap<>();
        List<EncoderModel> encoderModels = encoderModelMapper.findAll();
        if (encoderModels != null) {
            result.put("encoderModels", encoderModels);
            result.put("code", 200);
        } else {
            result.put("code", 500);
        }
        return result;
    }

    public static void main(String[] args) {


    }


}
