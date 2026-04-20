package com.learning.healthpro.service.impl;

import com.learning.healthpro.mapper.PostLikeMapper;
import com.learning.healthpro.mapper.PostMapper;
import com.learning.healthpro.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    private PostLikeMapper postLikeMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public boolean like(int userId, int postId) {
        if (postLikeMapper.existsLike(userId, postId)) {
            return false;
        }
        String date = java.time.LocalDate.now().toString();
        postLikeMapper.insertLike(userId, postId, date);
        postMapper.addLike(postId);
        return true;
    }

    @Override
    public boolean unlike(int userId, int postId) {
        if (!postLikeMapper.existsLike(userId, postId)) {
            return false;
        }
        postLikeMapper.deleteLike(userId, postId);
        postMapper.reduceLike(postId);
        return true;
    }

    @Override
    public boolean isLiked(int userId, int postId) {
        return postLikeMapper.existsLike(userId, postId);
    }
}
