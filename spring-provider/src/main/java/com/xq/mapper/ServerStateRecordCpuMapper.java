package com.xq.mapper;

import com.xq.domain.ServerStateRecordCpu;

public interface ServerStateRecordCpuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServerStateRecordCpu record);

    int insertSelective(ServerStateRecordCpu record);

    ServerStateRecordCpu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServerStateRecordCpu record);

    int updateByPrimaryKey(ServerStateRecordCpu record);
}