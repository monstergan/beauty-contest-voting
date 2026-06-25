package com.voter.dto;

import com.voter.entity.CandidatePhotos;
import com.voter.entity.Candidates;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 候选人响应DTO（包含图片信息）
 */
@Data
public class CandidateResponseDTO {

    /**
     * 候选人ID
     */
    private Long id;

    /**
     * 候选人姓名
     */
    private String candidateName;

    /**
     * 候选人年龄
     */
    private Integer age;

    /**
     * 候选人视频链接
     */
    private String videoUrl;

    /**
     * 候选人介绍
     */
    private String introduction;

    /**
     * 投票数
     */
    private Integer votesNumber;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 候选人图片列表（最新20张，按创建时间倒序）
     */
    private List<CandidatePhotos> photos;

    /**
     * 从Candidates实体转换
     */
    public static CandidateResponseDTO fromEntity(Candidates candidate) {
        CandidateResponseDTO dto = new CandidateResponseDTO();
        dto.setId(candidate.getId());
        dto.setCandidateName(candidate.getCandidateName());
        dto.setAge(candidate.getAge());
        dto.setVideoUrl(candidate.getVideoUrl());
        dto.setIntroduction(candidate.getIntroduction());
        dto.setVotesNumber(candidate.getVotesNumber());
        dto.setCreateTime(candidate.getCreateTime());
        dto.setUpdateTime(candidate.getUpdateTime());
        return dto;
    }
}
