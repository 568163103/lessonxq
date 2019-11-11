package com.xq.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.xq.cache.nvss.service.impl.ServerCache;
import com.xq.expansion.EventsExpansion;
import com.xq.mapper.EventsExpansionMapper;
import com.xq.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import java.util.List;


/**
 * @author xq
 * @ClassName
 * @Description
 * @Date
 * @Version
 **/
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventsExpansionMapper eventsExpansionMapper;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<EventsExpansion> findAllEvents(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", required = false) int pageSize
    ) {

        String result = restTemplate.postForObject("http://spring-cloud-nacos-provider/server/findAllServer", null, String.class);
        JSONArray jsonArray = JSON.parseObject(result).getJSONArray("serverList");
        for (int i = 0; i < jsonArray.size(); i++) {
            System.out.println(jsonArray.getJSONObject(i).getString("name"));
        }
        PageHelper.startPage(pageNum, pageSize);

        return eventsExpansionMapper.findAllEvents();
    }
}
