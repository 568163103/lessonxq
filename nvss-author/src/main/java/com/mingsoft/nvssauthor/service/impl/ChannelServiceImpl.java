package com.mingsoft.nvssauthor.service.impl;

import com.mingsoft.nvssauthor.cache.channel.ChannelCacheService;
import com.mingsoft.nvssauthor.constant.Constant;
import com.mingsoft.nvssauthor.domain.Channel;
import com.mingsoft.nvssauthor.domain.Encoder;
import com.mingsoft.nvssauthor.domain.TDict;
import com.mingsoft.nvssauthor.mapper.ChannelMapper;
import com.mingsoft.nvssauthor.mapper.EncoderMapper;
import com.mingsoft.nvssauthor.mapper.TDictMapper;
import com.mingsoft.nvssauthor.service.ChannelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-29 16:50
 * @Version
 **/
@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private ChannelCacheService channelCacheService;
    @Autowired
    private TDictMapper tDictMapper;

    @Autowired
    private EncoderMapper encoderMapper;

    @Override
    public List<Channel> getChannelInfoList() {
        return channelMapper.getChannelList();
    }

    @Override
    public void setChannelType() {
        List<TDict> cacheDictList = channelCacheService.getChannelTypeList("tDictList", TDict.class);
        if (cacheDictList == null) {
            cacheDictList = tDictMapper.findChannelTypeDict(Integer.valueOf(Constant.CAMERA_TYPE) );
            channelCacheService.setChannelTypeList("tDictList",cacheDictList,100000);
        }
        if (cacheDictList != null && cacheDictList.size() > 0) {
            for (TDict tDict : cacheDictList) {
                String dictName = channelCacheService.getChannelType(Constant.CACHE_TYPE+":"+tDict.getValue());
                if (StringUtils.isBlank(dictName)) {
                    channelCacheService.setChannelType(Constant.CACHE_TYPE+":"+tDict.getValue(), tDict.getName());
                }
            }
        }
    }

    @Override
    public String getChannelTypeName(String key) {
        return channelCacheService.getChannelType(key);
    }

    @Override
    public List<TDict> getChannelTypeList(String key,Class<TDict> tDictClass) {
        return channelCacheService.getChannelTypeList(key,tDictClass);
    }

    @Override
    public List<Encoder> getChannelStatusCount(int status) {
        return encoderMapper.findStatusCount(status);
    }


}
