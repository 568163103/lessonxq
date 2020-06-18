package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.ServerStauts;

public interface ServerStautsMapper {
    int deleteByPrimaryKey(String serverId);

    int insert(ServerStauts record);

    int insertSelective(ServerStauts record);

    ServerStauts selectByPrimaryKey(String serverId);

    int updateByPrimaryKeySelective(ServerStauts record);

    int updateByPrimaryKey(ServerStauts record);
}