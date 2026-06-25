package com.voter.dto;

import lombok.Data;

/**
 * 分页请求参数
 */
@Data
public class PageRequestDTO {

    /**
     * 当前页码，默认第1页
     */
    private Integer pageNum = 1;

    /**
     * 每页数量，默认20条
     */
    private Integer pageSize = 20;

    /**
     * 验证并修正参数
     */
    public void validate() {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 20;
        }
        // 限制每页最大数量，防止查询过多数据
        if (pageSize > 100) {
            pageSize = 100;
        }
    }
}
