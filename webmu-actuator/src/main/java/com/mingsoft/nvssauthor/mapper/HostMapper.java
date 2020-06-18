package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.Host;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface HostMapper {
    int deleteByPrimaryKey(String id);

    int insert(Host record);

    int insertSelective(Host record);

    Host selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Host record);

    int updateByPrimaryKey(Host record);

    List<Host> findAllServerInfo();
}