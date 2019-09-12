package com.xq.mapper;

import com.xq.domain.HostInventory;

public interface HostInventoryMapper {
    int deleteByPrimaryKey(Long hostid);

    int insert(HostInventory record);

    int insertSelective(HostInventory record);

    HostInventory selectByPrimaryKey(Long hostid);

    int updateByPrimaryKeySelective(HostInventory record);

    int updateByPrimaryKey(HostInventory record);
}