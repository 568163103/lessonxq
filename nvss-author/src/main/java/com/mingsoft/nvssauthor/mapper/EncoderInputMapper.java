package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.EncoderInput;

public interface EncoderInputMapper {
    int deleteByPrimaryKey(String id);

    int insert(EncoderInput record);

    int insertSelective(EncoderInput record);

    EncoderInput selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EncoderInput record);

    int updateByPrimaryKey(EncoderInput record);
}