package com.learning.healthpro.service;

import com.learning.healthpro.entity.Follow;

import java.util.ArrayList;

public interface FollowService {

    void follow(int followerId, int followingId, String date);

    void unfollow(int followerId, int followingId);

    boolean isFollowing(int followerId, int followingId);

    ArrayList<Follow> getFollowingList(int followerId);

    ArrayList<Follow> getFollowerList(int followingId);

    int getFollowingCount(int followerId);

    int getFollowerCount(int followingId);
}
