package com.xq.cache.nvss;

import com.alibaba.fastjson.JSON;
import com.xq.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Server 缓存
 *
 * @author xq
 * @since 2019/09/19
 */
@Component
public class ServerCache {
    @Autowired
    private RedisUtil redisUtil;

    public void setServerCache(String JSONServer) {
        redisUtil.set("server:serverList", JSON.toJSONString(JSONServer));
    }

    public String getServerCache(String key) {
        return redisUtil.get(key);
    }


}
