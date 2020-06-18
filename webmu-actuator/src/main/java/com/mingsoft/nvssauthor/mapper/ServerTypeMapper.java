package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.ServerType;
import org.springframework.stereotype.Component;

@Component
public interface ServerTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServerType record);

    int insertSelective(ServerType record);

    ServerType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServerType record);

    int updateByPrimaryKey(ServerType record);
}