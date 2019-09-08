package com.xq.mapper;

import com.xq.domain.TPrivData;
import org.apache.ibatis.annotations.Param;

public interface TPrivDataMapper {
    int deleteByPrimaryKey(@Param("userId") String userId, @Param("privCode") String privCode);

    int insert(TPrivData record);

    int insertSelective(TPrivData record);

    TPrivData selectByPrimaryKey(@Param("userId") String userId, @Param("privCode") String privCode);

    int updateByPrimaryKeySelective(TPrivData record);

    int updateByPrimaryKey(TPrivData record);
}