package com.xq.mapper;

import com.xq.domain.ProxyDhistory;

public interface ProxyDhistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProxyDhistory record);

    int insertSelective(ProxyDhistory record);

    ProxyDhistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProxyDhistory record);

    int updateByPrimaryKey(ProxyDhistory record);
}