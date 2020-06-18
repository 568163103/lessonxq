package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.VideowallWindowsGroup;
import org.apache.ibatis.annotations.Param;

public interface VideowallWindowsGroupMapper {
    int deleteByPrimaryKey(@Param("videoWallId") Integer videoWallId, @Param("windowsNum") Integer windowsNum, @Param("subWindowsNum") Integer subWindowsNum);

    int insert(VideowallWindowsGroup record);

    int insertSelective(VideowallWindowsGroup record);

    VideowallWindowsGroup selectByPrimaryKey(@Param("videoWallId") Integer videoWallId, @Param("windowsNum") Integer windowsNum, @Param("subWindowsNum") Integer subWindowsNum);

    int updateByPrimaryKeySelective(VideowallWindowsGroup record);

    int updateByPrimaryKey(VideowallWindowsGroup record);
}