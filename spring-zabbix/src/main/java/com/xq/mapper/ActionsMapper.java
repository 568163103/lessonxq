package com.xq.mapper;

import com.xq.domain.Actions;

public interface ActionsMapper {
    int deleteByPrimaryKey(Long actionid);

    int insert(Actions record);

    int insertSelective(Actions record);

    Actions selectByPrimaryKey(Long actionid);

    int updateByPrimaryKeySelective(Actions record);

    int updateByPrimaryKey(Actions record);
}