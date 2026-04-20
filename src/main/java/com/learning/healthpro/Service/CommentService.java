package com.learning.healthpro.service;

import com.learning.healthpro.entity.Comment;

import java.util.ArrayList;

public interface CommentService {

    ArrayList<Comment> getCommentsByPostId(int postId);

    void addComment(Comment comment);

    void deleteComment(int id);
}
