package com.xq.mapper;

import com.xq.domain.ScreensItems;

public interface ScreensItemsMapper {
    int deleteByPrimaryKey(Long screenitemid);

    int insert(ScreensItems record);

    int insertSelective(ScreensItems record);

    ScreensItems selectByPrimaryKey(Long screenitemid);

    int updateByPrimaryKeySelective(ScreensItems record);

    int updateByPrimaryKey(ScreensItems record);
}