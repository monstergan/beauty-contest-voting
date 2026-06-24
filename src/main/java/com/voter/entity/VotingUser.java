package com.voter.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@TableName(value = "voting_user")
public class VotingUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增长主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
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

    /**
     * 是否已经投票
     */
    private Boolean isVoted;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建人信息
     */
    private String createUser;

    /**
     * 更新人名称
     */
    private String updateUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}