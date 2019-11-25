package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.DbVersion;

public interface DbVersionMapper {
    int deleteByPrimaryKey(String version);

    int insert(DbVersion record);

    int insertSelective(DbVersion record);

    DbVersion selectByPrimaryKey(String version);

    int updateByPrimaryKeySelective(DbVersion record);

    int updateByPrimaryKey(DbVersion record);
}