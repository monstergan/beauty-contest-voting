package com.voter.dto;

import com.voter.entity.VotingUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 访问令牌过期时间（秒）
     */
    private Long expiresIn;

    /**
     * 用户信息
     */
    private VotingUser userInfo;
}
