package com.learning.healthpro.service;

public interface PostLikeService {

    boolean like(int userId, int postId);

    boolean unlike(int userId, int postId);

    boolean isLiked(int userId, int postId);
}
