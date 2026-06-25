package com.voter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voter.entity.Candidates;
import com.voter.mapper.CandidatesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidatesMapper candidatesMapper;

    /**
     * 分页查询候选人列表
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    public Page<Candidates> getCandidateList(Integer pageNum, Integer pageSize) {
        // 创建分页对象
        Page<Candidates> page = new Page<>(pageNum, pageSize);

        // 创建查询条件：按ID倒序
        QueryWrapper<Candidates> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        // 执行分页查询
        return candidatesMapper.selectPage(page, queryWrapper);
    }
}
