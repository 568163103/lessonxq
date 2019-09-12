package com.xq.mapper;

import com.xq.domain.GraphDiscovery;

public interface GraphDiscoveryMapper {
    int deleteByPrimaryKey(Long graphid);

    int insert(GraphDiscovery record);

    int insertSelective(GraphDiscovery record);

    GraphDiscovery selectByPrimaryKey(Long graphid);

    int updateByPrimaryKeySelective(GraphDiscovery record);

    int updateByPrimaryKey(GraphDiscovery record);
}