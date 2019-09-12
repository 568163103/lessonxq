package com.xq.mapper;

import com.xq.domain.ServicesLinks;

public interface ServicesLinksMapper {
    int deleteByPrimaryKey(Long linkid);

    int insert(ServicesLinks record);

    int insertSelective(ServicesLinks record);

    ServicesLinks selectByPrimaryKey(Long linkid);

    int updateByPrimaryKeySelective(ServicesLinks record);

    int updateByPrimaryKey(ServicesLinks record);
}