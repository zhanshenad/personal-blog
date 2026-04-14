package com.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.entity.Article;
import com.blog.entity.ArticleTag;
import com.blog.entity.Tag;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.ArticleTagMapper;
import com.blog.mapper.TagMapper;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public IPage<Article> getPublishedArticles(int page, int size) {
        IPage<Article> result = articleMapper.selectPublishedPages(new Page<>(page, size));
        result.getRecords().forEach(a -> a.setTags(tagMapper.selectByArticleId(a.getId())));
        return result;
    }

    @Override
    public IPage<Article> getArticlesByCategory(int page, int size, Long categoryId) {
        IPage<Article> result = articleMapper.selectByCategoryId(new Page<>(page, size), categoryId);
        result.getRecords().forEach(a -> a.setTags(tagMapper.selectByArticleId(a.getId())));
        return result;
    }

    @Override
    public IPage<Article> searchArticles(int page, int size, String keyword) {
        IPage<Article> result = articleMapper.searchArticles(new Page<>(page, size), keyword);
        result.getRecords().forEach(a -> a.setTags(tagMapper.selectByArticleId(a.getId())));
        return result;
    }

    @Override
    public IPage<Article> getAllArticles(int page, int size) {
        IPage<Article> result = articleMapper.selectAllPages(new Page<>(page, size));
        result.getRecords().forEach(a -> a.setTags(tagMapper.selectByArticleId(a.getId())));
        return result;
    }

    @Override
    public Article getArticleDetail(Long id) {
        Article article = articleMapper.selectById(id);
        if (article != null) {
            articleMapper.incrementViewCount(id);
            article.setViewCount(article.getViewCount() + 1);
            article.setTags(tagMapper.selectByArticleId(id));
        }
        return article;
    }

    @Override
    @Transactional
    public Article createArticle(Article article) {
        article.setViewCount(0);
        save(article);
        saveArticleTags(article.getId(), article.getTagIds());
        return article;
    }

    @Override
    @Transactional
    public Article updateArticle(Article article) {
        updateById(article);
        // 先删除旧的标签关联
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, article.getId()));
        saveArticleTags(article.getId(), article.getTagIds());
        return article;
    }

    @Override
    public void deleteArticle(Long id) {
        removeById(id);
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, id));
    }

    private void saveArticleTags(Long articleId, List<Long> tagIds) {
        if (tagIds != null && !tagIds.isEmpty()) {
            for (Long tagId : tagIds) {
                ArticleTag at = new ArticleTag();
                at.setArticleId(articleId);
                at.setTagId(tagId);
                articleTagMapper.insert(at);
            }
        }
    }
}
