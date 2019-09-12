package com.xq.mapper;

import com.xq.domain.Graphs;

public interface GraphsMapper {
    int deleteByPrimaryKey(Long graphid);

    int insert(Graphs record);

    int insertSelective(Graphs record);

    Graphs selectByPrimaryKey(Long graphid);

    int updateByPrimaryKeySelective(Graphs record);

    int updateByPrimaryKey(Graphs record);
}