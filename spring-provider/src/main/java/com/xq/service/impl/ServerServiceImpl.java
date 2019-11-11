package com.xq.service.impl;

import com.alibaba.fastjson.JSON;
import com.xq.cache.nvss.service.impl.ServerCache;
import com.xq.constant.Constant;
import com.xq.domain.Server;
import com.xq.mapper.ServerMapper;
import com.xq.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link ServerService}
 * {@link ServerMapper}
 * 服务器接口类
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

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<Server> findServerAll() {
        List<Server> serverList = serverCache.getListCache(Constant.SERVER_LIST,Server.class);
        if (serverList!=null && serverList.size()>0) {
           return serverList;
        } else {
            serverList = serverMapper.findAllServer();
            serverCache.setServerCacheList(Constant.SERVER_LIST,serverList);
        }

        return serverList;
    }
}
