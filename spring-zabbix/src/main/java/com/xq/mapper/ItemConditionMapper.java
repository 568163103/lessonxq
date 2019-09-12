package com.xq.mapper;

import com.xq.domain.ItemCondition;

public interface ItemConditionMapper {
    int deleteByPrimaryKey(Long itemConditionid);

    int insert(ItemCondition record);

    int insertSelective(ItemCondition record);

    ItemCondition selectByPrimaryKey(Long itemConditionid);

    int updateByPrimaryKeySelective(ItemCondition record);

    int updateByPrimaryKey(ItemCondition record);
}