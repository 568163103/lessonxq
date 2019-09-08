package com.xq.mapper;

import com.xq.domain.SwitchPortInfo;

public interface SwitchPortInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SwitchPortInfo record);

    int insertSelective(SwitchPortInfo record);

    SwitchPortInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SwitchPortInfo record);

    int updateByPrimaryKey(SwitchPortInfo record);
}