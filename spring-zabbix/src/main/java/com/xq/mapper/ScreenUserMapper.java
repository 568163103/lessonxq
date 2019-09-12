package com.xq.mapper;

import com.xq.domain.ScreenUser;

public interface ScreenUserMapper {
    int deleteByPrimaryKey(Long screenuserid);

    int insert(ScreenUser record);

    int insertSelective(ScreenUser record);

    ScreenUser selectByPrimaryKey(Long screenuserid);

    int updateByPrimaryKeySelective(ScreenUser record);

    int updateByPrimaryKey(ScreenUser record);
}