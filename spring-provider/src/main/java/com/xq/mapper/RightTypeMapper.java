package com.xq.mapper;

import com.xq.domain.RightType;

public interface RightTypeMapper {
    int deleteByPrimaryKey(Integer type);

    int insert(RightType record);

    int insertSelective(RightType record);

    RightType selectByPrimaryKey(Integer type);

    int updateByPrimaryKeySelective(RightType record);

    int updateByPrimaryKey(RightType record);
}