package com.xq.mapper;

import com.xq.domain.ProxyHistory;

public interface ProxyHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProxyHistory record);

    int insertSelective(ProxyHistory record);

    ProxyHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProxyHistory record);

    int updateByPrimaryKey(ProxyHistory record);
}