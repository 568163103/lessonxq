package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.UserTree;
import org.apache.ibatis.annotations.Param;

public interface UserTreeMapper {
    int deleteByPrimaryKey(@Param("userId") String userId, @Param("resId") String resId, @Param("parentId") String parentId);

    int insert(UserTree record);

    int insertSelective(UserTree record);

    UserTree selectByPrimaryKey(@Param("userId") String userId, @Param("resId") String resId, @Param("parentId") String parentId);

    int updateByPrimaryKeySelective(UserTree record);

    int updateByPrimaryKey(UserTree record);
}