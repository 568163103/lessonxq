package com.xq.mapper;

import com.xq.domain.Services;

public interface ServicesMapper {
    int deleteByPrimaryKey(Long serviceid);

    int insert(Services record);

    int insertSelective(Services record);

    Services selectByPrimaryKey(Long serviceid);

    int updateByPrimaryKeySelective(Services record);

    int updateByPrimaryKey(Services record);
}