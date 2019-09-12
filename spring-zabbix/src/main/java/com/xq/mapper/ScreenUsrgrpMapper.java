package com.xq.mapper;

import com.xq.domain.ScreenUsrgrp;

public interface ScreenUsrgrpMapper {
    int deleteByPrimaryKey(Long screenusrgrpid);

    int insert(ScreenUsrgrp record);

    int insertSelective(ScreenUsrgrp record);

    ScreenUsrgrp selectByPrimaryKey(Long screenusrgrpid);

    int updateByPrimaryKeySelective(ScreenUsrgrp record);

    int updateByPrimaryKey(ScreenUsrgrp record);
}