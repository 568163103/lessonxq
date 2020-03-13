package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.Route;

public interface RouteMapper {
    int deleteByPrimaryKey(Integer routeId);

    int insert(Route record);

    int insertSelective(Route record);

    Route selectByPrimaryKey(Integer routeId);

    int updateByPrimaryKeySelective(Route record);

    int updateByPrimaryKey(Route record);
}