package com.xq.mapper;

import com.xq.domain.OsdType;

public interface OsdTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(OsdType record);

    int insertSelective(OsdType record);

    OsdType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OsdType record);

    int updateByPrimaryKey(OsdType record);
}