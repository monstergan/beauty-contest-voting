package com.voter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voter.dto.CandidateResponseDTO;
import com.voter.dto.PageRequestDTO;
import com.voter.dto.RespBody;
import com.voter.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 候选人控制器
 */
@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    /**
     * 分页查询候选人列表（包含图片信息）
     *
     * @param dto 分页参数
     * @return 候选人分页列表
     */
    @GetMapping("/list")
    public RespBody<Page<CandidateResponseDTO>> getCandidateList(PageRequestDTO dto) {
        // 验证并修正参数
        dto.validate();

        // 查询分页数据
        Page<CandidateResponseDTO> page = candidateService.getCandidateList(dto.getPageNum(), dto.getPageSize());

        return RespBody.ok(page);
    }

    /**
     * 根据ID查询单个候选人信息
     *
     * @param id 候选人ID
     * @return 候选人详情
     */
    @GetMapping("/getById")
    public RespBody<CandidateResponseDTO> getCandidateById(@RequestParam("id") Long id) {
        CandidateResponseDTO candidate = candidateService.getCandidateById(id);
        if (candidate == null) {
            return RespBody.fail("候选人不存在");
        }
        return RespBody.ok(candidate);
    }
}
