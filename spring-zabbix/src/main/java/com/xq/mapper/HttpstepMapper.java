package com.xq.mapper;

import com.xq.domain.Httpstep;

public interface HttpstepMapper {
    int deleteByPrimaryKey(Long httpstepid);

    int insert(Httpstep record);

    int insertSelective(Httpstep record);

    Httpstep selectByPrimaryKey(Long httpstepid);

    int updateByPrimaryKeySelective(Httpstep record);

    int updateByPrimaryKey(Httpstep record);
}