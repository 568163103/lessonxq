package com.xq.service;

import com.xq.expansion.EventsExpansion;
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

    List<EventsExpansion> findAllEvents();
}
