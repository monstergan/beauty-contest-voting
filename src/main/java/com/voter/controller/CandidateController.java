package com.voter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voter.dto.PageRequestDTO;
import com.voter.dto.RespBody;
import com.voter.entity.Candidates;
import com.voter.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 候选人控制器
 */
@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    /**
     * 分页查询候选人列表
     *
     * @param dto 分页参数
     * @return 候选人分页列表
     */
    @GetMapping("/list")
    public RespBody<Page<Candidates>> getCandidateList(PageRequestDTO dto) {
        // 验证并修正参数
        dto.validate();

        // 查询分页数据
        Page<Candidates> page = candidateService.getCandidateList(dto.getPageNum(), dto.getPageSize());

        return RespBody.ok(page);
    }
}
