package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.Comment;
import com.learning.healthpro.mapper.CommentMapper;
import com.learning.healthpro.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public ArrayList<Comment> getCommentsByPostId(int postId) {
        return commentMapper.getCommentsByPostId(postId);
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public void deleteComment(int id) {
        commentMapper.deleteCommentById(id);
    }
}
