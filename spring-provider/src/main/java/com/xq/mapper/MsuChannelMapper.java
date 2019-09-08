package com.xq.mapper;

import com.xq.domain.MsuChannel;
import org.apache.ibatis.annotations.Param;

public interface MsuChannelMapper {
    int deleteByPrimaryKey(@Param("channelId") String channelId, @Param("serverId") String serverId);

    int insert(MsuChannel record);

    int insertSelective(MsuChannel record);
}