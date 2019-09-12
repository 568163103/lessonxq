package com.xq.mapper;

import com.xq.domain.Users;

public interface UsersMapper {
    int deleteByPrimaryKey(Long userid);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long userid);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}