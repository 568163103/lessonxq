package com.xq.mapper;

import com.xq.domain.UserGroupResource;

public interface UserGroupResourceMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserGroupResource record);

    int insertSelective(UserGroupResource record);

    UserGroupResource selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserGroupResource record);

    int updateByPrimaryKey(UserGroupResource record);
}