package com.xq.service;


import com.xq.domain.Server;
import com.xq.service.impl.ServerServiceImpl;

import java.util.List;

/**
 * {@link ServerServiceImpl}
 *
 * @author xq
 * @since 2019/09/18
 */
public interface ServerService {
    /**
     * 查询所有服务器
     * @return
     */
    List<Server> findServerAll();
}
