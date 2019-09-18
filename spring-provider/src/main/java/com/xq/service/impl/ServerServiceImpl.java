package com.xq.service.impl;

import com.xq.domain.Server;
import com.xq.mapper.ServerMapper;
import com.xq.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link ServerService}
 * {@link ServerMapper}
 * @author xq
 * @since 2019/09/18
 */
@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerMapper serverMapper;


    @Override
    public Map<String,Object> findServerAll() {
        Map<String,Object> result = new HashMap<>(16);
        result.put("server",serverMapper.findAllServer());
        return result;
    }
}
