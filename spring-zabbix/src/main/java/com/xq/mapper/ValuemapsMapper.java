package com.xq.mapper;

import com.xq.domain.Valuemaps;

public interface ValuemapsMapper {
    int deleteByPrimaryKey(Long valuemapid);

    int insert(Valuemaps record);

    int insertSelective(Valuemaps record);

    Valuemaps selectByPrimaryKey(Long valuemapid);

    int updateByPrimaryKeySelective(Valuemaps record);

    int updateByPrimaryKey(Valuemaps record);
}