package com.xq.mapper;

import com.xq.domain.Media;

public interface MediaMapper {
    int deleteByPrimaryKey(Long mediaid);

    int insert(Media record);

    int insertSelective(Media record);

    Media selectByPrimaryKey(Long mediaid);

    int updateByPrimaryKeySelective(Media record);

    int updateByPrimaryKey(Media record);
}