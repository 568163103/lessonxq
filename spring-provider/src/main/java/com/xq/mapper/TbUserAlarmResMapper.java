package com.xq.mapper;

import com.xq.domain.TbUserAlarmRes;
import org.apache.ibatis.annotations.Param;

public interface TbUserAlarmResMapper {
    int deleteByPrimaryKey(@Param("userId") String userId, @Param("alarmResId") Integer alarmResId);

    int insert(TbUserAlarmRes record);

    int insertSelective(TbUserAlarmRes record);
}