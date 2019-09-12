package com.xq.mapper;

import com.xq.domain.Trends;
import org.apache.ibatis.annotations.Param;

public interface TrendsMapper {
    int deleteByPrimaryKey(@Param("itemid") Long itemid, @Param("clock") Integer clock);

    int insert(Trends record);

    int insertSelective(Trends record);

    Trends selectByPrimaryKey(@Param("itemid") Long itemid, @Param("clock") Integer clock);

    int updateByPrimaryKeySelective(Trends record);

    int updateByPrimaryKey(Trends record);
}