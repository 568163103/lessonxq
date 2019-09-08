package com.xq.mapper;

import com.xq.domain.TUserTrace;

public interface TUserTraceMapper {
    int deleteByPrimaryKey(Integer auid);

    int insert(TUserTrace record);

    int insertSelective(TUserTrace record);

    TUserTrace selectByPrimaryKey(Integer auid);

    int updateByPrimaryKeySelective(TUserTrace record);

    int updateByPrimaryKey(TUserTrace record);
}