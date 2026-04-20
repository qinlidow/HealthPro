package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.Follow;
import com.learning.healthpro.mapper.FollowMapper;
import com.learning.healthpro.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public void follow(int followerId, int followingId, String date) {
        if (followerId == followingId) return;
        Follow existing = followMapper.getFollow(followerId, followingId);
        if (existing == null) {
            Follow follow = Follow.builder()
                    .follower_id(followerId)
                    .following_id(followingId)
                    .date(date)
                    .build();
            followMapper.insertFollow(follow);
        }
    }

    @Override
    public void unfollow(int followerId, int followingId) {
        followMapper.deleteFollow(followerId, followingId);
    }

    @Override
    public boolean isFollowing(int followerId, int followingId) {
        return followMapper.getFollow(followerId, followingId) != null;
    }

    @Override
    public ArrayList<Follow> getFollowingList(int followerId) {
        return followMapper.getFollowingList(followerId);
    }

    @Override
    public ArrayList<Follow> getFollowerList(int followingId) {
        return followMapper.getFollowerList(followingId);
    }

    @Override
    public int getFollowingCount(int followerId) {
        return followMapper.getFollowingCount(followerId);
    }

    @Override
    public int getFollowerCount(int followingId) {
        return followMapper.getFollowerCount(followingId);
    }
}
