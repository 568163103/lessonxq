package com.xq.mapper;

import com.xq.domain.TUserRole;
import org.apache.ibatis.annotations.Param;

public interface TUserRoleMapper {
    int deleteByPrimaryKey(@Param("rid") Integer rid, @Param("amid") String amid);

    int insert(TUserRole record);

    int insertSelective(TUserRole record);

    TUserRole selectByPrimaryKey(@Param("rid") Integer rid, @Param("amid") String amid);

    int updateByPrimaryKeySelective(TUserRole record);

    int updateByPrimaryKey(TUserRole record);
}