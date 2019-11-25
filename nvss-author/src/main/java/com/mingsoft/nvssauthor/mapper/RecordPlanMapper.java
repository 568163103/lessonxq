package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.RecordPlan;

public interface RecordPlanMapper {
    int deleteByPrimaryKey(String name);

    int insert(RecordPlan record);

    int insertSelective(RecordPlan record);

    RecordPlan selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(RecordPlan record);

    int updateByPrimaryKey(RecordPlan record);
}