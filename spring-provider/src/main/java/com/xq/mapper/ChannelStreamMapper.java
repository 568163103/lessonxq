package com.xq.mapper;

import com.xq.domain.ChannelStream;
import org.apache.ibatis.annotations.Param;

public interface ChannelStreamMapper {
    int deleteByPrimaryKey(@Param("channelId") String channelId, @Param("num") Integer num);

    int insert(ChannelStream record);

    int insertSelective(ChannelStream record);

    ChannelStream selectByPrimaryKey(@Param("channelId") String channelId, @Param("num") Integer num);

    int updateByPrimaryKeySelective(ChannelStream record);

    int updateByPrimaryKey(ChannelStream record);
}