package com.xq.mapper;

import com.xq.domain.Dhosts;

public interface DhostsMapper {
    int deleteByPrimaryKey(Long dhostid);

    int insert(Dhosts record);

    int insertSelective(Dhosts record);

    Dhosts selectByPrimaryKey(Long dhostid);

    int updateByPrimaryKeySelective(Dhosts record);

    int updateByPrimaryKey(Dhosts record);
}