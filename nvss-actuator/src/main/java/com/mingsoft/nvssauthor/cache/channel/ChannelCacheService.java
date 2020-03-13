package com.mingsoft.nvssauthor.cache.channel;

import com.mingsoft.nvssauthor.domain.TDict;

import java.util.List;

/**
 * @author xq
 */
public interface ChannelCacheService {

    void setChannelType(String key, String value);

    String getChannelType(String key);

    void setChannelTypeList(String key, List<TDict> channelList, long time);

    List<TDict> getChannelTypeList(String key, Class<TDict> tDictClass);
}
