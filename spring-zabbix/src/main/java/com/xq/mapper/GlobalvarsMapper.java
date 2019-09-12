package com.xq.mapper;

import com.xq.domain.Globalvars;

public interface GlobalvarsMapper {
    int deleteByPrimaryKey(Long globalvarid);

    int insert(Globalvars record);

    int insertSelective(Globalvars record);

    Globalvars selectByPrimaryKey(Long globalvarid);

    int updateByPrimaryKeySelective(Globalvars record);

    int updateByPrimaryKey(Globalvars record);
}