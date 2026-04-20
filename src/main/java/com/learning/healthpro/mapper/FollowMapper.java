package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface FollowMapper {

    int insertFollow(Follow follow);

    int deleteFollow(@Param("followerId") int followerId, @Param("followingId") int followingId);

    Follow getFollow(@Param("followerId") int followerId, @Param("followingId") int followingId);

    ArrayList<Follow> getFollowingList(int followerId);

    ArrayList<Follow> getFollowerList(int followingId);

    int getFollowingCount(int followerId);

    int getFollowerCount(int followingId);
}
