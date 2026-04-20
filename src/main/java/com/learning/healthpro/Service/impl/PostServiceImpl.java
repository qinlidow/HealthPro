package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.Post;
import com.learning.healthpro.mapper.PostMapper;
import com.learning.healthpro.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public ArrayList<Post> getAllPosts() {
        return postMapper.getAllPosts();
    }

    @Override
    public ArrayList<Post> getPostsByCategory(String category) {
        return postMapper.getPostsByCategory(category);
    }

    @Override
    public ArrayList<Post> getPostsByUserId(int userId) {
        return postMapper.getPostsByUserId(userId);
    }

    @Override
    public ArrayList<Post> getPostsByFollowingIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return postMapper.getPostsByFollowingIds(ids);
    }

    @Override
    public ArrayList<Post> getPendingPosts() {
        return postMapper.getPendingPosts();
    }

    @Override
    public Post getPostById(int id) {
        return postMapper.getPostById(id);
    }

    @Override
    public void addPost(Post post) {
        postMapper.insertPost(post);
    }

    @Override
    public void likePost(int id) {
        postMapper.addLike(id);
    }

    @Override
    public void commentPost(int id) {
        postMapper.addComment(id);
    }

    @Override
    public void updateStatus(int id, int status) {
        postMapper.updateStatus(id, status);
    }

    @Override
    public void updateTop(int id, int isTop) {
        postMapper.updateTop(id, isTop);
    }

    @Override
    public void deletePost(int id) {
        postMapper.deletePostById(id);
    }
}
