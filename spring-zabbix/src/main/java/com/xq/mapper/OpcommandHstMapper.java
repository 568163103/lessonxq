package com.xq.mapper;

import com.xq.domain.OpcommandHst;

public interface OpcommandHstMapper {
    int deleteByPrimaryKey(Long opcommandHstid);

    int insert(OpcommandHst record);

    int insertSelective(OpcommandHst record);

    OpcommandHst selectByPrimaryKey(Long opcommandHstid);

    int updateByPrimaryKeySelective(OpcommandHst record);

    int updateByPrimaryKey(OpcommandHst record);
}