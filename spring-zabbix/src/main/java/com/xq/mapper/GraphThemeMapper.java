package com.xq.mapper;

import com.xq.domain.GraphTheme;

public interface GraphThemeMapper {
    int deleteByPrimaryKey(Long graphthemeid);

    int insert(GraphTheme record);

    int insertSelective(GraphTheme record);

    GraphTheme selectByPrimaryKey(Long graphthemeid);

    int updateByPrimaryKeySelective(GraphTheme record);

    int updateByPrimaryKey(GraphTheme record);
}