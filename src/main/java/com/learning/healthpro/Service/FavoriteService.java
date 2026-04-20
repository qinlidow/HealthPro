package com.learning.healthpro.service;

import com.learning.healthpro.entity.Favorite;
import com.learning.healthpro.entity.Post;

import java.util.ArrayList;

public interface FavoriteService {

    void favorite(int userId, int postId, String date);

    void unfavorite(int userId, int postId);

    boolean isFavorite(int userId, int postId);

    ArrayList<Favorite> getFavoriteList(int userId);

    ArrayList<Post> getFavoritePosts(int userId);

    int getFavoriteCount(int userId);
}
