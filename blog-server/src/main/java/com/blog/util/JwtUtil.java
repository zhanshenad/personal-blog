package com.blog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Long userId, String username, String role) {
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("username", username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        return verifier.verify(token);
    }

    public Long getUserId(String token) {
        return verifyToken(token).getClaim("userId").asLong();
    }

    public String getUsername(String token) {
        return verifyToken(token).getClaim("username").asString();
    }

    public String getRole(String token) {
        return verifyToken(token).getClaim("role").asString();
    }
}
