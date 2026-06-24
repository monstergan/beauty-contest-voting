package com.voter.controller;

import com.voter.dto.RegisterUserDTO;
import com.voter.dto.RespBody;
import com.voter.entity.VotingUser;
import com.voter.service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/voter")
@RequiredArgsConstructor
public class VoterController {

    private final VoterService voterService;

    /**
     * 注册用户信息
     *
     * @param dto 参数
     * @return 是否成功
     */
    @PostMapping("/regist")
    public RespBody<Boolean> register(@RequestBody @Valid RegisterUserDTO dto) {
        boolean isSuccess = voterService.register(dto);
        return RespBody.ok(isSuccess);
    }
}
