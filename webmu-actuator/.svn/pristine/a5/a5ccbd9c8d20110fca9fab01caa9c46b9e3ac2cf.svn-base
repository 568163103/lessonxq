package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.Server;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Server record);

    int insertSelective(Server record);

    Server selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);

    List<Server> findServerList();

    List<Server> findServerAndType();

    String createId(String typeName, String codeType, String channelnum, String position);

    Server findByIp(String serverIp);
}