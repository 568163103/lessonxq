package com.xq.mapper;

import com.xq.domain.Preset;
import org.apache.ibatis.annotations.Param;

public interface PresetMapper {
    int deleteByPrimaryKey(@Param("channelId") String channelId, @Param("num") Integer num);

    int insert(Preset record);

    int insertSelective(Preset record);

    Preset selectByPrimaryKey(@Param("channelId") String channelId, @Param("num") Integer num);

    int updateByPrimaryKeySelective(Preset record);

    int updateByPrimaryKey(Preset record);
}