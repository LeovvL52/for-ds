package com.gjq.dto.fire;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 火灾事件处理DTO
 */
@Data
public class FireEventHandleDTO {
    /**
     * 事件ID
     */
    @NotNull(message = "事件ID不能为空")
    private Long id;

    /**
     * 处理人ID
     */
    @NotNull(message = "处理人ID不能为空")
    private Long handlerId;

    /**
     * 处理结果
     */
    @NotBlank(message = "处理结果不能为空")
    private String handleResult;
} 