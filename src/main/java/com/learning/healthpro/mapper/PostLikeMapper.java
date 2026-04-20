package com.learning.healthpro.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostLikeMapper {

    int insertLike(@Param("userId") int userId, @Param("postId") int postId, @Param("date") String date);

    int deleteLike(@Param("userId") int userId, @Param("postId") int postId);

    boolean existsLike(@Param("userId") int userId, @Param("postId") int postId);
}
