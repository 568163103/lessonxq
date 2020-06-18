package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.SwitchStateRecordPort;

public interface SwitchStateRecordPortMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SwitchStateRecordPort record);

    int insertSelective(SwitchStateRecordPort record);

    SwitchStateRecordPort selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SwitchStateRecordPort record);

    int updateByPrimaryKey(SwitchStateRecordPort record);
}