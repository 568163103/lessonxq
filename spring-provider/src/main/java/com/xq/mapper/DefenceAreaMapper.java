package com.xq.mapper;

import com.xq.domain.DefenceArea;

public interface DefenceAreaMapper {
    int deleteByPrimaryKey(String userId);

    int insert(DefenceArea record);

    int insertSelective(DefenceArea record);

    DefenceArea selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(DefenceArea record);

    int updateByPrimaryKey(DefenceArea record);
}