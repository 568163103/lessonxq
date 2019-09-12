package com.xq.mapper;

import com.xq.domain.HostDiscovery;

public interface HostDiscoveryMapper {
    int deleteByPrimaryKey(Long hostid);

    int insert(HostDiscovery record);

    int insertSelective(HostDiscovery record);

    HostDiscovery selectByPrimaryKey(Long hostid);

    int updateByPrimaryKeySelective(HostDiscovery record);

    int updateByPrimaryKey(HostDiscovery record);
}