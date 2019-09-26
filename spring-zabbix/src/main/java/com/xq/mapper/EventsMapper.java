package com.xq.mapper;

import com.xq.domain.Events;

import java.util.List;

public interface EventsMapper {
    int deleteByPrimaryKey(Long eventid);

    int insert(Events record);

    int insertSelective(Events record);

    Events selectByPrimaryKey(Long eventid);

    int updateByPrimaryKeySelective(Events record);

    int updateByPrimaryKey(Events record);

    List<Events> findAllEvents();
}