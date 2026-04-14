package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{articleId}")
    public Result<?> listByArticle(@PathVariable Long articleId) {
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);
        return Result.success(comments);
    }
}
