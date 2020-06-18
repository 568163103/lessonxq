package com.mingsoft.nvssauthor.cache.server.service.impl;

import com.mingsoft.nvssauthor.cache.server.service.ServerCacheService;
import com.mingsoft.nvssauthor.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ServerCacheImpl implements ServerCacheService {

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void setServerType(String key, String value) {
        redisUtil.set(key,value);
    }
}
