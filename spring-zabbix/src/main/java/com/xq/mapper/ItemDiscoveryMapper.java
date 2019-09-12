package com.xq.mapper;

import com.xq.domain.ItemDiscovery;

public interface ItemDiscoveryMapper {
    int deleteByPrimaryKey(Long itemdiscoveryid);

    int insert(ItemDiscovery record);

    int insertSelective(ItemDiscovery record);

    ItemDiscovery selectByPrimaryKey(Long itemdiscoveryid);

    int updateByPrimaryKeySelective(ItemDiscovery record);

    int updateByPrimaryKey(ItemDiscovery record);
}