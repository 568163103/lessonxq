package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.TUserLink;

public interface TUserLinkMapper {
    int deleteByPrimaryKey(String fullAmid);

    int insert(TUserLink record);

    int insertSelective(TUserLink record);

    TUserLink selectByPrimaryKey(String fullAmid);

    int updateByPrimaryKeySelective(TUserLink record);

    int updateByPrimaryKey(TUserLink record);
}