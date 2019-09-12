package com.xq.mapper;

import com.xq.domain.Interface;

public interface InterfaceMapper {
    int deleteByPrimaryKey(Long interfaceid);

    int insert(Interface record);

    int insertSelective(Interface record);

    Interface selectByPrimaryKey(Long interfaceid);

    int updateByPrimaryKeySelective(Interface record);

    int updateByPrimaryKey(Interface record);
}