package com.xq.mapper;

import com.xq.domain.Regexps;

public interface RegexpsMapper {
    int deleteByPrimaryKey(Long regexpid);

    int insert(Regexps record);

    int insertSelective(Regexps record);

    Regexps selectByPrimaryKey(Long regexpid);

    int updateByPrimaryKeySelective(Regexps record);

    int updateByPrimaryKey(Regexps record);
}