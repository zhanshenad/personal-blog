package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("tags")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;

    @TableField(exist = false)
    private Integer articleCount;
}
