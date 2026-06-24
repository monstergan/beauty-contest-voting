package com.voter.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 候选人信息
 */
@Data
@TableName(value = "candidates")
public class Candidates {
    /**
     * 自增长主键
     */
    @TableId(value = "id",type = IdType.AUTO)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}