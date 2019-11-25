package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.InterDevice;

public interface InterDeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InterDevice record);

    int insertSelective(InterDevice record);

    InterDevice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InterDevice record);

    int updateByPrimaryKey(InterDevice record);
}