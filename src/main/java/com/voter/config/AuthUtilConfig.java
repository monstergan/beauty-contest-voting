package com.voter.config;

import com.voter.mapper.VotingUserMapper;
import com.voter.auth.TokenBlacklistService;
import com.voter.auth.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * AuthUtil配置类
 * 用于注入VotingUserMapper和TokenBlacklistService到静态工具类
 */
@Component
@RequiredArgsConstructor
public class AuthUtilConfig {

    private final VotingUserMapper votingUserMapper;
    private final TokenBlacklistService tokenBlacklistService;

    @PostConstruct
    public void init() {
        AuthUtil.setVotingUserMapper(votingUserMapper);
        AuthUtil.setTokenBlacklistService(tokenBlacklistService);
    }
}
