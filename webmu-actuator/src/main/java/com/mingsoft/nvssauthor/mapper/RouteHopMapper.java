package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.RouteHop;

public interface RouteHopMapper {
    int insert(RouteHop record);

    int insertSelective(RouteHop record);
}