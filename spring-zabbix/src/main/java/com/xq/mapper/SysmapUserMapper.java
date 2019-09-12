package com.xq.mapper;

import com.xq.domain.SysmapUser;

public interface SysmapUserMapper {
    int deleteByPrimaryKey(Long sysmapuserid);

    int insert(SysmapUser record);

    int insertSelective(SysmapUser record);

    SysmapUser selectByPrimaryKey(Long sysmapuserid);

    int updateByPrimaryKeySelective(SysmapUser record);

    int updateByPrimaryKey(SysmapUser record);
}