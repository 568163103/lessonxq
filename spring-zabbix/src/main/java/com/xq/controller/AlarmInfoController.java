package com.xq.controller;

import com.xq.domain.Events;
import com.xq.expansion.EventsExpansion;
import com.xq.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description
 * @Auror
 * @Date
 * @Version
 **/

@RestController
@RequestMapping("/alarm")
public class AlarmInfoController {
    @Autowired
    private EventService eventService;

    @PostMapping(value = "/alarmInfoList",produces = "application/json;charset=utf-8")
    public Map<String,Object> alarmInfoList(){
        Map<String,Object> result = new HashMap<>(16);
        List<EventsExpansion> eventsList = eventService.findAllEvents();
        result.put("eventsList",eventsList);

        return result;
    }
}
