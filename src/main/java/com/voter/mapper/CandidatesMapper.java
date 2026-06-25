package com.voter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.voter.entity.Candidates;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CandidatesMapper extends BaseMapper<Candidates> {
}
