package com.xq.mapper;

import com.xq.domain.VideoWall;

public interface VideoWallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoWall record);

    int insertSelective(VideoWall record);

    VideoWall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoWall record);

    int updateByPrimaryKey(VideoWall record);
}