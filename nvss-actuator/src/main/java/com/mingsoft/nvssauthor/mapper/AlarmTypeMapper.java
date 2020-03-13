package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.AlarmType;

public interface AlarmTypeMapper {
    int deleteByPrimaryKey(Integer type);

    int insert(AlarmType record);

    int insertSelective(AlarmType record);

    AlarmType selectByPrimaryKey(Integer type);

    int updateByPrimaryKeySelective(AlarmType record);

    int updateByPrimaryKey(AlarmType record);
}