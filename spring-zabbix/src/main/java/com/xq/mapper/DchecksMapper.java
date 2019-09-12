package com.xq.mapper;

import com.xq.domain.Dchecks;

public interface DchecksMapper {
    int deleteByPrimaryKey(Long dcheckid);

    int insert(Dchecks record);

    int insertSelective(Dchecks record);

    Dchecks selectByPrimaryKey(Long dcheckid);

    int updateByPrimaryKeySelective(Dchecks record);

    int updateByPrimaryKey(Dchecks record);
}