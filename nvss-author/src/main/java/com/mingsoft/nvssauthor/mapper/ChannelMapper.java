package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.Channel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChannelMapper {
    int deleteByPrimaryKey(String id);

    int insert(Channel record);

    int insertSelective(Channel record);

    Channel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);

    List<Channel> getChannelList();
}