package com.xq.mapper;

import com.xq.domain.GroupResource;
import org.apache.ibatis.annotations.Param;

public interface GroupResourceMapper {
    int deleteByPrimaryKey(@Param("groupId") String groupId, @Param("resourceId") String resourceId);

    int insert(GroupResource record);

    int insertSelective(GroupResource record);
}