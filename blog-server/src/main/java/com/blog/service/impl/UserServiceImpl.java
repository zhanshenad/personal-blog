package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.entity.User;
import com.blog.mapper.UserMapper;
import com.blog.service.UserService;
import com.blog.util.JwtUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User findByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public User register(User user) {
        if (findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setRole("ROLE_USER");
        if (user.getNickname() == null || user.getNickname().isEmpty()) {
            user.setNickname(user.getUsername());
        }
        save(user);
        user.setPassword(null);
        return user;
    }

    @Override
    public String login(String username, String password) {
        User user = findByUsername(username);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        return jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
    }

    @Override
    public User getUserInfo(Long userId) {
        User user = getById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }
}
