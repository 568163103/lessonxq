package com.xq.mapper;

import com.xq.domain.UsersGroups;

public interface UsersGroupsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UsersGroups record);

    int insertSelective(UsersGroups record);

    UsersGroups selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UsersGroups record);

    int updateByPrimaryKey(UsersGroups record);
}