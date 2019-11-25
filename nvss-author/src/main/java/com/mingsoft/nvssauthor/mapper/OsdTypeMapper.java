package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.OsdType;

public interface OsdTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(OsdType record);

    int insertSelective(OsdType record);

    OsdType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OsdType record);

    int updateByPrimaryKey(OsdType record);
}