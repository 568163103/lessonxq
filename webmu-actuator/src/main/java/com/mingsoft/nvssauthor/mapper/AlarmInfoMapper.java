package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.AlarmInfo;

public interface AlarmInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmInfo record);

    int insertSelective(AlarmInfo record);

    AlarmInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmInfo record);

    int updateByPrimaryKey(AlarmInfo record);
}