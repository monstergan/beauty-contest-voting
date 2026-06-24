package com.voter.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 投票记录表
 */
@Data
@TableName(value = "vote_records")
public class VoteRecords implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增长主键
     */
    private Long id;

    /**
     * 候选人ID
     */
    private Long candidateId;

    /**
     * 用户ID
     */
    private Long voterId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}