package com.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

@Data
@TableName("article_tags")
public class ArticleTag {
    @TableId(type = IdType.INPUT)
    private Long articleId;
    private Long tagId;
}
