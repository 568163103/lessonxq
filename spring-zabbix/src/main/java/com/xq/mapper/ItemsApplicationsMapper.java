package com.xq.mapper;

import com.xq.domain.ItemsApplications;

public interface ItemsApplicationsMapper {
    int deleteByPrimaryKey(Long itemappid);

    int insert(ItemsApplications record);

    int insertSelective(ItemsApplications record);

    ItemsApplications selectByPrimaryKey(Long itemappid);

    int updateByPrimaryKeySelective(ItemsApplications record);

    int updateByPrimaryKey(ItemsApplications record);
}