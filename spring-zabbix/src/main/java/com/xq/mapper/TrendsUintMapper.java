package com.xq.mapper;

import com.xq.domain.TrendsUint;
import org.apache.ibatis.annotations.Param;

public interface TrendsUintMapper {
    int deleteByPrimaryKey(@Param("itemid") Long itemid, @Param("clock") Integer clock);

    int insert(TrendsUint record);

    int insertSelective(TrendsUint record);

    TrendsUint selectByPrimaryKey(@Param("itemid") Long itemid, @Param("clock") Integer clock);

    int updateByPrimaryKeySelective(TrendsUint record);

    int updateByPrimaryKey(TrendsUint record);
}