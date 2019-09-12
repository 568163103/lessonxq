package com.xq.mapper;

import com.xq.domain.ApplicationTemplate;

public interface ApplicationTemplateMapper {
    int deleteByPrimaryKey(Long applicationTemplateid);

    int insert(ApplicationTemplate record);

    int insertSelective(ApplicationTemplate record);

    ApplicationTemplate selectByPrimaryKey(Long applicationTemplateid);

    int updateByPrimaryKeySelective(ApplicationTemplate record);

    int updateByPrimaryKey(ApplicationTemplate record);
}