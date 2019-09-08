package com.xq.mapper;

import com.xq.domain.EquipmentCorp;

public interface EquipmentCorpMapper {
    int deleteByPrimaryKey(String id);

    int insert(EquipmentCorp record);

    int insertSelective(EquipmentCorp record);

    EquipmentCorp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EquipmentCorp record);

    int updateByPrimaryKey(EquipmentCorp record);
}