package com.xq.mapper;

import com.xq.domain.Escalations;

public interface EscalationsMapper {
    int deleteByPrimaryKey(Long escalationid);

    int insert(Escalations record);

    int insertSelective(Escalations record);

    Escalations selectByPrimaryKey(Long escalationid);

    int updateByPrimaryKeySelective(Escalations record);

    int updateByPrimaryKey(Escalations record);
}