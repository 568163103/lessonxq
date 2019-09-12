package com.xq.mapper;

import com.xq.domain.AutoregHost;

public interface AutoregHostMapper {
    int deleteByPrimaryKey(Long autoregHostid);

    int insert(AutoregHost record);

    int insertSelective(AutoregHost record);

    AutoregHost selectByPrimaryKey(Long autoregHostid);

    int updateByPrimaryKeySelective(AutoregHost record);

    int updateByPrimaryKey(AutoregHost record);
}