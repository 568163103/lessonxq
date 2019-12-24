package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.AlarmDiskInfo;

import java.util.List;

public interface AlarmDiskInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmDiskInfo record);

    int insertSelective(AlarmDiskInfo record);

    AlarmDiskInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmDiskInfo record);

    int updateByPrimaryKey(AlarmDiskInfo record);

    List<AlarmDiskInfo> findByNewDiskInfo();

    int delAllAlarmDiskInfo();
}