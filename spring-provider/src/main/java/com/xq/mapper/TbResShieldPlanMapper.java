package com.xq.mapper;

import com.xq.domain.TbResShieldPlan;

public interface TbResShieldPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbResShieldPlan record);

    int insertSelective(TbResShieldPlan record);

    TbResShieldPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbResShieldPlan record);

    int updateByPrimaryKey(TbResShieldPlan record);
}