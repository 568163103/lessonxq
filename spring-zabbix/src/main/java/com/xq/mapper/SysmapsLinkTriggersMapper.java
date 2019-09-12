package com.xq.mapper;

import com.xq.domain.SysmapsLinkTriggers;

public interface SysmapsLinkTriggersMapper {
    int deleteByPrimaryKey(Long linktriggerid);

    int insert(SysmapsLinkTriggers record);

    int insertSelective(SysmapsLinkTriggers record);

    SysmapsLinkTriggers selectByPrimaryKey(Long linktriggerid);

    int updateByPrimaryKeySelective(SysmapsLinkTriggers record);

    int updateByPrimaryKey(SysmapsLinkTriggers record);
}