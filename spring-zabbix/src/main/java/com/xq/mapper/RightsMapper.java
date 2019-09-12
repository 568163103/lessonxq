package com.xq.mapper;

import com.xq.domain.Rights;

public interface RightsMapper {
    int deleteByPrimaryKey(Long rightid);

    int insert(Rights record);

    int insertSelective(Rights record);

    Rights selectByPrimaryKey(Long rightid);

    int updateByPrimaryKeySelective(Rights record);

    int updateByPrimaryKey(Rights record);
}