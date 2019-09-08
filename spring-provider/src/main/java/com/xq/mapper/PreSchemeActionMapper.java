package com.xq.mapper;

import com.xq.domain.PreSchemeAction;
import org.apache.ibatis.annotations.Param;

public interface PreSchemeActionMapper {
    int deleteByPrimaryKey(@Param("schemeId") Integer schemeId, @Param("actionId") Integer actionId);

    int insert(PreSchemeAction record);

    int insertSelective(PreSchemeAction record);
}