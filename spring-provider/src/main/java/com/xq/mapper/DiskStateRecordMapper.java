package com.xq.mapper;

import com.xq.domain.DiskStateRecord;

public interface DiskStateRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DiskStateRecord record);

    int insertSelective(DiskStateRecord record);

    DiskStateRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DiskStateRecord record);

    int updateByPrimaryKey(DiskStateRecord record);
}