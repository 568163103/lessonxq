package com.xq.mapper;

import com.xq.domain.Usrgrp;

public interface UsrgrpMapper {
    int deleteByPrimaryKey(Long usrgrpid);

    int insert(Usrgrp record);

    int insertSelective(Usrgrp record);

    Usrgrp selectByPrimaryKey(Long usrgrpid);

    int updateByPrimaryKeySelective(Usrgrp record);

    int updateByPrimaryKey(Usrgrp record);
}