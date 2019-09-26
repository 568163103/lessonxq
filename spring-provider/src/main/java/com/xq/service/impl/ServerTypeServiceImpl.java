package com.xq.service.impl;

import com.xq.cache.nvss.service.impl.ServerCache;
import com.xq.constant.Constant;
import com.xq.domain.ServerType;
import com.xq.mapper.ServerTypeMapper;
import com.xq.service.ServerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务器类型实现类
 *
 * @author xq
 */
@Service
public class ServerTypeServiceImpl implements ServerTypeService {
    @Autowired
    private ServerTypeMapper serverTypeMapper;
    @Autowired
    private ServerCache serverCache;

    @Override
    public List<ServerType> findServeTypeAll() {
        List<ServerType> serverTypeList = serverCache.getListCache(Constant.SERVER_TYPE_LIST, ServerType.class);
        if (serverTypeList != null && serverTypeList.size() > 0) {
            return serverTypeList;
        } else {
            serverTypeList = serverTypeMapper.findServeTypeAll();
            serverCache.setServerCacheList(Constant.SERVER_TYPE_LIST, serverTypeList);
        }

        return serverTypeList;
    }
}
