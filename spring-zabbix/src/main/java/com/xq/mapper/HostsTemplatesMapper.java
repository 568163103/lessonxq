package com.xq.mapper;

import com.xq.domain.HostsTemplates;

public interface HostsTemplatesMapper {
    int deleteByPrimaryKey(Long hosttemplateid);

    int insert(HostsTemplates record);

    int insertSelective(HostsTemplates record);

    HostsTemplates selectByPrimaryKey(Long hosttemplateid);

    int updateByPrimaryKeySelective(HostsTemplates record);

    int updateByPrimaryKey(HostsTemplates record);
}