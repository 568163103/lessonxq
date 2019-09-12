package com.xq.mapper;

import com.xq.domain.MediaType;

public interface MediaTypeMapper {
    int deleteByPrimaryKey(Long mediatypeid);

    int insert(MediaType record);

    int insertSelective(MediaType record);

    MediaType selectByPrimaryKey(Long mediatypeid);

    int updateByPrimaryKeySelective(MediaType record);

    int updateByPrimaryKey(MediaType record);
}