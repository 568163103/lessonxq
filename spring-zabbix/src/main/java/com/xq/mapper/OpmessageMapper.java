package com.xq.mapper;

import com.xq.domain.Opmessage;

public interface OpmessageMapper {
    int deleteByPrimaryKey(Long operationid);

    int insert(Opmessage record);

    int insertSelective(Opmessage record);

    Opmessage selectByPrimaryKey(Long operationid);

    int updateByPrimaryKeySelective(Opmessage record);

    int updateByPrimaryKey(Opmessage record);
}