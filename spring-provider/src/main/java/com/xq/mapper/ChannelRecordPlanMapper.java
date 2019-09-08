package com.xq.mapper;

import com.xq.domain.ChannelRecordPlan;
import org.apache.ibatis.annotations.Param;

public interface ChannelRecordPlanMapper {
    int deleteByPrimaryKey(@Param("channelId") String channelId, @Param("planName") String planName);

    int insert(ChannelRecordPlan record);

    int insertSelective(ChannelRecordPlan record);
}