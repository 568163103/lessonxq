package com.xq.mapper;

import com.xq.domain.MssChannel;

public interface MssChannelMapper {
    int deleteByPrimaryKey(String channelId);

    int insert(MssChannel record);

    int insertSelective(MssChannel record);

    MssChannel selectByPrimaryKey(String channelId);

    int updateByPrimaryKeySelective(MssChannel record);

    int updateByPrimaryKey(MssChannel record);
}