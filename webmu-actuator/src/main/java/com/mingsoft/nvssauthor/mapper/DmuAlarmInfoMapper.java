package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.DmuAlarmInfo;
import org.springframework.stereotype.Component;

@Component
public interface DmuAlarmInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DmuAlarmInfo record);

    int insertSelective(DmuAlarmInfo record);

    DmuAlarmInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmuAlarmInfo record);

    int updateByPrimaryKey(DmuAlarmInfo record);
}