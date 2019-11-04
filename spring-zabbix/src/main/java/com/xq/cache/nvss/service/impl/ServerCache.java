package com.xq.cache.nvss.service.impl;

import com.xq.cache.nvss.service.RedisService;
import com.xq.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Server 缓存
 *
 * @author xq
 * @since 2019/09/19
 */
@Component
public class ServerCache implements RedisService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void setServerCacheList(String key,List list) {
        redisUtil.putListCacheWithExpireTime(key, list, 1000000000);
    }

    @Override
    public void refresh(String key) {
        redisUtil.delete(key);
    }

    @Override
    public void add(String key, String value) {
        redisUtil.set(key, value);
    }

    @Override
    public void remove(String key) {
        redisUtil.delete(key);
    }

    @Override
    public void update(String key,String value) {
        redisUtil.set(key,value);
    }

    @Override
    public String get(String key) {
        return redisUtil.get(key);
    }

    @Override
    public <T> List<T> getListCache(String key, Class<T> targetClass) {
        return redisUtil.getListCache(key, targetClass);
    }


}
