package com.xq.mapper;

import com.xq.domain.SysmapsLinks;

public interface SysmapsLinksMapper {
    int deleteByPrimaryKey(Long linkid);

    int insert(SysmapsLinks record);

    int insertSelective(SysmapsLinks record);

    SysmapsLinks selectByPrimaryKey(Long linkid);

    int updateByPrimaryKeySelective(SysmapsLinks record);

    int updateByPrimaryKey(SysmapsLinks record);
}