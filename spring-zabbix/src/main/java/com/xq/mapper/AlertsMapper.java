package com.xq.mapper;

import com.xq.domain.Alerts;

public interface AlertsMapper {
    int deleteByPrimaryKey(Long alertid);

    int insert(Alerts record);

    int insertSelective(Alerts record);

    Alerts selectByPrimaryKey(Long alertid);

    int updateByPrimaryKeySelective(Alerts record);

    int updateByPrimaryKey(Alerts record);
}