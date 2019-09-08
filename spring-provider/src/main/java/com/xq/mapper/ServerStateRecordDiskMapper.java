package com.xq.mapper;

import com.xq.domain.ServerStateRecordDisk;

public interface ServerStateRecordDiskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServerStateRecordDisk record);

    int insertSelective(ServerStateRecordDisk record);

    ServerStateRecordDisk selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServerStateRecordDisk record);

    int updateByPrimaryKey(ServerStateRecordDisk record);
}