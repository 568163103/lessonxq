package com.xq.mapper;

import com.xq.domain.Dbversion;

public interface DbversionMapper {
    int insert(Dbversion record);

    int insertSelective(Dbversion record);
}