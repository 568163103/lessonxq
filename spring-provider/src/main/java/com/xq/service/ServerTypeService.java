package com.xq.service;

import com.xq.domain.ServerType;

import java.util.List;

/**
 * {@link }
 * 服务器类型 类
 * @author xq
 */
public interface ServerTypeService {
    /**
     * 查询所有服务器类型
     * @return
     */
    List<ServerType> findServeTypeAll();
}
