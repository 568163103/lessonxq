package com.xq.mapper;

import com.xq.domain.TbAlarmType;

public interface TbAlarmTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAlarmType record);

    int insertSelective(TbAlarmType record);

    TbAlarmType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAlarmType record);

    int updateByPrimaryKey(TbAlarmType record);
}