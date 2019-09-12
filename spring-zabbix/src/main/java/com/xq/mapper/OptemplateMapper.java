package com.xq.mapper;

import com.xq.domain.Optemplate;

public interface OptemplateMapper {
    int deleteByPrimaryKey(Long optemplateid);

    int insert(Optemplate record);

    int insertSelective(Optemplate record);

    Optemplate selectByPrimaryKey(Long optemplateid);

    int updateByPrimaryKeySelective(Optemplate record);

    int updateByPrimaryKey(Optemplate record);
}