package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.AlarmCpuInfo;
import org.springframework.stereotype.Component;

@Component
public interface AlarmCpuInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmCpuInfo record);

    int insertSelective(AlarmCpuInfo record);

    AlarmCpuInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmCpuInfo record);

    int updateByPrimaryKey(AlarmCpuInfo record);

    AlarmCpuInfo findNewCpuInfo();
}