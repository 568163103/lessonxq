package com.xq.mapper;

import com.xq.domain.Items;

public interface ItemsMapper {
    int deleteByPrimaryKey(Long itemid);

    int insert(Items record);

    int insertSelective(Items record);

    Items selectByPrimaryKey(Long itemid);

    int updateByPrimaryKeySelective(Items record);

    int updateByPrimaryKey(Items record);
}