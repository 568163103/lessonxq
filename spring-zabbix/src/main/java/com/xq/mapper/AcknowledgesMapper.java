package com.xq.mapper;

import com.xq.domain.Acknowledges;

public interface AcknowledgesMapper {
    int deleteByPrimaryKey(Long acknowledgeid);

    int insert(Acknowledges record);

    int insertSelective(Acknowledges record);

    Acknowledges selectByPrimaryKey(Long acknowledgeid);

    int updateByPrimaryKeySelective(Acknowledges record);

    int updateByPrimaryKey(Acknowledges record);
}