package com.xq.mapper;

import com.xq.domain.DmuEquipment;

public interface DmuEquipmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(DmuEquipment record);

    int insertSelective(DmuEquipment record);

    DmuEquipment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DmuEquipment record);

    int updateByPrimaryKey(DmuEquipment record);
}