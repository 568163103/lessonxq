package com.xq.mapper;

import com.xq.domain.Expressions;

public interface ExpressionsMapper {
    int deleteByPrimaryKey(Long expressionid);

    int insert(Expressions record);

    int insertSelective(Expressions record);

    Expressions selectByPrimaryKey(Long expressionid);

    int updateByPrimaryKeySelective(Expressions record);

    int updateByPrimaryKey(Expressions record);
}