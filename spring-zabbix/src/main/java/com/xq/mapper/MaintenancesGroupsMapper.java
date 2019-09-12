package com.xq.mapper;

import com.xq.domain.MaintenancesGroups;

public interface MaintenancesGroupsMapper {
    int deleteByPrimaryKey(Long maintenanceGroupid);

    int insert(MaintenancesGroups record);

    int insertSelective(MaintenancesGroups record);

    MaintenancesGroups selectByPrimaryKey(Long maintenanceGroupid);

    int updateByPrimaryKeySelective(MaintenancesGroups record);

    int updateByPrimaryKey(MaintenancesGroups record);
}