package com.xq.mapper;

import com.xq.domain.Opgroup;

public interface OpgroupMapper {
    int deleteByPrimaryKey(Long opgroupid);

    int insert(Opgroup record);

    int insertSelective(Opgroup record);

    Opgroup selectByPrimaryKey(Long opgroupid);

    int updateByPrimaryKeySelective(Opgroup record);

    int updateByPrimaryKey(Opgroup record);
}