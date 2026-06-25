package com.voter.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtil {

    private JwtUtil() {

    }

    /**
     * 密钥
     */
    private static final String SECRET_KEY = "beauty-contest-voting-secret-key-2024";

    /**
     * access_token 过期时间：2小时
     */
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 2 * 60 * 60 * 1000;

    /**
     * refresh_token 过期时间：7天
     */
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

    /**
     * 生成access_token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return token
     */
    public static String generateAccessToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("tokenType", "access");
        return generateToken(claims, ACCESS_TOKEN_EXPIRE_TIME);
    }

    /**
     * 生成refresh_token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return token
     */
    public static String generateRefreshToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("tokenType", "refresh");
        return generateToken(claims, REFRESH_TOKEN_EXPIRE_TIME);
    }

    /**
     * 生成token
     *
     * @param claims     载荷
     * @param expireTime 过期时间（毫秒）
     * @return token
     */
    private static String generateToken(Map<String, Object> claims, long expireTime) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Claims
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证token是否过期
     *
     * @param token token
     * @return true-已过期，false-未过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 从token中获取用户ID
     *
     * @param token token
     * @return 用户ID
     */
    public static Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return Long.valueOf(claims.get("userId").toString());
    }

    /**
     * 从token中获取用户名
     *
     * @param token token
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("username").toString();
    }

    /**
     * 获取access_token过期时间（秒）
     *
     * @return 过期时间
     */
    public static long getAccessTokenExpireTime() {
        return ACCESS_TOKEN_EXPIRE_TIME / 1000;
    }
}
