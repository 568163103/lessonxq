package com.xq.mapper;

import com.xq.domain.EncoderInput;

public interface EncoderInputMapper {
    int deleteByPrimaryKey(String id);

    int insert(EncoderInput record);

    int insertSelective(EncoderInput record);

    EncoderInput selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EncoderInput record);

    int updateByPrimaryKey(EncoderInput record);
}