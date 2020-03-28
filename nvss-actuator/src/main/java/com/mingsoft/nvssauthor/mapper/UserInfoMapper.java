package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.UserInfo;

import java.util.List;


public interface UserInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> userInfoAndUser();


}