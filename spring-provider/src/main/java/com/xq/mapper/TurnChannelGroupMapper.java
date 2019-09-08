package com.xq.mapper;

import com.xq.domain.TurnChannelGroup;

public interface TurnChannelGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TurnChannelGroup record);

    int insertSelective(TurnChannelGroup record);

    TurnChannelGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TurnChannelGroup record);

    int updateByPrimaryKey(TurnChannelGroup record);
}