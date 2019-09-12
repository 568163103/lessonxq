package com.xq.mapper;

import com.xq.domain.SysmapUsrgrp;

public interface SysmapUsrgrpMapper {
    int deleteByPrimaryKey(Long sysmapusrgrpid);

    int insert(SysmapUsrgrp record);

    int insertSelective(SysmapUsrgrp record);

    SysmapUsrgrp selectByPrimaryKey(Long sysmapusrgrpid);

    int updateByPrimaryKeySelective(SysmapUsrgrp record);

    int updateByPrimaryKey(SysmapUsrgrp record);
}