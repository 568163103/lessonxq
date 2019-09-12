package com.xq.mapper;

import com.xq.domain.GroupPrototype;

public interface GroupPrototypeMapper {
    int deleteByPrimaryKey(Long groupPrototypeid);

    int insert(GroupPrototype record);

    int insertSelective(GroupPrototype record);

    GroupPrototype selectByPrimaryKey(Long groupPrototypeid);

    int updateByPrimaryKeySelective(GroupPrototype record);

    int updateByPrimaryKey(GroupPrototype record);
}