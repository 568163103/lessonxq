package com.xq.mapper;

import com.xq.domain.Slideshows;

public interface SlideshowsMapper {
    int deleteByPrimaryKey(Long slideshowid);

    int insert(Slideshows record);

    int insertSelective(Slideshows record);

    Slideshows selectByPrimaryKey(Long slideshowid);

    int updateByPrimaryKeySelective(Slideshows record);

    int updateByPrimaryKey(Slideshows record);
}