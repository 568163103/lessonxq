package com.xq.mapper;

import com.xq.domain.ServerType;

import java.util.List;

public interface ServerTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServerType record);

    int insertSelective(ServerType record);

    ServerType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServerType record);

    int updateByPrimaryKey(ServerType record);

    List<ServerType> findServeTypeAll();
}