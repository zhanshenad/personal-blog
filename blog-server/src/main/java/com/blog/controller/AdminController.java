package com.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.common.Result;
import com.blog.entity.*;
import com.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    // ===== Dashboard =====
    @GetMapping("/dashboard")
    public Result<?> dashboard() {
        long articleCount = articleService.count();
        long commentCount = commentService.count();
        long categoryCount = categoryService.count();
        long tagCount = tagService.count();
        return Result.success(Map.of(
                "articleCount", articleCount,
                "commentCount", commentCount,
                "categoryCount", categoryCount,
                "tagCount", tagCount
        ));
    }

    // ===== Article Management =====
    @GetMapping("/articles")
    public Result<?> listArticles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        IPage<Article> result = articleService.getAllArticles(page, size);
        return Result.success(result);
    }

    @PostMapping("/articles")
    public Result<?> createArticle(@RequestBody Article article,
                                   @RequestAttribute("userId") Long userId) {
        article.setUserId(userId);
        return Result.success(articleService.createArticle(article));
    }

    @PutMapping("/articles/{id}")
    public Result<?> updateArticle(@PathVariable Long id,
                                   @RequestBody Article article,
                                   @RequestAttribute("userId") Long userId) {
        article.setId(id);
        article.setUserId(userId);
        return Result.success(articleService.updateArticle(article));
    }

    @DeleteMapping("/articles/{id}")
    public Result<?> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Result.success();
    }

    // ===== Category Management =====
    @PostMapping("/categories")
    public Result<?> createCategory(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success(category);
    }

    @PutMapping("/categories/{id}")
    public Result<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        categoryService.updateById(category);
        return Result.success();
    }

    @DeleteMapping("/categories/{id}")
    public Result<?> deleteCategory(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.success();
    }

    // ===== Tag Management =====
    @PostMapping("/tags")
    public Result<?> createTag(@RequestBody Tag tag) {
        tagService.save(tag);
        return Result.success(tag);
    }

    @DeleteMapping("/tags/{id}")
    public Result<?> deleteTag(@PathVariable Long id) {
        tagService.removeById(id);
        return Result.success();
    }

    // ===== Comment Management =====
    @PostMapping("/comments/article/{articleId}")
    public Result<?> addComment(@PathVariable Long articleId,
                                @RequestBody Comment comment,
                                @RequestAttribute("userId") Long userId) {
        comment.setArticleId(articleId);
        comment.setUserId(userId);
        commentService.addComment(comment);
        return Result.success();
    }

    @GetMapping("/comments")
    public Result<?> listComments() {
        List<Comment> comments = commentService.getAllComments();
        return Result.success(comments);
    }

    @DeleteMapping("/comments/{id}")
    public Result<?> deleteComment(@PathVariable Long id) {
        commentService.removeById(id);
        return Result.success();
    }
}
