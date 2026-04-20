package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.Favorite;
import com.learning.healthpro.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface FavoriteMapper {

    int insertFavorite(Favorite favorite);

    int deleteFavorite(@Param("userId") int userId, @Param("postId") int postId);

    Favorite getFavorite(@Param("userId") int userId, @Param("postId") int postId);

    ArrayList<Favorite> getFavoriteList(int userId);

    ArrayList<Post> getFavoritePosts(int userId);

    int getFavoriteCount(int userId);
}
