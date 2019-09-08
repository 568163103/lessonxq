package com.xq.mapper;

import com.xq.domain.TbShieldRes;
import org.apache.ibatis.annotations.Param;

public interface TbShieldResMapper {
    int deleteByPrimaryKey(@Param("shieldId") Integer shieldId, @Param("resId") String resId);

    int insert(TbShieldRes record);

    int insertSelective(TbShieldRes record);
}