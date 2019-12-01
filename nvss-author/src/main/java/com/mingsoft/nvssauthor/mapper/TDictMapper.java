package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.TDict;

import java.util.List;

public interface TDictMapper {
    int deleteByPrimaryKey(Integer did);

    int insert(TDict record);

    int insertSelective(TDict record);

    TDict selectByPrimaryKey(Integer did);

    int updateByPrimaryKeySelective(TDict record);

    int updateByPrimaryKey(TDict record);

    List<TDict> findChannelTypeDict(int preId);
}