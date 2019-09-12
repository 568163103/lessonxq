package com.xq.mapper;

import com.xq.domain.OpcommandGrp;

public interface OpcommandGrpMapper {
    int deleteByPrimaryKey(Long opcommandGrpid);

    int insert(OpcommandGrp record);

    int insertSelective(OpcommandGrp record);

    OpcommandGrp selectByPrimaryKey(Long opcommandGrpid);

    int updateByPrimaryKeySelective(OpcommandGrp record);

    int updateByPrimaryKey(OpcommandGrp record);
}