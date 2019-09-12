package com.xq.mapper;

import com.xq.domain.Config;

public interface ConfigMapper {
    int deleteByPrimaryKey(Long configid);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(Long configid);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);
}