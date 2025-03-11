package com.gjq.dto.feedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户反馈回复DTO
 */
@Data
public class FeedbackReplyDTO {
    /**
     * 反馈ID
     */
    @NotNull(message = "反馈ID不能为空")
    private Long id;

    /**
     * 回复内容
     */
    @NotBlank(message = "回复内容不能为空")
    private String reply;
} 