package com.xq.mapper;

import com.xq.domain.Conditions;

public interface ConditionsMapper {
    int deleteByPrimaryKey(Long conditionid);

    int insert(Conditions record);

    int insertSelective(Conditions record);

    Conditions selectByPrimaryKey(Long conditionid);

    int updateByPrimaryKeySelective(Conditions record);

    int updateByPrimaryKey(Conditions record);
}