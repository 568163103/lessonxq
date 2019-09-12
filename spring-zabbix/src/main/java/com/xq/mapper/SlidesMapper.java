package com.xq.mapper;

import com.xq.domain.Slides;

public interface SlidesMapper {
    int deleteByPrimaryKey(Long slideid);

    int insert(Slides record);

    int insertSelective(Slides record);

    Slides selectByPrimaryKey(Long slideid);

    int updateByPrimaryKeySelective(Slides record);

    int updateByPrimaryKey(Slides record);
}