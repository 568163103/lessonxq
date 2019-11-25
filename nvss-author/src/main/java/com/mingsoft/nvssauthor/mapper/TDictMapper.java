package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.TDict;

public interface TDictMapper {
    int deleteByPrimaryKey(Integer did);

    int insert(TDict record);

    int insertSelective(TDict record);

    TDict selectByPrimaryKey(Integer did);

    int updateByPrimaryKeySelective(TDict record);

    int updateByPrimaryKey(TDict record);
}