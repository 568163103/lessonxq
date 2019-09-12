package com.xq.mapper;

import com.xq.domain.OpmessageUsr;

public interface OpmessageUsrMapper {
    int deleteByPrimaryKey(Long opmessageUsrid);

    int insert(OpmessageUsr record);

    int insertSelective(OpmessageUsr record);

    OpmessageUsr selectByPrimaryKey(Long opmessageUsrid);

    int updateByPrimaryKeySelective(OpmessageUsr record);

    int updateByPrimaryKey(OpmessageUsr record);
}