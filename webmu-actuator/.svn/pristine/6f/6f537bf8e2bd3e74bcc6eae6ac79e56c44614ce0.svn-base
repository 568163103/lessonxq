package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.Encoder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EncoderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Encoder record);

    int insertSelective(Encoder record);

    Encoder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Encoder record);

    int updateByPrimaryKey(Encoder record);
    List<Encoder> findStatusCount(int status);

    List<Encoder> findEncoder(Map<String,Object> params);
}