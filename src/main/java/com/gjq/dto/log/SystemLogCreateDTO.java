package com.gjq.dto.log;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 系统日志创建DTO
 */
@Data
public class SystemLogCreateDTO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 操作内容
     */
    @NotBlank(message = "操作内容不能为空")
    private String operation;

    /**
     * IP地址
     */
    private String ipAddress;
} 