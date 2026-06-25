package com.voter.auth;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Token黑名单服务
 * 用于管理已登出的token
 */
@Service
public class TokenBlacklistService {

    /**
     * 黑名单存储（生产环境建议使用Redis）
     * key: token, value: 过期时间戳
     */
    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    /**
     * 将token加入黑名单
     *
     * @param token      token
     * @param expireTime 过期时间戳（毫秒）
     */
    public void addToBlacklist(String token, Long expireTime) {
        blacklist.put(token, expireTime);
        // 清理已过期的token（简单实现，生产环境建议使用定时任务）
        cleanExpiredTokens();
    }

    /**
     * 检查token是否在黑名单中
     *
     * @param token token
     * @return true-在黑名单中，false-不在黑名单中
     */
    public boolean isBlacklisted(String token) {
        if (!blacklist.containsKey(token)) {
            return false;
        }

        Long expireTime = blacklist.get(token);
        // 如果已过期，从黑名单移除
        if (expireTime < System.currentTimeMillis()) {
            blacklist.remove(token);
            return false;
        }

        return true;
    }

    /**
     * 清理已过期的token
     */
    private void cleanExpiredTokens() {
        long now = System.currentTimeMillis();
        blacklist.entrySet().removeIf(entry -> entry.getValue() < now);
    }

    /**
     * 获取黑名单大小（用于监控）
     *
     * @return 黑名单中的token数量
     */
    public int getBlacklistSize() {
        cleanExpiredTokens();
        return blacklist.size();
    }
}
