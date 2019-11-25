package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.TMenu;

public interface TMenuMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(TMenu record);

    int insertSelective(TMenu record);

    TMenu selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(TMenu record);

    int updateByPrimaryKey(TMenu record);
}