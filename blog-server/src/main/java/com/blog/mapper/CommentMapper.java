package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("SELECT c.*, u.username, u.avatar FROM comments c " +
            "LEFT JOIN users u ON c.user_id = u.id " +
            "WHERE c.article_id = #{articleId} ORDER BY c.created_at ASC")
    List<Comment> selectByArticleId(Long articleId);

    @Select("SELECT c.*, u.username, u.avatar, a.title AS article_title " +
            "FROM comments c " +
            "LEFT JOIN users u ON c.user_id = u.id " +
            "LEFT JOIN articles a ON c.article_id = a.id " +
            "ORDER BY c.created_at DESC")
    List<Comment> selectAllWithDetails();
}
