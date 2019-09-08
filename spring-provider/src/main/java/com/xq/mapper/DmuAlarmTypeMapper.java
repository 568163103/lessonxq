package com.xq.mapper;

import com.xq.domain.DmuAlarmType;

public interface DmuAlarmTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(DmuAlarmType record);

    int insertSelective(DmuAlarmType record);

    DmuAlarmType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DmuAlarmType record);

    int updateByPrimaryKey(DmuAlarmType record);
}