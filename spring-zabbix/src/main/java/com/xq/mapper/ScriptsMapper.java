package com.xq.mapper;

import com.xq.domain.Scripts;

public interface ScriptsMapper {
    int deleteByPrimaryKey(Long scriptid);

    int insert(Scripts record);

    int insertSelective(Scripts record);

    Scripts selectByPrimaryKey(Long scriptid);

    int updateByPrimaryKeySelective(Scripts record);

    int updateByPrimaryKey(Scripts record);
}