package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.DmuAlarmType;

public interface DmuAlarmTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(DmuAlarmType record);

    int insertSelective(DmuAlarmType record);

    DmuAlarmType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DmuAlarmType record);

    int updateByPrimaryKey(DmuAlarmType record);
}