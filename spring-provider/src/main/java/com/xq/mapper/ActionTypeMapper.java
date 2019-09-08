package com.xq.mapper;

import com.xq.domain.ActionType;

public interface ActionTypeMapper {
    int deleteByPrimaryKey(String type);

    int insert(ActionType record);

    int insertSelective(ActionType record);

    ActionType selectByPrimaryKey(String type);

    int updateByPrimaryKeySelective(ActionType record);

    int updateByPrimaryKey(ActionType record);
}