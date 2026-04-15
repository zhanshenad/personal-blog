package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("SELECT c.*, COUNT(a.id) AS article_count FROM categories c " +
            "LEFT JOIN articles a ON c.id = a.category_id AND a.status = 1 " +
            "GROUP BY c.id ORDER BY c.sort_order")
    List<Category> selectWithCount();
}
