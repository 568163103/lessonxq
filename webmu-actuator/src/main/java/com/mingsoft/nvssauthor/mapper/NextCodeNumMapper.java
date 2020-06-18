package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.NextCodeNum;
import org.springframework.stereotype.Component;

@Component
public interface NextCodeNumMapper {
    int deleteByPrimaryKey(String name);

    int insert(NextCodeNum record);

    int insertSelective(NextCodeNum record);

    NextCodeNum selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(NextCodeNum record);

    int updateByPrimaryKey(NextCodeNum record);
}