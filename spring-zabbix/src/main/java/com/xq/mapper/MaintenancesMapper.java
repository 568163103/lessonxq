package com.xq.mapper;

import com.xq.domain.Maintenances;

public interface MaintenancesMapper {
    int deleteByPrimaryKey(Long maintenanceid);

    int insert(Maintenances record);

    int insertSelective(Maintenances record);

    Maintenances selectByPrimaryKey(Long maintenanceid);

    int updateByPrimaryKeySelective(Maintenances record);

    int updateByPrimaryKey(Maintenances record);
}