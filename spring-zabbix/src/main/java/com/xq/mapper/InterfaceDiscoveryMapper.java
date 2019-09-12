package com.xq.mapper;

import com.xq.domain.InterfaceDiscovery;

public interface InterfaceDiscoveryMapper {
    int deleteByPrimaryKey(Long interfaceid);

    int insert(InterfaceDiscovery record);

    int insertSelective(InterfaceDiscovery record);

    InterfaceDiscovery selectByPrimaryKey(Long interfaceid);

    int updateByPrimaryKeySelective(InterfaceDiscovery record);

    int updateByPrimaryKey(InterfaceDiscovery record);
}