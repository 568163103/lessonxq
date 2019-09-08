package com.xq.mapper;

import com.xq.domain.TMenu;

public interface TMenuMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(TMenu record);

    int insertSelective(TMenu record);

    TMenu selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(TMenu record);

    int updateByPrimaryKey(TMenu record);
}