package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.EncoderExtra;

public interface EncoderExtraMapper {
    int deleteByPrimaryKey(String id);

    int insert(EncoderExtra record);

    int insertSelective(EncoderExtra record);

    EncoderExtra selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EncoderExtra record);

    int updateByPrimaryKey(EncoderExtra record);
}