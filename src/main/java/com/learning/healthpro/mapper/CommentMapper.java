package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CommentMapper {

    ArrayList<Comment> getCommentsByPostId(int postId);

    int insertComment(Comment comment);

    int deleteCommentById(int id);
}
