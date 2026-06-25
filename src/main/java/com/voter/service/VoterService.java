package com.voter.service;

import com.voter.dto.LoginDTO;
import com.voter.dto.LoginResponseDTO;
import com.voter.dto.RegisterUserDTO;
import com.voter.entity.VotingUser;
import com.voter.exception.BusinessException;
import com.voter.mapper.VotingUserMapper;
import com.voter.utils.DigestUtil;
import com.voter.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class VoterService {

    private final VotingUserMapper votingUserMapper;

    public boolean register(RegisterUserDTO dto) {
        checkUser(dto, votingUserMapper);
        VotingUser user = dto.getVotingUser();
        int result = votingUserMapper.insert(user);
        return result > 0;
    }

    public LoginResponseDTO login(LoginDTO dto) {
        if (ObjectUtils.isEmpty(dto.getUsername())) {
            throw new BusinessException("用户名不能为空");
        }
        if (ObjectUtils.isEmpty(dto.getPassword())) {
            throw new BusinessException("密码不能为空");
        }

        VotingUser user = votingUserMapper.selectByName(dto.getUsername());
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException("用户不存在");
        }

        String encryptedPassword = DigestUtil.encrypt(dto.getPassword());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 生成token
        String accessToken = JwtUtil.generateAccessToken(user.getId(), user.getUserName());
        String refreshToken = JwtUtil.generateRefreshToken(user.getId(), user.getUserName());
        Long expiresIn = JwtUtil.getAccessTokenExpireTime();

        // 清空密码字段，不返回给前端
        user.setPassword(null);

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .userInfo(user)
                .build();
    }

    private static void checkUser(RegisterUserDTO dto, VotingUserMapper votingUserMapper) {
        if (ObjectUtils.isEmpty(dto.getUsername())) {
            throw new BusinessException("用户账户不能为空");
        }
        VotingUser oldUser = votingUserMapper.selectByName(dto.getUsername());
        if (!ObjectUtils.isEmpty(oldUser)) {
            throw new BusinessException("用户名已存在");
        }

        // 校验电话号码格式
        if (!ObjectUtils.isEmpty(dto.getPhone())) {
            String phoneRegex = "^1[3-9]\\d{9}$";
            if (!dto.getPhone().matches(phoneRegex)) {
                throw new BusinessException("电话号码格式不正确");
            }
        }

        // 校验birthdayYear必须是4位整数
        if (dto.getBirthdayYear() != null) {
            if (dto.getBirthdayYear() < 1000 || dto.getBirthdayYear() > 9999) {
                throw new BusinessException("出生年份必须是4位整数");
            }
        }
    }
}
