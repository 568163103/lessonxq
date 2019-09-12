package com.xq.mapper;

import com.xq.domain.Hosts;

public interface HostsMapper {
    int deleteByPrimaryKey(Long hostid);

    int insert(Hosts record);

    int insertSelective(Hosts record);

    Hosts selectByPrimaryKey(Long hostid);

    int updateByPrimaryKeySelective(Hosts record);

    int updateByPrimaryKey(Hosts record);
}