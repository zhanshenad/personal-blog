package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("comments")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long articleId;
    private Long userId;
    private Long parentId;
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    // 非数据库字段
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String avatar;
    @TableField(exist = false)
    private String articleTitle;
}
