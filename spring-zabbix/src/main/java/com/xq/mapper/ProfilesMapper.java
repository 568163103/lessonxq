package com.xq.mapper;

import com.xq.domain.Profiles;

public interface ProfilesMapper {
    int deleteByPrimaryKey(Long profileid);

    int insert(Profiles record);

    int insertSelective(Profiles record);

    Profiles selectByPrimaryKey(Long profileid);

    int updateByPrimaryKeySelective(Profiles record);

    int updateByPrimaryKey(Profiles record);
}