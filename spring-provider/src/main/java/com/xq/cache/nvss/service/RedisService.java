package com.xq.cache.nvss.service;

import java.util.List;

/**
 * redis 接口定义
 *
 * @author xq
 */
public interface RedisService {
    /**
     * 添加一个缓存
     *
     * @param key   键位
     * @param value 值
     */
    public void add(String key, String value);

    /**
     * 删除一个缓存
     */
    public void remove(String key);

    /**
     * 更新一个缓存
     */
    public void update(String key, String value);


    /**
     * 获取一个缓存
     */
    public String get(String key);

    /**
     * 获取一个集合
     *
     * @param key
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> List<T> getListCache(final String key, Class<T> targetClass);

    /**
     * 设置一个集合缓存
     *
     * @param key
     * @param list
     */
    public void setServerCacheList(String key, List list);

    /**
     * 刷新当前缓存
     *
     * @param key 键
     */

    public void refresh(String key);

}
