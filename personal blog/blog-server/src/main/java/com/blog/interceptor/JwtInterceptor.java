package com.blog.interceptor;

import com.blog.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS 请求直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || token.isEmpty()) {
            sendError(response, 401, "未登录，请先登录");
            return false;
        }

        try {
            jwtUtil.verifyToken(token);
            // 将用户信息存入 request attribute
            request.setAttribute("userId", jwtUtil.getUserId(token));
            request.setAttribute("username", jwtUtil.getUsername(token));
            request.setAttribute("role", jwtUtil.getRole(token));
            return true;
        } catch (Exception e) {
            sendError(response, 401, "Token无效或已过期");
            return false;
        }
    }

    private void sendError(HttpServletResponse response, int status, String message) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(Map.of("code", status, "message", message)));
    }
}
