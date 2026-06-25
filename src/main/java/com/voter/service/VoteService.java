package com.voter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.voter.auth.AuthUtil;
import com.voter.dto.VoteDTO;
import com.voter.entity.Candidates;
import com.voter.entity.VoteRecords;
import com.voter.entity.VotingUser;
import com.voter.exception.BusinessException;
import com.voter.mapper.CandidatesMapper;
import com.voter.mapper.VoteRecordsMapper;
import com.voter.mapper.VotingUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VotingUserMapper votingUserMapper;
    private final CandidatesMapper candidatesMapper;
    private final VoteRecordsMapper voteRecordsMapper;

    /**
     * 用户投票
     *
     * @param dto 投票信息
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean vote(VoteDTO dto) {
        Long userId = AuthUtil.getCurrentUserId();

        VotingUser user = votingUserMapper.selectById(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException("用户不存在");
        }
        if (user.getIsVoted() != null && user.getIsVoted()) {
            throw new BusinessException("您已经投过票了，不能重复投票");
        }

        Candidates candidate = candidatesMapper.selectById(dto.getCandidateId());
        if (ObjectUtils.isEmpty(candidate)) {
            throw new BusinessException("候选人不存在");
        }

        QueryWrapper<VoteRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("voter_id", userId)
                .eq("candidate_id", dto.getCandidateId());
        VoteRecords existingRecord = voteRecordsMapper.selectOne(queryWrapper);
        if (!ObjectUtils.isEmpty(existingRecord)) {
            throw new BusinessException("您已经给该候选人投过票了");
        }

        user.setIsVoted(true);
        user.setUpdateTime(new Date());
        votingUserMapper.updateById(user);

        candidate.setVotesNumber((candidate.getVotesNumber() == null ? 0 : candidate.getVotesNumber()) + 1);
        candidate.setUpdateTime(new Date());
        candidatesMapper.updateById(candidate);

        VoteRecords voteRecord = new VoteRecords();
        voteRecord.setVoterId(userId);
        voteRecord.setCandidateId(dto.getCandidateId());
        voteRecord.setCreateTime(new Date());
        voteRecordsMapper.insert(voteRecord);

        return true;
    }
}
