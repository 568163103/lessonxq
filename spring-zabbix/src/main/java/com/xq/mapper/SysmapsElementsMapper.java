package com.xq.mapper;

import com.xq.domain.SysmapsElements;

public interface SysmapsElementsMapper {
    int deleteByPrimaryKey(Long selementid);

    int insert(SysmapsElements record);

    int insertSelective(SysmapsElements record);

    SysmapsElements selectByPrimaryKey(Long selementid);

    int updateByPrimaryKeySelective(SysmapsElements record);

    int updateByPrimaryKey(SysmapsElements record);
}