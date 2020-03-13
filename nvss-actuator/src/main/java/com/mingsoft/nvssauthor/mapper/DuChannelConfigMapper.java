package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.DuChannelConfig;

public interface DuChannelConfigMapper {
    int deleteByPrimaryKey(String duChannelId);

    int insert(DuChannelConfig record);

    int insertSelective(DuChannelConfig record);

    DuChannelConfig selectByPrimaryKey(String duChannelId);

    int updateByPrimaryKeySelective(DuChannelConfig record);

    int updateByPrimaryKey(DuChannelConfig record);
}