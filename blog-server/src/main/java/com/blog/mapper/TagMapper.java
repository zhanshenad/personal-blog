package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    @Select("SELECT t.*, COUNT(at2.article_id) AS article_count FROM tags t " +
            "LEFT JOIN article_tags at2 ON t.id = at2.tag_id " +
            "LEFT JOIN articles a ON at2.article_id = a.id AND a.status = 1 " +
            "GROUP BY t.id")
    List<Tag> selectWithCount();

    @Select("SELECT t.* FROM tags t " +
            "INNER JOIN article_tags at2 ON t.id = at2.tag_id " +
            "WHERE at2.article_id = #{articleId}")
    List<Tag> selectByArticleId(Long articleId);
}
