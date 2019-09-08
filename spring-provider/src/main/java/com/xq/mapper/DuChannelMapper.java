package com.xq.mapper;

import com.xq.domain.DuChannel;

public interface DuChannelMapper {
    int deleteByPrimaryKey(String id);

    int insert(DuChannel record);

    int insertSelective(DuChannel record);

    DuChannel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DuChannel record);

    int updateByPrimaryKey(DuChannel record);
}