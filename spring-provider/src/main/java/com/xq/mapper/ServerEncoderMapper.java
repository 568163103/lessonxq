package com.xq.mapper;

import com.xq.domain.ServerEncoder;

public interface ServerEncoderMapper {
    int insert(ServerEncoder record);

    int insertSelective(ServerEncoder record);
}