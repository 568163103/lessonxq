package com.xq.mapper;

import com.xq.domain.Auditlog;

public interface AuditlogMapper {
    int deleteByPrimaryKey(Long auditid);

    int insert(Auditlog record);

    int insertSelective(Auditlog record);

    Auditlog selectByPrimaryKey(Long auditid);

    int updateByPrimaryKeySelective(Auditlog record);

    int updateByPrimaryKey(Auditlog record);
}