package com.xq.mapper;

import com.xq.domain.TriggerDepends;

public interface TriggerDependsMapper {
    int deleteByPrimaryKey(Long triggerdepid);

    int insert(TriggerDepends record);

    int insertSelective(TriggerDepends record);

    TriggerDepends selectByPrimaryKey(Long triggerdepid);

    int updateByPrimaryKeySelective(TriggerDepends record);

    int updateByPrimaryKey(TriggerDepends record);
}