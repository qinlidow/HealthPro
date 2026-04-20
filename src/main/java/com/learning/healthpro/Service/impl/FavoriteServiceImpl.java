package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.Favorite;
import com.learning.healthpro.entity.Post;
import com.learning.healthpro.mapper.FavoriteMapper;
import com.learning.healthpro.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public void favorite(int userId, int postId, String date) {
        Favorite existing = favoriteMapper.getFavorite(userId, postId);
        if (existing == null) {
            Favorite favorite = Favorite.builder()
                    .user_id(userId)
                    .post_id(postId)
                    .date(date)
                    .build();
            favoriteMapper.insertFavorite(favorite);
        }
    }

    @Override
    public void unfavorite(int userId, int postId) {
        favoriteMapper.deleteFavorite(userId, postId);
    }

    @Override
    public boolean isFavorite(int userId, int postId) {
        return favoriteMapper.getFavorite(userId, postId) != null;
    }

    @Override
    public ArrayList<Favorite> getFavoriteList(int userId) {
        return favoriteMapper.getFavoriteList(userId);
    }

    @Override
    public ArrayList<Post> getFavoritePosts(int userId) {
        return favoriteMapper.getFavoritePosts(userId);
    }

    @Override
    public int getFavoriteCount(int userId) {
        return favoriteMapper.getFavoriteCount(userId);
    }
}
