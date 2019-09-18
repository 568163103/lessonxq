package com.xq.service;


import com.xq.domain.Server;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author xq
 * @since 2019/09/18
 */
public interface ServerService {

    Map<String,Object> findServerAll();
}
