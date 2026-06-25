package com.voter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voter.dto.CandidateResponseDTO;
import com.voter.entity.CandidatePhotos;
import com.voter.entity.Candidates;
import com.voter.mapper.CandidatePhotosMapper;
import com.voter.mapper.CandidatesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidatesMapper candidatesMapper;
    private final CandidatePhotosMapper candidatePhotosMapper;

    /**
     * 分页查询候选人列表（包含图片信息）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    public Page<CandidateResponseDTO> getCandidateList(Integer pageNum, Integer pageSize) {
        // 创建分页对象
        Page<Candidates> candidatePage = new Page<>(pageNum, pageSize);

        // 创建查询条件：按ID倒序
        QueryWrapper<Candidates> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");

        // 执行分页查询
        Page<Candidates> resultPage = candidatesMapper.selectPage(candidatePage, queryWrapper);

        // 转换为DTO并填充图片信息
        Page<CandidateResponseDTO> dtoPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        List<CandidateResponseDTO> dtoList = resultPage.getRecords().stream()
                .map(candidate -> {
                    CandidateResponseDTO dto = CandidateResponseDTO.fromEntity(candidate);
                    // 查询该候选人的最新20张图片
                    dto.setPhotos(getCandidatePhotos(candidate.getId()));
                    return dto;
                })
                .collect(Collectors.toList());

        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    /**
     * 根据ID查询单个候选人信息（包含全部图片）
     *
     * @param id 候选人ID
     * @return 候选人详情，如果不存在则返回null
     */
    public CandidateResponseDTO getCandidateById(Long id) {
        Candidates candidate = candidatesMapper.selectById(id);
        if (candidate == null) {
            return null;
        }

        CandidateResponseDTO dto = CandidateResponseDTO.fromEntity(candidate);
        // 查询该候选人的全部图片
        dto.setPhotos(getAllCandidatePhotos(candidate.getId()));
        return dto;
    }

    /**
     * 获取候选人的最新20张图片（按创建时间倒序）
     *
     * @param candidateId 候选人ID
     * @return 图片列表
     */
    private List<CandidatePhotos> getCandidatePhotos(Long candidateId) {
        QueryWrapper<CandidatePhotos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("candidate_id", candidateId)
                .orderByDesc("create_time")
                .last("LIMIT 20");

        return candidatePhotosMapper.selectList(queryWrapper);
    }

    /**
     * 获取候选人的全部图片（按创建时间倒序）
     *
     * @param candidateId 候选人ID
     * @return 图片列表
     */
    private List<CandidatePhotos> getAllCandidatePhotos(Long candidateId) {
        QueryWrapper<CandidatePhotos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("candidate_id", candidateId)
                .orderByDesc("create_time");
        return candidatePhotosMapper.selectList(queryWrapper);
    }
}
