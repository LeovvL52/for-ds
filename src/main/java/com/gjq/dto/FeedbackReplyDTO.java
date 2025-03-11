package com.gjq.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 用户反馈回复参数
 */
@Data
public class FeedbackReplyDTO {
    /**
     * 反馈ID
     */
    @NotNull(message = "反馈ID不能为空")
    private Long id;

    /**
     * 处理状态：0-未处理，1-处理中，2-已处理
     */
    @NotNull(message = "处理状态不能为空")
    private Integer status;

    /**
     * 回复内容
     */
    @NotBlank(message = "回复内容不能为空")
    private String reply;
} 