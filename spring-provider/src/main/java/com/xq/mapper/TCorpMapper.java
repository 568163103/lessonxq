package com.xq.mapper;

import com.xq.domain.TCorp;

public interface TCorpMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(TCorp record);

    int insertSelective(TCorp record);

    TCorp selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(TCorp record);

    int updateByPrimaryKey(TCorp record);
}