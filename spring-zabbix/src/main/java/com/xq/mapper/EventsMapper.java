package com.xq.mapper;

import com.xq.domain.Events;

public interface EventsMapper {
    int deleteByPrimaryKey(Long eventid);

    int insert(Events record);

    int insertSelective(Events record);

    Events selectByPrimaryKey(Long eventid);

    int updateByPrimaryKeySelective(Events record);

    int updateByPrimaryKey(Events record);
}