package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.User;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        try {
            User registered = userService.register(user);
            return Result.success(registered);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        try {
            String token = userService.login(params.get("username"), params.get("password"));
            User user = userService.findByUsername(params.get("username"));
            user.setPassword(null);
            return Result.success(Map.of("token", token, "user", user));
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result<?> getUserInfo(@RequestParam Long userId) {
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }
}
