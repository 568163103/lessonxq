package com.xq.mapper;

import com.xq.domain.History;

public interface HistoryMapper {
    int insert(History record);

    int insertSelective(History record);
}