package com.voter.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 投票请求DTO
 */
@Data
public class VoteDTO {

    /**
     * 候选人ID
     */
    @NotNull(message = "候选人ID不能为空")
    private Long candidateId;
}
