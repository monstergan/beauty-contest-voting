package com.voter.controller;

import com.voter.dto.LoginDTO;
import com.voter.dto.LoginResponseDTO;
import com.voter.dto.RegisterUserDTO;
import com.voter.dto.RespBody;
import com.voter.service.VoterService;
import com.voter.auth.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户登录
     *
     * @param dto 登录参数
     * @return 登录结果（包含token信息）
     */
    @PostMapping("/login")
    public RespBody<LoginResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        LoginResponseDTO response = voterService.login(dto);
        return RespBody.ok(response);
    }

    /**
     * 用户登出
     *
     * @return 登出结果
     */
    @PostMapping("/logout")
    public RespBody<String> logout() {
        String token = AuthUtil.getTokenFromRequest();
        voterService.logout(token);
        return RespBody.ok("登出成功");
    }
}
