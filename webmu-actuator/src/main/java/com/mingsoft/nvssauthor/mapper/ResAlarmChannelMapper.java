package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.ResAlarmChannel;
import org.apache.ibatis.annotations.Param;

public interface ResAlarmChannelMapper {
    int deleteByPrimaryKey(@Param("alarmChannelId") String alarmChannelId, @Param("channelId") String channelId);

    int insert(ResAlarmChannel record);

    int insertSelective(ResAlarmChannel record);
}