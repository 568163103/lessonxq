package com.xq.mapper;

import com.xq.domain.ChannelDistribute;

public interface ChannelDistributeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ChannelDistribute record);

    int insertSelective(ChannelDistribute record);

    ChannelDistribute selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChannelDistribute record);

    int updateByPrimaryKey(ChannelDistribute record);
}