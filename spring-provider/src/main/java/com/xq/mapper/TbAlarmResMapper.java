package com.xq.mapper;

import com.xq.domain.TbAlarmRes;

public interface TbAlarmResMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAlarmRes record);

    int insertSelective(TbAlarmRes record);

    TbAlarmRes selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAlarmRes record);

    int updateByPrimaryKey(TbAlarmRes record);
}