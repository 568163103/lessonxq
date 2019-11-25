package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.ServerStateRecordNetcard;

public interface ServerStateRecordNetcardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServerStateRecordNetcard record);

    int insertSelective(ServerStateRecordNetcard record);

    ServerStateRecordNetcard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServerStateRecordNetcard record);

    int updateByPrimaryKey(ServerStateRecordNetcard record);
}