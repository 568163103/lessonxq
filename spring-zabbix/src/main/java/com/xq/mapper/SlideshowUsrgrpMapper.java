package com.xq.mapper;

import com.xq.domain.SlideshowUsrgrp;

public interface SlideshowUsrgrpMapper {
    int deleteByPrimaryKey(Long slideshowusrgrpid);

    int insert(SlideshowUsrgrp record);

    int insertSelective(SlideshowUsrgrp record);

    SlideshowUsrgrp selectByPrimaryKey(Long slideshowusrgrpid);

    int updateByPrimaryKeySelective(SlideshowUsrgrp record);

    int updateByPrimaryKey(SlideshowUsrgrp record);
}