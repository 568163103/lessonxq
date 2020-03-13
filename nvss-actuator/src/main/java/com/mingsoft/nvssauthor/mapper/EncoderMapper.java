package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.Encoder;

import java.util.List;

public interface EncoderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Encoder record);

    int insertSelective(Encoder record);

    Encoder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Encoder record);

    int updateByPrimaryKey(Encoder record);
    List<Encoder> findStatusCount(int status);
}