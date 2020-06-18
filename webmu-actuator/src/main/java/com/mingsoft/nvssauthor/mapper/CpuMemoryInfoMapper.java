package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.CpuMemoryInfo;

public interface CpuMemoryInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CpuMemoryInfo record);

    int insertSelective(CpuMemoryInfo record);

    CpuMemoryInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CpuMemoryInfo record);

    int updateByPrimaryKey(CpuMemoryInfo record);

    CpuMemoryInfo getNewCpuMemoryInfo();
}