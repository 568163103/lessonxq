package com.xq.mapper;

import com.xq.domain.Sysmaps;

public interface SysmapsMapper {
    int deleteByPrimaryKey(Long sysmapid);

    int insert(Sysmaps record);

    int insertSelective(Sysmaps record);

    Sysmaps selectByPrimaryKey(Long sysmapid);

    int updateByPrimaryKeySelective(Sysmaps record);

    int updateByPrimaryKey(Sysmaps record);
}