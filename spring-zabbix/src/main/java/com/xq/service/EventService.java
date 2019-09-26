package com.xq.service;

import com.xq.domain.Events;
import com.xq.service.impl.EventServiceImpl;

import java.util.List;

/**
 * {@link EventServiceImpl}
 * @ClassName
 * @Description
 * @Auror
 * @Date
 * @Version
 **/
public interface EventService {

    List<Events> findAllEvents();
}
