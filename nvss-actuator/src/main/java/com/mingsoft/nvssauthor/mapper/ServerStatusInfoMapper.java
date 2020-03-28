package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.ServerStatusInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServerStatusInfoMapper {
    int deleteByPrimaryKey(Long serverId);

    int insert(ServerStatusInfo record);

    int insertSelective(ServerStatusInfo record);

    ServerStatusInfo selectByPrimaryKey(Long serverId);

    int updateByPrimaryKeySelective(ServerStatusInfo record);

    int updateByPrimaryKey(ServerStatusInfo record);

    List<ServerStatusInfo> initServerStatus();
}