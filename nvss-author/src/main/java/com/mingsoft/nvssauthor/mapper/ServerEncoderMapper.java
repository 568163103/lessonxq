package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.ServerEncoder;
import org.apache.ibatis.annotations.Param;

public interface ServerEncoderMapper {
    int deleteByPrimaryKey(@Param("serverId") String serverId, @Param("encoderId") String encoderId);

    int insert(ServerEncoder record);

    int insertSelective(ServerEncoder record);
}