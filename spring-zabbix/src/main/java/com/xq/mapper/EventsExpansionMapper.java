package com.xq.mapper;

import com.xq.expansion.EventsExpansion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EventsExpansionMapper extends EventsMapper{

    List<EventsExpansion> findAllEvents();

}
