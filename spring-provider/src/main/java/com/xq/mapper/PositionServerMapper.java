package com.xq.mapper;

import com.xq.domain.PositionServer;
import org.apache.ibatis.annotations.Param;

public interface PositionServerMapper {
    int deleteByPrimaryKey(@Param("positionId") String positionId, @Param("serverId") String serverId);

    int insert(PositionServer record);

    int insertSelective(PositionServer record);
}