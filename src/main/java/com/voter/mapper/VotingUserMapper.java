package com.voter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.voter.entity.VotingUser;
import org.apache.ibatis.annotations.Param;


public interface VotingUserMapper extends BaseMapper<VotingUser> {
    VotingUser selectByName(@Param("userName") String userName);
}
