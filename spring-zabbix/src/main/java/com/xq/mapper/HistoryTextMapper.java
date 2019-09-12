package com.xq.mapper;

import com.xq.domain.HistoryText;

public interface HistoryTextMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HistoryText record);

    int insertSelective(HistoryText record);

    HistoryText selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HistoryText record);

    int updateByPrimaryKey(HistoryText record);
}