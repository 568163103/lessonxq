package com.xq.mapper;

import com.xq.domain.HistoryStr;

public interface HistoryStrMapper {
    int insert(HistoryStr record);

    int insertSelective(HistoryStr record);
}