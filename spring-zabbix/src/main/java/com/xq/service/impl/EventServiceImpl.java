package com.xq.service.impl;


import com.github.pagehelper.PageHelper;
import com.xq.expansion.EventsExpansion;
import com.xq.mapper.EventsExpansionMapper;
import com.xq.mapper.EventsMapper;
import com.xq.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;


/**
 * @ClassName
 * @Description
 * @author xq
 * @Date
 * @Version
 **/
@Service
public class EventServiceImpl  implements EventService {
    @Autowired
    private EventsExpansionMapper eventsExpansionMapper;
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<EventsExpansion> findAllEvents() {
        PageHelper.startPage(1,10);
        try{}catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return eventsExpansionMapper.findAllEvents();
    }
}
