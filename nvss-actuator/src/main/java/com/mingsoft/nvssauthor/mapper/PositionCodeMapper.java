package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.PositionCode;

public interface PositionCodeMapper {
    int deleteByPrimaryKey(String code);

    int insert(PositionCode record);

    int insertSelective(PositionCode record);

    PositionCode selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(PositionCode record);

    int updateByPrimaryKey(PositionCode record);
}