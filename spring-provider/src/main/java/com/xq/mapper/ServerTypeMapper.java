package com.xq.mapper;

import com.xq.domain.ServerType;

public interface ServerTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServerType record);

    int insertSelective(ServerType record);

    ServerType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServerType record);

    int updateByPrimaryKey(ServerType record);
}