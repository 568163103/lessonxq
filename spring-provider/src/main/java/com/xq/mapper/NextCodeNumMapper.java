package com.xq.mapper;

import com.xq.domain.NextCodeNum;

public interface NextCodeNumMapper {
    int deleteByPrimaryKey(String name);

    int insert(NextCodeNum record);

    int insertSelective(NextCodeNum record);

    NextCodeNum selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(NextCodeNum record);

    int updateByPrimaryKey(NextCodeNum record);
}