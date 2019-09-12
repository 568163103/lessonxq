package com.xq.mapper;

import com.xq.domain.Opcommand;

public interface OpcommandMapper {
    int deleteByPrimaryKey(Long operationid);

    int insert(Opcommand record);

    int insertSelective(Opcommand record);

    Opcommand selectByPrimaryKey(Long operationid);

    int updateByPrimaryKeySelective(Opcommand record);

    int updateByPrimaryKey(Opcommand record);
}