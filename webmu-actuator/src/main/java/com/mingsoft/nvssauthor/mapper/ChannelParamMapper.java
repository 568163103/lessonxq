package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.ChannelParam;

public interface ChannelParamMapper {
    int deleteByPrimaryKey(String channelId);

    int insert(ChannelParam record);

    int insertSelective(ChannelParam record);

    ChannelParam selectByPrimaryKey(String channelId);

    int updateByPrimaryKeySelective(ChannelParam record);

    int updateByPrimaryKey(ChannelParam record);
}