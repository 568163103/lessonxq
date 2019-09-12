package com.xq.mapper;

import com.xq.domain.OpmessageGrp;

public interface OpmessageGrpMapper {
    int deleteByPrimaryKey(Long opmessageGrpid);

    int insert(OpmessageGrp record);

    int insertSelective(OpmessageGrp record);

    OpmessageGrp selectByPrimaryKey(Long opmessageGrpid);

    int updateByPrimaryKeySelective(OpmessageGrp record);

    int updateByPrimaryKey(OpmessageGrp record);
}