package com.xq.mapper;

import com.xq.domain.ServiceAlarms;

public interface ServiceAlarmsMapper {
    int deleteByPrimaryKey(Long servicealarmid);

    int insert(ServiceAlarms record);

    int insertSelective(ServiceAlarms record);

    ServiceAlarms selectByPrimaryKey(Long servicealarmid);

    int updateByPrimaryKeySelective(ServiceAlarms record);

    int updateByPrimaryKey(ServiceAlarms record);
}