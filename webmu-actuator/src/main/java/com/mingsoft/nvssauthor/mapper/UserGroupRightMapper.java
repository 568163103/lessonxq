package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.UserGroupRight;
import org.apache.ibatis.annotations.Param;

public interface UserGroupRightMapper {
    int deleteByPrimaryKey(@Param("userId") String userId, @Param("groupId") String groupId);

    int insert(UserGroupRight record);

    int insertSelective(UserGroupRight record);

    UserGroupRight selectByPrimaryKey(@Param("userId") String userId, @Param("groupId") String groupId);

    int updateByPrimaryKeySelective(UserGroupRight record);

    int updateByPrimaryKey(UserGroupRight record);
}