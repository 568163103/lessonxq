package com.xq.mapper;

import com.xq.domain.ImsGisInfo;

public interface ImsGisInfoMapper {
    int deleteByPrimaryKey(String channelId);

    int insert(ImsGisInfo record);

    int insertSelective(ImsGisInfo record);

    ImsGisInfo selectByPrimaryKey(String channelId);

    int updateByPrimaryKeySelective(ImsGisInfo record);

    int updateByPrimaryKey(ImsGisInfo record);
}