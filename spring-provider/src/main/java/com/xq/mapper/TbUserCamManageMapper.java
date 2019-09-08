package com.xq.mapper;

import com.xq.domain.TbUserCamManage;

public interface TbUserCamManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbUserCamManage record);

    int insertSelective(TbUserCamManage record);

    TbUserCamManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbUserCamManage record);

    int updateByPrimaryKey(TbUserCamManage record);
}