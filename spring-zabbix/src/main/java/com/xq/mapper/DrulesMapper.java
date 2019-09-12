package com.xq.mapper;

import com.xq.domain.Drules;

public interface DrulesMapper {
    int deleteByPrimaryKey(Long druleid);

    int insert(Drules record);

    int insertSelective(Drules record);

    Drules selectByPrimaryKey(Long druleid);

    int updateByPrimaryKeySelective(Drules record);

    int updateByPrimaryKey(Drules record);
}