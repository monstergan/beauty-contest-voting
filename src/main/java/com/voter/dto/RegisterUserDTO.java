package com.voter.dto;

import com.voter.entity.VotingUser;
import com.voter.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterUserDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 出生年
     */
    @NotNull(message = "出生年不能为空")
    private Integer birthdayYear;

    /**
     * 电话号码
     */
    private String phone;


    public VotingUser getVotingUser() {
        VotingUser user = new VotingUser();
        user.setUserName(username);
        user.setPassword(DigestUtil.encrypt(password));
        user.setBirthdayYear(birthdayYear);
        user.setPhone(phone);
        user.setIsDelete(false);
        user.setCreateUser("system");
        user.setUpdateUser("system");
        return user;
    }
}
