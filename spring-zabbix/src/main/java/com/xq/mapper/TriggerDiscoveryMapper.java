package com.xq.mapper;

import com.xq.domain.TriggerDiscovery;

public interface TriggerDiscoveryMapper {
    int deleteByPrimaryKey(Long triggerid);

    int insert(TriggerDiscovery record);

    int insertSelective(TriggerDiscovery record);

    TriggerDiscovery selectByPrimaryKey(Long triggerid);

    int updateByPrimaryKeySelective(TriggerDiscovery record);

    int updateByPrimaryKey(TriggerDiscovery record);
}