package com.xq.service;


import com.alibaba.fastjson.JSONObject;
import com.xq.service.impl.ServerServiceImpl;

import java.util.Map;

/**
 * {@link ServerServiceImpl}
 *
 * @author xq
 * @since 2019/09/18
 */
public interface ServerService {

    JSONObject findServerAll();
}
