package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("SELECT a.*, u.nickname AS author_name, c.name AS category_name " +
            "FROM articles a LEFT JOIN users u ON a.user_id = u.id " +
            "LEFT JOIN categories c ON a.category_id = c.id " +
            "WHERE a.status = 1 AND (a.title LIKE CONCAT('%',#{keyword},'%') OR a.content LIKE CONCAT('%',#{keyword},'%')) " +
            "ORDER BY a.created_at DESC")
    IPage<Article> searchArticles(Page<Article> page, @Param("keyword") String keyword);

    @Select("SELECT a.*, u.nickname AS author_name, c.name AS category_name " +
            "FROM articles a LEFT JOIN users u ON a.user_id = u.id " +
            "LEFT JOIN categories c ON a.category_id = c.id " +
            "WHERE a.status = 1 ORDER BY a.created_at DESC")
    IPage<Article> selectPublishedPages(Page<Article> page);

    @Select("SELECT a.*, u.nickname AS author_name, c.name AS category_name " +
            "FROM articles a LEFT JOIN users u ON a.user_id = u.id " +
            "LEFT JOIN categories c ON a.category_id = c.id " +
            "WHERE a.status = 1 AND a.category_id = #{categoryId} ORDER BY a.created_at DESC")
    IPage<Article> selectByCategoryId(Page<Article> page, @Param("categoryId") Long categoryId);

    @Update("UPDATE articles SET view_count = view_count + 1 WHERE id = #{id}")
    void incrementViewCount(@Param("id") Long id);

    @Select("SELECT a.*, u.nickname AS author_name, c.name AS category_name " +
            "FROM articles a LEFT JOIN users u ON a.user_id = u.id " +
            "LEFT JOIN categories c ON a.category_id = c.id " +
            "ORDER BY a.created_at DESC")
    IPage<Article> selectAllPages(Page<Article> page);
}
