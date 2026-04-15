package com.blog.service;

import com.blog.entity.User;

public interface UserService {
    User findByUsername(String username);
    User register(User user);
    String login(String username, String password);
    User getUserInfo(Long userId);
}
