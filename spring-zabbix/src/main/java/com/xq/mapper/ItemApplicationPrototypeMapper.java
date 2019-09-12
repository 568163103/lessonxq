package com.xq.mapper;

import com.xq.domain.ItemApplicationPrototype;

public interface ItemApplicationPrototypeMapper {
    int deleteByPrimaryKey(Long itemApplicationPrototypeid);

    int insert(ItemApplicationPrototype record);

    int insertSelective(ItemApplicationPrototype record);

    ItemApplicationPrototype selectByPrimaryKey(Long itemApplicationPrototypeid);

    int updateByPrimaryKeySelective(ItemApplicationPrototype record);

    int updateByPrimaryKey(ItemApplicationPrototype record);
}