package com.xq.mapper;

import com.xq.domain.Groups;

public interface GroupsMapper {
    int deleteByPrimaryKey(Long groupid);

    int insert(Groups record);

    int insertSelective(Groups record);

    Groups selectByPrimaryKey(Long groupid);

    int updateByPrimaryKeySelective(Groups record);

    int updateByPrimaryKey(Groups record);
}