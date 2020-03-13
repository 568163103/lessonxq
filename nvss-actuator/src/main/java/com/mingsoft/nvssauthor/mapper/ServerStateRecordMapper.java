package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.ServerStateRecord;

public interface ServerStateRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServerStateRecord record);

    int insertSelective(ServerStateRecord record);

    ServerStateRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServerStateRecord record);

    int updateByPrimaryKey(ServerStateRecord record);
}