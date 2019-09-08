package com.xq.mapper;

import com.xq.domain.DmuAlarmInfo;

public interface DmuAlarmInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DmuAlarmInfo record);

    int insertSelective(DmuAlarmInfo record);

    DmuAlarmInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmuAlarmInfo record);

    int updateByPrimaryKey(DmuAlarmInfo record);
}