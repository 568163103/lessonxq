package com.xq.mapper;

import com.xq.domain.Resolution;

public interface ResolutionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resolution record);

    int insertSelective(Resolution record);

    Resolution selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resolution record);

    int updateByPrimaryKey(Resolution record);
}