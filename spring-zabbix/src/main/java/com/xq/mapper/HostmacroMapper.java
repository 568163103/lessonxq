package com.xq.mapper;

import com.xq.domain.Hostmacro;

public interface HostmacroMapper {
    int deleteByPrimaryKey(Long hostmacroid);

    int insert(Hostmacro record);

    int insertSelective(Hostmacro record);

    Hostmacro selectByPrimaryKey(Long hostmacroid);

    int updateByPrimaryKeySelective(Hostmacro record);

    int updateByPrimaryKey(Hostmacro record);
}