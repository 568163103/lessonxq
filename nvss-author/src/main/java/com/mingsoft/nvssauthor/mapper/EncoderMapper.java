package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.Encoder;

public interface EncoderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Encoder record);

    int insertSelective(Encoder record);

    Encoder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Encoder record);

    int updateByPrimaryKey(Encoder record);
}