package com.xq.mapper;

import com.xq.domain.UserGroupRelation;
import org.apache.ibatis.annotations.Param;

public interface UserGroupRelationMapper {
    int deleteByPrimaryKey(@Param("userGroupId") Integer userGroupId, @Param("groupId") String groupId);

    int insert(UserGroupRelation record);

    int insertSelective(UserGroupRelation record);

    UserGroupRelation selectByPrimaryKey(@Param("userGroupId") Integer userGroupId, @Param("groupId") String groupId);

    int updateByPrimaryKeySelective(UserGroupRelation record);

    int updateByPrimaryKey(UserGroupRelation record);
}