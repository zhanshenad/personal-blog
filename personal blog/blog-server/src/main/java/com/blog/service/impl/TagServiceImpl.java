package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.Tag;
import com.blog.mapper.TagMapper;
import com.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getTagsWithCount() {
        return tagMapper.selectWithCount();
    }

    @Override
    public List<Tag> getTagsByArticleId(Long articleId) {
        return tagMapper.selectByArticleId(articleId);
    }
}
