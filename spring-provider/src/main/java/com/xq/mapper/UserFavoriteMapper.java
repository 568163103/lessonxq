package com.xq.mapper;

import com.xq.domain.UserFavorite;

public interface UserFavoriteMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserFavorite record);

    int insertSelective(UserFavorite record);

    UserFavorite selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserFavorite record);

    int updateByPrimaryKey(UserFavorite record);
}