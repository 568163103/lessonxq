package com.xq.mapper;

import com.xq.domain.Sessions;

public interface SessionsMapper {
    int deleteByPrimaryKey(String sessionid);

    int insert(Sessions record);

    int insertSelective(Sessions record);

    Sessions selectByPrimaryKey(String sessionid);

    int updateByPrimaryKeySelective(Sessions record);

    int updateByPrimaryKey(Sessions record);
}