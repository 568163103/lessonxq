package com.xq.mapper;

import com.xq.domain.TbShieldUser;
import org.apache.ibatis.annotations.Param;

public interface TbShieldUserMapper {
    int deleteByPrimaryKey(@Param("shieldId") Integer shieldId, @Param("userId") String userId);

    int insert(TbShieldUser record);

    int insertSelective(TbShieldUser record);
}