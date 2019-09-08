package com.xq.mapper;

import com.xq.domain.EquipmentType;

public interface EquipmentTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EquipmentType record);

    int insertSelective(EquipmentType record);

    EquipmentType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EquipmentType record);

    int updateByPrimaryKey(EquipmentType record);
}