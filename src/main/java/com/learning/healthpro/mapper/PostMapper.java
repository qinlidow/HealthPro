package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PostMapper {

    ArrayList<Post> getAllPosts();

    ArrayList<Post> getPostsByCategory(String category);

    ArrayList<Post> getPostsByUserId(int userId);

    ArrayList<Post> getPostsByFollowingIds(@Param("ids") List<Integer> ids);

    ArrayList<Post> getPendingPosts();

    Post getPostById(int id);

    int insertPost(Post post);

    int addLike(int id);

    int addComment(int id);

    int updateStatus(@Param("id") int id, @Param("status") int status);

    int updateTop(@Param("id") int id, @Param("isTop") int isTop);

    int deletePostById(int id);
}
