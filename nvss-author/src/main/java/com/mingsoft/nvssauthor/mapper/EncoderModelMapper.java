package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.EncoderModel;

public interface EncoderModelMapper {
    int deleteByPrimaryKey(String model);

    int insert(EncoderModel record);

    int insertSelective(EncoderModel record);

    EncoderModel selectByPrimaryKey(String model);

    int updateByPrimaryKeySelective(EncoderModel record);

    int updateByPrimaryKey(EncoderModel record);
}