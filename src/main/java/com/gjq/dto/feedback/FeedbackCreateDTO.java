package com.gjq.dto.feedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户反馈创建DTO
 */
@Data
public class FeedbackCreateDTO {
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 反馈类型(1:故障报修 2:意见建议 3:其他)
     */
    @NotNull(message = "反馈类型不能为空")
    private Integer type;

    /**
     * 反馈内容
     */
    @NotBlank(message = "反馈内容不能为空")
    private String content;
} 