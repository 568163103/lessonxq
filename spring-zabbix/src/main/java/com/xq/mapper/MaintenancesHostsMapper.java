package com.xq.mapper;

import com.xq.domain.MaintenancesHosts;

public interface MaintenancesHostsMapper {
    int deleteByPrimaryKey(Long maintenanceHostid);

    int insert(MaintenancesHosts record);

    int insertSelective(MaintenancesHosts record);

    MaintenancesHosts selectByPrimaryKey(Long maintenanceHostid);

    int updateByPrimaryKeySelective(MaintenancesHosts record);

    int updateByPrimaryKey(MaintenancesHosts record);
}