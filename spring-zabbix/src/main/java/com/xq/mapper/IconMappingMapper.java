package com.xq.mapper;

import com.xq.domain.IconMapping;

public interface IconMappingMapper {
    int deleteByPrimaryKey(Long iconmappingid);

    int insert(IconMapping record);

    int insertSelective(IconMapping record);

    IconMapping selectByPrimaryKey(Long iconmappingid);

    int updateByPrimaryKeySelective(IconMapping record);

    int updateByPrimaryKey(IconMapping record);
}