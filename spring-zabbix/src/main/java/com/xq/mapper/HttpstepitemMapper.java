package com.xq.mapper;

import com.xq.domain.Httpstepitem;

public interface HttpstepitemMapper {
    int deleteByPrimaryKey(Long httpstepitemid);

    int insert(Httpstepitem record);

    int insertSelective(Httpstepitem record);

    Httpstepitem selectByPrimaryKey(Long httpstepitemid);

    int updateByPrimaryKeySelective(Httpstepitem record);

    int updateByPrimaryKey(Httpstepitem record);
}