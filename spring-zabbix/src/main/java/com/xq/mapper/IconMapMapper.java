package com.xq.mapper;

import com.xq.domain.IconMap;

public interface IconMapMapper {
    int deleteByPrimaryKey(Long iconmapid);

    int insert(IconMap record);

    int insertSelective(IconMap record);

    IconMap selectByPrimaryKey(Long iconmapid);

    int updateByPrimaryKeySelective(IconMap record);

    int updateByPrimaryKey(IconMap record);
}