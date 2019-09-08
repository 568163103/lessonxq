package com.xq.mapper;

import com.xq.domain.ServerStateRecord;

public interface ServerStateRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServerStateRecord record);

    int insertSelective(ServerStateRecord record);

    ServerStateRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServerStateRecord record);

    int updateByPrimaryKey(ServerStateRecord record);
}