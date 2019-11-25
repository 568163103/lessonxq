package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.VideoWall;

public interface VideoWallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoWall record);

    int insertSelective(VideoWall record);

    VideoWall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoWall record);

    int updateByPrimaryKey(VideoWall record);
}