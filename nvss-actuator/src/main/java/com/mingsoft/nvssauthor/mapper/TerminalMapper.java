package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.Terminal;

public interface TerminalMapper {
    int deleteByPrimaryKey(String id);

    int insert(Terminal record);

    int insertSelective(Terminal record);

    Terminal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Terminal record);

    int updateByPrimaryKey(Terminal record);
}