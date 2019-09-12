package com.xq.mapper;

import com.xq.domain.SysmapUrl;

public interface SysmapUrlMapper {
    int deleteByPrimaryKey(Long sysmapurlid);

    int insert(SysmapUrl record);

    int insertSelective(SysmapUrl record);

    SysmapUrl selectByPrimaryKey(Long sysmapurlid);

    int updateByPrimaryKeySelective(SysmapUrl record);

    int updateByPrimaryKey(SysmapUrl record);
}