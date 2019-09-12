package com.xq.mapper;

import com.xq.domain.ApplicationDiscovery;

public interface ApplicationDiscoveryMapper {
    int deleteByPrimaryKey(Long applicationDiscoveryid);

    int insert(ApplicationDiscovery record);

    int insertSelective(ApplicationDiscovery record);

    ApplicationDiscovery selectByPrimaryKey(Long applicationDiscoveryid);

    int updateByPrimaryKeySelective(ApplicationDiscovery record);

    int updateByPrimaryKey(ApplicationDiscovery record);
}