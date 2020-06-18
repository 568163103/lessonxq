package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.PlatformRes;

public interface PlatformResMapper {
    int deleteByPrimaryKey(String id);

    int insert(PlatformRes record);

    int insertSelective(PlatformRes record);

    PlatformRes selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PlatformRes record);

    int updateByPrimaryKey(PlatformRes record);
}