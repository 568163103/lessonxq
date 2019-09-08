package com.xq.mapper;

import com.xq.domain.Channel;

public interface ChannelMapper {
    int deleteByPrimaryKey(String id);

    int insert(Channel record);

    int insertSelective(Channel record);

    Channel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);
}