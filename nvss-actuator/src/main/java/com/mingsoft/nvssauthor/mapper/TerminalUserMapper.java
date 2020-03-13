package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.TerminalUser;
import org.apache.ibatis.annotations.Param;

public interface TerminalUserMapper {
    int deleteByPrimaryKey(@Param("terminalId") String terminalId, @Param("userId") String userId);

    int insert(TerminalUser record);

    int insertSelective(TerminalUser record);
}