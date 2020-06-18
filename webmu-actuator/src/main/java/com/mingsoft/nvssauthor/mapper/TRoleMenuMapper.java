package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.TRoleMenu;
import org.apache.ibatis.annotations.Param;

public interface TRoleMenuMapper {
    int deleteByPrimaryKey(@Param("rid") Integer rid, @Param("mid") Integer mid);

    int insert(TRoleMenu record);

    int insertSelective(TRoleMenu record);

    TRoleMenu selectByPrimaryKey(@Param("rid") Integer rid, @Param("mid") Integer mid);

    int updateByPrimaryKeySelective(TRoleMenu record);

    int updateByPrimaryKey(TRoleMenu record);
}