package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.PreScheme;

public interface PreSchemeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PreScheme record);

    int insertSelective(PreScheme record);

    PreScheme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PreScheme record);

    int updateByPrimaryKey(PreScheme record);
}