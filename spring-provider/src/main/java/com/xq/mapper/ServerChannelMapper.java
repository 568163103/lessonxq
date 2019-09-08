package com.xq.mapper;

import com.xq.domain.ServerChannel;
import org.apache.ibatis.annotations.Param;

public interface ServerChannelMapper {
    int deleteByPrimaryKey(@Param("channelId") String channelId, @Param("serverId") String serverId);

    int insert(ServerChannel record);

    int insertSelective(ServerChannel record);
}