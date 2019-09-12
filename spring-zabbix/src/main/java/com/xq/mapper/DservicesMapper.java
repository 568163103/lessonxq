package com.xq.mapper;

import com.xq.domain.Dservices;

public interface DservicesMapper {
    int deleteByPrimaryKey(Long dserviceid);

    int insert(Dservices record);

    int insertSelective(Dservices record);

    Dservices selectByPrimaryKey(Long dserviceid);

    int updateByPrimaryKeySelective(Dservices record);

    int updateByPrimaryKey(Dservices record);
}