package com.xq.mapper;

import com.xq.domain.Httptestitem;

public interface HttptestitemMapper {
    int deleteByPrimaryKey(Long httptestitemid);

    int insert(Httptestitem record);

    int insertSelective(Httptestitem record);

    Httptestitem selectByPrimaryKey(Long httptestitemid);

    int updateByPrimaryKeySelective(Httptestitem record);

    int updateByPrimaryKey(Httptestitem record);
}