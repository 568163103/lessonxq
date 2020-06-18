package com.mingsoft.nvssauthor.service;

import com.alibaba.fastjson.JSONArray;
import com.mingsoft.nvssauthor.domain.CpuMemoryInfo;
import com.mingsoft.nvssauthor.domain.Host;
import com.mingsoft.nvssauthor.domain.Server;
import com.mingsoft.nvssauthor.domain.ServerStatusInfo;

import java.text.ParseException;
import java.util.List;

/**
 * 服务器接口列表
 *
 * @author xq
 */

public interface ServerService {
    /**
     * 获得所有的  server服务器
     *
     * @return
     */
    List<Server> findServerList();

    /**
     * 返回服务器 和 类型
     *
     * @return
     */
    List<Server> findServerAndType();

    List<ServerStatusInfo> initServerStatus() throws ParseException;

    CpuMemoryInfo getCpuAndMemoryInfo();

    String createId(String typeName, String codeType, String channelnum, String position);

    /**
     * 更新服务器状态
     * @param onlineJSONArray 在线服务器数组
     * @param offOnlineJSONArray 离线服务器数组
     * @param hosts  服务器列表
     */
    void updateServerStatus(JSONArray onlineJSONArray, JSONArray offOnlineJSONArray, List<Host> hosts);


}
