package com.voter.service;

import com.voter.dto.RegisterUserDTO;
import com.voter.entity.VotingUser;
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
            throw new RuntimeException("用户账户不能为空");
        }
        VotingUser oldUser = votingUserMapper.selectByName(dto.getUsername());
        if (!ObjectUtils.isEmpty(oldUser)) {
            throw new RuntimeException("用户名已存在");
        }
    }
}
