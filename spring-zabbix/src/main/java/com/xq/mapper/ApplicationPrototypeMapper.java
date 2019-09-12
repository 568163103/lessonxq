package com.xq.mapper;

import com.xq.domain.ApplicationPrototype;

public interface ApplicationPrototypeMapper {
    int deleteByPrimaryKey(Long applicationPrototypeid);

    int insert(ApplicationPrototype record);

    int insertSelective(ApplicationPrototype record);

    ApplicationPrototype selectByPrimaryKey(Long applicationPrototypeid);

    int updateByPrimaryKeySelective(ApplicationPrototype record);

    int updateByPrimaryKey(ApplicationPrototype record);
}