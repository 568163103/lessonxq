package com.xq.mapper;

import com.xq.domain.PositionEncoder;
import org.apache.ibatis.annotations.Param;

public interface PositionEncoderMapper {
    int deleteByPrimaryKey(@Param("positionId") String positionId, @Param("encoderId") String encoderId);

    int insert(PositionEncoder record);

    int insertSelective(PositionEncoder record);
}