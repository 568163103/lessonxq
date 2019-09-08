package com.xq.mapper;

import com.xq.domain.SwitchStateRecord;

public interface SwitchStateRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SwitchStateRecord record);

    int insertSelective(SwitchStateRecord record);

    SwitchStateRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SwitchStateRecord record);

    int updateByPrimaryKey(SwitchStateRecord record);
}