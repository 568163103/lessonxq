package com.xq.mapper;

import com.xq.domain.HistoryLog;

public interface HistoryLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HistoryLog record);

    int insertSelective(HistoryLog record);

    HistoryLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HistoryLog record);

    int updateByPrimaryKey(HistoryLog record);
}