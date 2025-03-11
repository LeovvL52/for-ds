package com.gjq.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统日志查询参数
 */
@Data
public class LogQueryDTO {
    /**
     * 当前页码
     */
    private Long current;

    /**
     * 每页大小
     */
    private Long size;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 日志类型：1-登录日志，2-操作日志，3-异常日志
     */
    private Integer type;

    /**
     * 操作状态：0-失败，1-成功
     */
    private Integer status;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
} 