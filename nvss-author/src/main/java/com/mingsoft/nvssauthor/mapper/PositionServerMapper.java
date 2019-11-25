package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.PositionServer;
import org.apache.ibatis.annotations.Param;

public interface PositionServerMapper {
    int deleteByPrimaryKey(@Param("positionId") String positionId, @Param("serverId") String serverId);

    int insert(PositionServer record);

    int insertSelective(PositionServer record);
}