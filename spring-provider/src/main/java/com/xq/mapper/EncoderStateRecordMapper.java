package com.xq.mapper;

import com.xq.domain.EncoderStateRecord;

public interface EncoderStateRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EncoderStateRecord record);

    int insertSelective(EncoderStateRecord record);

    EncoderStateRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EncoderStateRecord record);

    int updateByPrimaryKey(EncoderStateRecord record);
}