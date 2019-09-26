package com.xq.service.impl;

import com.xq.domain.Events;
import com.xq.mapper.EventsMapper;
import com.xq.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;

/**
 * @ClassName
 * @Description
 * @author xq
 * @Date
 * @Version
 **/
public class EventServiceImpl implements EventService {
    @Autowired
    private EventsMapper eventsMapper;

    @Override
    public List<Events> findAllEvents() {
        return eventsMapper.findAllEvents();
    }
}
