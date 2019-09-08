package com.xq.mapper;

import com.xq.domain.PreScheme;

public interface PreSchemeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PreScheme record);

    int insertSelective(PreScheme record);

    PreScheme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PreScheme record);

    int updateByPrimaryKey(PreScheme record);
}