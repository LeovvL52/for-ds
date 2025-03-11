package com.gjq.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户反馈查询参数
 */
@Data
public class FeedbackQueryDTO {
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
     * 反馈类型：1-故障报修，2-意见建议，3-其他
     */
    private Integer type;

    /**
     * 处理状态：0-未处理，1-处理中，2-已处理
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