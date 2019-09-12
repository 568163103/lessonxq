package com.xq.mapper;

import com.xq.domain.Opinventory;

public interface OpinventoryMapper {
    int deleteByPrimaryKey(Long operationid);

    int insert(Opinventory record);

    int insertSelective(Opinventory record);

    Opinventory selectByPrimaryKey(Long operationid);

    int updateByPrimaryKeySelective(Opinventory record);

    int updateByPrimaryKey(Opinventory record);
}