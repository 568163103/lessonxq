package com.mingsoft.nvssauthor.service.impl;

import com.mingsoft.nvssauthor.domain.CpuMemoryInfo;
import com.mingsoft.nvssauthor.domain.Server;
import com.mingsoft.nvssauthor.domain.ServerStatusInfo;
import com.mingsoft.nvssauthor.mapper.CpuMemoryInfoMapper;
import com.mingsoft.nvssauthor.mapper.ServerMapper;
import com.mingsoft.nvssauthor.mapper.ServerStatusInfoMapper;
import com.mingsoft.nvssauthor.mapper.ServerTypeMapper;
import com.mingsoft.nvssauthor.service.ServerService;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private ServerStatusInfoMapper serverStatusInfoMapper;
    @Autowired
    private CpuMemoryInfoMapper cpuMemoryInfoMapper;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public List<Server> findServerList() {
        return serverMapper.findServerList();
    }

    @Override
    public List<Server> findServerAndType() {
        return serverMapper.findServerAndType();
    }

    @Override
    public List<ServerStatusInfo> initServerStatus() throws ParseException {

        List<ServerStatusInfo> list = serverStatusInfoMapper.initServerStatus();

        long time = new Date().getTime();
        for (ServerStatusInfo serverStatusInfo : list) {
            serverStatusInfo.setSpare1(dateFormat.format(new Date(time + 30000)));
            time += 3000;
        }
        return list;
    }

    @Override
    public CpuMemoryInfo getCpuAndMemoryInfo() {
        return cpuMemoryInfoMapper.getNewCpuMemoryInfo();
    }


}
