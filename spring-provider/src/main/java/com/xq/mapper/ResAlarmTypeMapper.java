package com.xq.mapper;

import com.xq.domain.ResAlarmType;

public interface ResAlarmTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(ResAlarmType record);

    int insertSelective(ResAlarmType record);

    ResAlarmType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ResAlarmType record);

    int updateByPrimaryKey(ResAlarmType record);
}