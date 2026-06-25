package com.voter.auth;

import com.voter.entity.VotingUser;
import com.voter.exception.BusinessException;
import com.voter.mapper.VotingUserMapper;
import com.voter.utils.JwtUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证工具类
 */
public class AuthUtil {

    private static VotingUserMapper votingUserMapper;
    private static TokenBlacklistService tokenBlacklistService;

    /**
     * 设置UserMapper（通过配置类注入）
     */
    public static void setVotingUserMapper(VotingUserMapper mapper) {
        votingUserMapper = mapper;
    }

    /**
     * 设置TokenBlacklistService（通过配置类注入）
     */
    public static void setTokenBlacklistService(TokenBlacklistService service) {
        tokenBlacklistService = service;
    }

    /**
     * 从请求头中获取token
     *
     * @return token
     */
    public static String getTokenFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    public static Long getCurrentUserId() {
        String token = getTokenFromRequest();
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(401, "未提供认证信息");
        }

        try {
            validateCurrentToken(token);
            return JwtUtil.getUserIdFromToken(token);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(401, "无效的认证信息");
        }
    }

    /**
     * 获取当前登录用户名
     *
     * @return 用户名
     */
    public static String getCurrentUsername() {
        String token = getTokenFromRequest();
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(401, "未提供认证信息");
        }

        try {
            validateCurrentToken(token);
            return JwtUtil.getUsernameFromToken(token);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(401, "无效的认证信息");
        }
    }

    /**
     * 获取当前登录用户完整信息
     *
     * @return 用户信息
     */
    public static VotingUser getCurrentUser() {
        Long userId = getCurrentUserId();

        if (votingUserMapper == null) {
            throw new BusinessException(500, "系统配置错误");
        }

        VotingUser user = votingUserMapper.selectById(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException(401, "用户不存在");
        }

        // 清空密码字段
        user.setPassword(null);
        return user;
    }

    /**
     * 验证token是否有效
     *
     * @param token token
     * @return true-有效，false-无效
     */
    public static boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        try {
            // 检查是否在黑名单中
            if (tokenBlacklistService != null && tokenBlacklistService.isBlacklisted(token)) {
                return false;
            }
            return !JwtUtil.isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证当前请求的token是否有效（包含黑名单检查）
     */
    private static void validateCurrentToken(String token) {
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(401, "认证信息已过期");
        }
        if (tokenBlacklistService != null && tokenBlacklistService.isBlacklisted(token)) {
            throw new BusinessException(401, "认证信息已失效，请重新登录");
        }
    }
}
