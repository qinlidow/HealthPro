package com.learning.healthpro.service;

import com.learning.healthpro.entity.Post;

import java.util.ArrayList;
import java.util.List;

public interface PostService {

    ArrayList<Post> getAllPosts();

    ArrayList<Post> getPostsByCategory(String category);

    ArrayList<Post> getPostsByUserId(int userId);

    ArrayList<Post> getPostsByFollowingIds(List<Integer> ids);

    ArrayList<Post> getPendingPosts();

    Post getPostById(int id);

    void addPost(Post post);

    void likePost(int id);

    void commentPost(int id);

    void updateStatus(int id, int status);

    void updateTop(int id, int isTop);

    void deletePost(int id);
}
