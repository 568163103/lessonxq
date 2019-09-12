package com.xq.mapper;

import com.xq.domain.Screens;

public interface ScreensMapper {
    int deleteByPrimaryKey(Long screenid);

    int insert(Screens record);

    int insertSelective(Screens record);

    Screens selectByPrimaryKey(Long screenid);

    int updateByPrimaryKeySelective(Screens record);

    int updateByPrimaryKey(Screens record);
}