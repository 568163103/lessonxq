package com.mingsoft.nvssauthor.service.impl;

import com.mingsoft.nvssauthor.domain.Server;
import com.mingsoft.nvssauthor.mapper.ServerMapper;
import com.mingsoft.nvssauthor.mapper.ServerTypeMapper;
import com.mingsoft.nvssauthor.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xq
 */
@Service
public class ServerServiceImpl implements ServerService {
    @Autowired
    private ServerMapper serverMapper;

    @Autowired
    private ServerTypeMapper serverTypeMapper;

    @Override
    public List<Server> findServerList() {
        return serverMapper.findServerList();
    }

    @Override
    public List<Server> findServerAndType() {
        return serverMapper.findServerAndType();
    }




}
