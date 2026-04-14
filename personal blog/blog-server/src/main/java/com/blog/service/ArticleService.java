package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.Article;

public interface ArticleService extends IService<Article> {
    IPage<Article> getPublishedArticles(int page, int size);
    IPage<Article> getArticlesByCategory(int page, int size, Long categoryId);
    IPage<Article> searchArticles(int page, int size, String keyword);
    IPage<Article> getAllArticles(int page, int size);
    Article getArticleDetail(Long id);
    Article createArticle(Article article);
    Article updateArticle(Article article);
    void deleteArticle(Long id);
}
