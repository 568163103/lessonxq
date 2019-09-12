package com.xq.mapper;

import com.xq.domain.Timeperiods;

public interface TimeperiodsMapper {
    int deleteByPrimaryKey(Long timeperiodid);

    int insert(Timeperiods record);

    int insertSelective(Timeperiods record);

    Timeperiods selectByPrimaryKey(Long timeperiodid);

    int updateByPrimaryKeySelective(Timeperiods record);

    int updateByPrimaryKey(Timeperiods record);
}