package com.xq.mapper;

import com.xq.domain.SlideshowUser;

public interface SlideshowUserMapper {
    int deleteByPrimaryKey(Long slideshowuserid);

    int insert(SlideshowUser record);

    int insertSelective(SlideshowUser record);

    SlideshowUser selectByPrimaryKey(Long slideshowuserid);

    int updateByPrimaryKeySelective(SlideshowUser record);

    int updateByPrimaryKey(SlideshowUser record);
}