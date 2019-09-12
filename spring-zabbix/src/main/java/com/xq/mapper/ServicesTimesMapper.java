package com.xq.mapper;

import com.xq.domain.ServicesTimes;

public interface ServicesTimesMapper {
    int deleteByPrimaryKey(Long timeid);

    int insert(ServicesTimes record);

    int insertSelective(ServicesTimes record);

    ServicesTimes selectByPrimaryKey(Long timeid);

    int updateByPrimaryKeySelective(ServicesTimes record);

    int updateByPrimaryKey(ServicesTimes record);
}