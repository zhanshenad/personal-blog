package com.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.common.Result;
import com.blog.entity.Article;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Result<?> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long categoryId) {
        IPage<Article> result;
        if (categoryId != null) {
            result = articleService.getArticlesByCategory(page, size, categoryId);
        } else {
            result = articleService.getPublishedArticles(page, size);
        }
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        Article article = articleService.getArticleDetail(id);
        if (article == null) {
            return Result.error(404, "文章不存在");
        }
        return Result.success(article);
    }

    @GetMapping("/search")
    public Result<?> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(articleService.searchArticles(page, size, keyword));
    }
}
