package com.xq.mapper;

import com.xq.domain.AlarmDevice;

public interface AlarmDeviceMapper {
    int deleteByPrimaryKey(String id);

    int insert(AlarmDevice record);

    int insertSelective(AlarmDevice record);

    AlarmDevice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AlarmDevice record);

    int updateByPrimaryKey(AlarmDevice record);
}