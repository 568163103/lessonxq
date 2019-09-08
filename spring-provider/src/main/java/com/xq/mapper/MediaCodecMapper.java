package com.xq.mapper;

import com.xq.domain.MediaCodec;

public interface MediaCodecMapper {
    int deleteByPrimaryKey(Integer tag);

    int insert(MediaCodec record);

    int insertSelective(MediaCodec record);

    MediaCodec selectByPrimaryKey(Integer tag);

    int updateByPrimaryKeySelective(MediaCodec record);

    int updateByPrimaryKey(MediaCodec record);
}