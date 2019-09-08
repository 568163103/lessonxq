package com.xq.mapper;

import com.xq.domain.TUserLink;

public interface TUserLinkMapper {
    int deleteByPrimaryKey(String fullAmid);

    int insert(TUserLink record);

    int insertSelective(TUserLink record);

    TUserLink selectByPrimaryKey(String fullAmid);

    int updateByPrimaryKeySelective(TUserLink record);

    int updateByPrimaryKey(TUserLink record);
}