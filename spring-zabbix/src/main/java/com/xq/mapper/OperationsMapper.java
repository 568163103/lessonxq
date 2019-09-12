package com.xq.mapper;

import com.xq.domain.Operations;

public interface OperationsMapper {
    int deleteByPrimaryKey(Long operationid);

    int insert(Operations record);

    int insertSelective(Operations record);

    Operations selectByPrimaryKey(Long operationid);

    int updateByPrimaryKeySelective(Operations record);

    int updateByPrimaryKey(Operations record);
}