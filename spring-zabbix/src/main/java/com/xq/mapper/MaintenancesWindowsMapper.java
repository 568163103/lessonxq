package com.xq.mapper;

import com.xq.domain.MaintenancesWindows;

public interface MaintenancesWindowsMapper {
    int deleteByPrimaryKey(Long maintenanceTimeperiodid);

    int insert(MaintenancesWindows record);

    int insertSelective(MaintenancesWindows record);

    MaintenancesWindows selectByPrimaryKey(Long maintenanceTimeperiodid);

    int updateByPrimaryKeySelective(MaintenancesWindows record);

    int updateByPrimaryKey(MaintenancesWindows record);
}