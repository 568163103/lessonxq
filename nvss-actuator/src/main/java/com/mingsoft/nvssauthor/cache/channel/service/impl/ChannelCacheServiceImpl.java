package com.mingsoft.nvssauthor.cache.channel.service.impl;

import com.mingsoft.nvssauthor.cache.channel.ChannelCacheService;
import com.mingsoft.nvssauthor.domain.Channel;
import com.mingsoft.nvssauthor.domain.TDict;
import com.mingsoft.nvssauthor.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xq
 */
@Component
public class ChannelCacheServiceImpl implements ChannelCacheService {

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void setChannelType(String key, String value) {
        redisUtil.set(key,value);
    }

    @Override
    public String getChannelType(String key) {
     return redisUtil.get(key);
    }

    @Override
    public void setChannelTypeList(String key, List<TDict> channelList, long time) {
       redisUtil.putListCacheWithExpireTime(key,channelList,time);
    }

    @Override
    public List<TDict> getChannelTypeList(String key,Class<TDict> tClass) {
      return redisUtil.getListCache(key,tClass);
    }
}
