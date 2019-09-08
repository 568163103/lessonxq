package com.xq.mapper;

import com.xq.domain.TerminalUser;
import org.apache.ibatis.annotations.Param;

public interface TerminalUserMapper {
    int deleteByPrimaryKey(@Param("terminalId") String terminalId, @Param("userId") String userId);

    int insert(TerminalUser record);

    int insertSelective(TerminalUser record);
}