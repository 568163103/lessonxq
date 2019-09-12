package com.xq.mapper;

import com.xq.domain.HostsGroups;

public interface HostsGroupsMapper {
    int deleteByPrimaryKey(Long hostgroupid);

    int insert(HostsGroups record);

    int insertSelective(HostsGroups record);

    HostsGroups selectByPrimaryKey(Long hostgroupid);

    int updateByPrimaryKeySelective(HostsGroups record);

    int updateByPrimaryKey(HostsGroups record);
}