package com.xq.mapper;

import com.xq.domain.Server;

import java.util.List;

public interface ServerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Server record);

    int insertSelective(Server record);

    Server selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);

    List<Server> findAllServer();
}