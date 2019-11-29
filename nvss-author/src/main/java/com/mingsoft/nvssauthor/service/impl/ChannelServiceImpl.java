package com.mingsoft.nvssauthor.service.impl;

import com.mingsoft.nvssauthor.domain.Channel;
import com.mingsoft.nvssauthor.mapper.ChannelMapper;
import com.mingsoft.nvssauthor.service.ChannelService;
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


    @Override
    public List<Channel> getChannelInfoList() {
        return channelMapper.getChannelList()   ;
    }
}
