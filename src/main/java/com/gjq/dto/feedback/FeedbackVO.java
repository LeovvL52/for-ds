package com.gjq.dto.feedback;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户反馈详情VO
 */
@Data
public class FeedbackVO {
    /**
     * 反馈ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 反馈类型(1:故障报修 2:意见建议 3:其他)
     */
    private Integer type;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 处理状态(0:未处理 1:处理中 2:已处理)
     */
    private Integer status;

    /**
     * 回复内容
     */
    private String reply;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 