package com.xq.mapper;

import com.xq.domain.TbAlarmsubcriType;

public interface TbAlarmsubcriTypeMapper {
    int deleteByPrimaryKey(String alarmType);

    int insert(TbAlarmsubcriType record);

    int insertSelective(TbAlarmsubcriType record);

    TbAlarmsubcriType selectByPrimaryKey(String alarmType);

    int updateByPrimaryKeySelective(TbAlarmsubcriType record);

    int updateByPrimaryKey(TbAlarmsubcriType record);
}