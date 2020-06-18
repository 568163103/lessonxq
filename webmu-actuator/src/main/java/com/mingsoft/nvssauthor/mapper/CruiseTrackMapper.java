package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.CruiseTrack;
import org.apache.ibatis.annotations.Param;

public interface CruiseTrackMapper {
    int deleteByPrimaryKey(@Param("channelId") String channelId, @Param("num") Integer num);

    int insert(CruiseTrack record);

    int insertSelective(CruiseTrack record);

    CruiseTrack selectByPrimaryKey(@Param("channelId") String channelId, @Param("num") Integer num);

    int updateByPrimaryKeySelective(CruiseTrack record);

    int updateByPrimaryKey(CruiseTrack record);
}