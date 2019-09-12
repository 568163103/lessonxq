package com.xq.mapper;

import com.xq.domain.Opconditions;

public interface OpconditionsMapper {
    int deleteByPrimaryKey(Long opconditionid);

    int insert(Opconditions record);

    int insertSelective(Opconditions record);

    Opconditions selectByPrimaryKey(Long opconditionid);

    int updateByPrimaryKeySelective(Opconditions record);

    int updateByPrimaryKey(Opconditions record);
}