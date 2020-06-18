package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.DmuAlarmRes;

public interface DmuAlarmResMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DmuAlarmRes record);

    int insertSelective(DmuAlarmRes record);

    DmuAlarmRes selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmuAlarmRes record);

    int updateByPrimaryKey(DmuAlarmRes record);
}