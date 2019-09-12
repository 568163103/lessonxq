package com.xq.mapper;

import com.xq.domain.GroupDiscovery;

public interface GroupDiscoveryMapper {
    int deleteByPrimaryKey(Long groupid);

    int insert(GroupDiscovery record);

    int insertSelective(GroupDiscovery record);

    GroupDiscovery selectByPrimaryKey(Long groupid);

    int updateByPrimaryKeySelective(GroupDiscovery record);

    int updateByPrimaryKey(GroupDiscovery record);
}