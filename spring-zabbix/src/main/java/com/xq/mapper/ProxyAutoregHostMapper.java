package com.xq.mapper;

import com.xq.domain.ProxyAutoregHost;

public interface ProxyAutoregHostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProxyAutoregHost record);

    int insertSelective(ProxyAutoregHost record);

    ProxyAutoregHost selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProxyAutoregHost record);

    int updateByPrimaryKey(ProxyAutoregHost record);
}