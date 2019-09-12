package com.xq.mapper;

import com.xq.domain.Triggers;

public interface TriggersMapper {
    int deleteByPrimaryKey(Long triggerid);

    int insert(Triggers record);

    int insertSelective(Triggers record);

    Triggers selectByPrimaryKey(Long triggerid);

    int updateByPrimaryKeySelective(Triggers record);

    int updateByPrimaryKey(Triggers record);
}