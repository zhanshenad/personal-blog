package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.Comment;
import com.blog.mapper.CommentMapper;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentMapper.selectByArticleId(articleId);
    }

    @Override
    public Comment addComment(Comment comment) {
        save(comment);
        return comment;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentMapper.selectAllWithDetails();
    }
}
