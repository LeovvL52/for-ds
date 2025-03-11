package com.gjq.dto.log;

import com.gjq.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统日志查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemLogQueryDTO extends PageDTO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
} 