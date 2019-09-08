package com.xq.mapper;

import com.xq.domain.Groups;

public interface GroupsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Groups record);

    int insertSelective(Groups record);

    Groups selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Groups record);

    int updateByPrimaryKey(Groups record);
}