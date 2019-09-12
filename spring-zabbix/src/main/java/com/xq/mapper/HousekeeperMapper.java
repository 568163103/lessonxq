package com.xq.mapper;

import com.xq.domain.Housekeeper;

public interface HousekeeperMapper {
    int deleteByPrimaryKey(Long housekeeperid);

    int insert(Housekeeper record);

    int insertSelective(Housekeeper record);

    Housekeeper selectByPrimaryKey(Long housekeeperid);

    int updateByPrimaryKeySelective(Housekeeper record);

    int updateByPrimaryKey(Housekeeper record);
}