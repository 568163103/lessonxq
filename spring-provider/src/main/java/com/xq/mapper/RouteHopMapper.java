package com.xq.mapper;

import com.xq.domain.RouteHop;

public interface RouteHopMapper {
    int insert(RouteHop record);

    int insertSelective(RouteHop record);
}