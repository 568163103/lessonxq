package com.xq.mapper;

import com.xq.domain.Mappings;

public interface MappingsMapper {
    int deleteByPrimaryKey(Long mappingid);

    int insert(Mappings record);

    int insertSelective(Mappings record);

    Mappings selectByPrimaryKey(Long mappingid);

    int updateByPrimaryKeySelective(Mappings record);

    int updateByPrimaryKey(Mappings record);
}