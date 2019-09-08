package com.xq.mapper;

import com.xq.domain.ServerExtra;

public interface ServerExtraMapper {
    int deleteByPrimaryKey(String id);

    int insert(ServerExtra record);

    int insertSelective(ServerExtra record);

    ServerExtra selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ServerExtra record);

    int updateByPrimaryKey(ServerExtra record);
}