package com.xq.mapper;

import com.xq.domain.Globalmacro;

public interface GlobalmacroMapper {
    int deleteByPrimaryKey(Long globalmacroid);

    int insert(Globalmacro record);

    int insertSelective(Globalmacro record);

    Globalmacro selectByPrimaryKey(Long globalmacroid);

    int updateByPrimaryKeySelective(Globalmacro record);

    int updateByPrimaryKey(Globalmacro record);
}