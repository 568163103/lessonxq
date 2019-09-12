package com.xq.mapper;

import com.xq.domain.AuditlogDetails;

public interface AuditlogDetailsMapper {
    int deleteByPrimaryKey(Long auditdetailid);

    int insert(AuditlogDetails record);

    int insertSelective(AuditlogDetails record);

    AuditlogDetails selectByPrimaryKey(Long auditdetailid);

    int updateByPrimaryKeySelective(AuditlogDetails record);

    int updateByPrimaryKey(AuditlogDetails record);
}