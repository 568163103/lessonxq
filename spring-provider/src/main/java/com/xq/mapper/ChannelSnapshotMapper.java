package com.xq.mapper;

import com.xq.domain.ChannelSnapshot;

public interface ChannelSnapshotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelSnapshot record);

    int insertSelective(ChannelSnapshot record);

    ChannelSnapshot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelSnapshot record);

    int updateByPrimaryKey(ChannelSnapshot record);
}