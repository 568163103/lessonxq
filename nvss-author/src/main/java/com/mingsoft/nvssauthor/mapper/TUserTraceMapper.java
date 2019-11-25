package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.TUserTrace;

public interface TUserTraceMapper {
    int deleteByPrimaryKey(Integer auid);

    int insert(TUserTrace record);

    int insertSelective(TUserTrace record);

    TUserTrace selectByPrimaryKey(Integer auid);

    int updateByPrimaryKeySelective(TUserTrace record);

    int updateByPrimaryKey(TUserTrace record);
}