package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("articles")
public class Article {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long categoryId;
    private String title;
    private String summary;
    private String content;
    private String coverImage;
    private Integer viewCount;
    private Integer status;  // 0-草稿 1-发布
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    // 非数据库字段
    @TableField(exist = false)
    private String authorName;
    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private List<Tag> tags;
    @TableField(exist = false)
    private List<Long> tagIds;
}
