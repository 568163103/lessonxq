package com.xq.mapper;

import com.xq.domain.Ids;
import org.apache.ibatis.annotations.Param;

public interface IdsMapper {
    int deleteByPrimaryKey(@Param("tableName") String tableName, @Param("fieldName") String fieldName);

    int insert(Ids record);

    int insertSelective(Ids record);

    Ids selectByPrimaryKey(@Param("tableName") String tableName, @Param("fieldName") String fieldName);

    int updateByPrimaryKeySelective(Ids record);

    int updateByPrimaryKey(Ids record);
}