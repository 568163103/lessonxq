package com.xq.mapper;

import com.xq.domain.EncoderOutput;

public interface EncoderOutputMapper {
    int deleteByPrimaryKey(String id);

    int insert(EncoderOutput record);

    int insertSelective(EncoderOutput record);

    EncoderOutput selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EncoderOutput record);

    int updateByPrimaryKey(EncoderOutput record);
}