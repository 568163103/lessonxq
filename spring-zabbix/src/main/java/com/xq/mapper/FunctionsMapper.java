package com.xq.mapper;

import com.xq.domain.Functions;

public interface FunctionsMapper {
    int deleteByPrimaryKey(Long functionid);

    int insert(Functions record);

    int insertSelective(Functions record);

    Functions selectByPrimaryKey(Long functionid);

    int updateByPrimaryKeySelective(Functions record);

    int updateByPrimaryKey(Functions record);
}