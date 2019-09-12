package com.xq.mapper;

import com.xq.domain.Httptest;

public interface HttptestMapper {
    int deleteByPrimaryKey(Long httptestid);

    int insert(Httptest record);

    int insertSelective(Httptest record);

    Httptest selectByPrimaryKey(Long httptestid);

    int updateByPrimaryKeySelective(Httptest record);

    int updateByPrimaryKey(Httptest record);
}