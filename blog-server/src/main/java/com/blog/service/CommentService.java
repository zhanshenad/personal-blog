package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    List<Comment> getCommentsByArticleId(Long articleId);
    Comment addComment(Comment comment);
    List<Comment> getAllComments();
}
