package com.voter.controller;

import com.voter.dto.RespBody;
import com.voter.dto.VoteDTO;
import com.voter.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 投票控制器
 */
@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    /**
     * 用户投票
     *
     * @param dto 投票信息
     * @return 是否成功
     */
    @PostMapping("/vote")
    public RespBody<Boolean> vote(@RequestBody @Valid VoteDTO dto) {
        boolean result = voteService.vote(dto);
        return RespBody.ok(result);
    }
}
