package com.xq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xq.cache.nvss.ServerCache;
import com.xq.domain.Server;
import com.xq.mapper.ServerMapper;
import com.xq.service.ServerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link ServerService}
 * {@link ServerMapper}
 *
 * @author xq
 * @since 2019/09/18
 */
@Service
public class ServerServiceImpl implements ServerService {
    /**
     * server dao 层数据接口
     */
    @Autowired
    private ServerMapper serverMapper;
    /**
     * redisServer 缓存类
     */
    @Autowired
    private ServerCache serverCache;


    @Override
    public JSONObject findServerAll() {
        String resultCache = null;
        JSONObject result = new JSONObject();
        resultCache = serverCache.getServerCache("server:serverList");
        if (StringUtils.isNotBlank(resultCache)) {
            result.put("serverList",resultCache.substring(0,resultCache.length()-1).replaceAll("\\\\",""));
        } else {
            List<Server> serverList = serverMapper.findAllServer();
            result.put("serverList", serverList);
            serverCache.setServerCache(JSON.toJSONString(serverMapper.findAllServer()));
        }

        return result;
    }
}
