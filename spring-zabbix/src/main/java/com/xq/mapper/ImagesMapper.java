package com.xq.mapper;

import com.xq.domain.Images;

public interface ImagesMapper {
    int deleteByPrimaryKey(Long imageid);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(Long imageid);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);
}