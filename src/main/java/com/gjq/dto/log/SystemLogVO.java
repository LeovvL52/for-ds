package com.gjq.dto.log;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统日志详情VO
 */
@Data
public class SystemLogVO {
    /**
     * 日志ID
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
     * 操作内容
     */
    private String operation;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
} 