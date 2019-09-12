package com.xq.mapper;

import com.xq.domain.GraphsItems;

public interface GraphsItemsMapper {
    int deleteByPrimaryKey(Long gitemid);

    int insert(GraphsItems record);

    int insertSelective(GraphsItems record);

    GraphsItems selectByPrimaryKey(Long gitemid);

    int updateByPrimaryKeySelective(GraphsItems record);

    int updateByPrimaryKey(GraphsItems record);
}