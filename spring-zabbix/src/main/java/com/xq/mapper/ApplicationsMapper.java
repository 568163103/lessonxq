package com.xq.mapper;

import com.xq.domain.Applications;

public interface ApplicationsMapper {
    int deleteByPrimaryKey(Long applicationid);

    int insert(Applications record);

    int insertSelective(Applications record);

    Applications selectByPrimaryKey(Long applicationid);

    int updateByPrimaryKeySelective(Applications record);

    int updateByPrimaryKey(Applications record);
}