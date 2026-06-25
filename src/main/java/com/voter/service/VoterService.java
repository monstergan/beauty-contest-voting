package com.voter.service;

import com.voter.dto.RegisterUserDTO;
import com.voter.entity.VotingUser;
import com.voter.exception.BusinessException;
import com.voter.mapper.VotingUserMapper;
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
