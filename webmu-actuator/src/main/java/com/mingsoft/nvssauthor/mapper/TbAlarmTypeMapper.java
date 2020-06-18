package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.TbAlarmType;
import org.springframework.stereotype.Component;

@Component
public interface TbAlarmTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAlarmType record);

    int insertSelective(TbAlarmType record);

    TbAlarmType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAlarmType record);

    int updateByPrimaryKey(TbAlarmType record);

    TbAlarmType findByTypeId(String typeId);
}