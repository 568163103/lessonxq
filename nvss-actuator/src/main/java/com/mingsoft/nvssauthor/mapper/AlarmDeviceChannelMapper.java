package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.AlarmDeviceChannel;
import org.apache.ibatis.annotations.Param;

public interface AlarmDeviceChannelMapper {
    int deleteByPrimaryKey(@Param("num") Integer num, @Param("deviceId") String deviceId);

    int insert(AlarmDeviceChannel record);

    int insertSelective(AlarmDeviceChannel record);

    AlarmDeviceChannel selectByPrimaryKey(@Param("num") Integer num, @Param("deviceId") String deviceId);

    int updateByPrimaryKeySelective(AlarmDeviceChannel record);

    int updateByPrimaryKey(AlarmDeviceChannel record);
}