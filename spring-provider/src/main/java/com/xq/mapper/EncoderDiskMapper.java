package com.xq.mapper;

import com.xq.domain.EncoderDisk;

public interface EncoderDiskMapper {
    int deleteByPrimaryKey(String id);

    int insert(EncoderDisk record);

    int insertSelective(EncoderDisk record);

    EncoderDisk selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EncoderDisk record);

    int updateByPrimaryKey(EncoderDisk record);
}