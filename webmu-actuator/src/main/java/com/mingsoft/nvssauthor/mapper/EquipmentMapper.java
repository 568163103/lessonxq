package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.Equipment;

public interface EquipmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Equipment record);

    int insertSelective(Equipment record);

    Equipment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Equipment record);

    int updateByPrimaryKey(Equipment record);
}