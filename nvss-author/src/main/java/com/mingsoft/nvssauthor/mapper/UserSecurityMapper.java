package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.UserSecurity;

public interface UserSecurityMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserSecurity record);

    int insertSelective(UserSecurity record);

    UserSecurity selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserSecurity record);

    int updateByPrimaryKey(UserSecurity record);
}