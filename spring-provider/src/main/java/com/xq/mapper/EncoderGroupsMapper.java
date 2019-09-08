package com.xq.mapper;

import com.xq.domain.EncoderGroups;

public interface EncoderGroupsMapper {
    int deleteByPrimaryKey(String encoderId);

    int insert(EncoderGroups record);

    int insertSelective(EncoderGroups record);

    EncoderGroups selectByPrimaryKey(String encoderId);

    int updateByPrimaryKeySelective(EncoderGroups record);

    int updateByPrimaryKey(EncoderGroups record);
}