package com.mingsoft.nvssauthor.service;


import com.mingsoft.nvssauthor.domain.Channel;
import com.mingsoft.nvssauthor.domain.Encoder;
import com.mingsoft.nvssauthor.domain.TDict;

import java.util.List;
import java.util.Map;

/**
 * 摄像机服务类
 */
public interface ChannelService {
    /**
     * 获取摄像机详细信息
     *
     * @return
     */
    List<Channel> getChannelInfoList();

    void setChannelType();

    String getChannelTypeName(String key);

    List<TDict> getChannelTypeList(String key, Class<TDict> tDictClass);

    List<Encoder> getChannelStatusCount(int status);

    List<Encoder> findEncoder(Map<String, Object> params);

}
