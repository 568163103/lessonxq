package com.xq.mapper;

import com.xq.domain.SysmapElementUrl;

public interface SysmapElementUrlMapper {
    int deleteByPrimaryKey(Long sysmapelementurlid);

    int insert(SysmapElementUrl record);

    int insertSelective(SysmapElementUrl record);

    SysmapElementUrl selectByPrimaryKey(Long sysmapelementurlid);

    int updateByPrimaryKeySelective(SysmapElementUrl record);

    int updateByPrimaryKey(SysmapElementUrl record);
}