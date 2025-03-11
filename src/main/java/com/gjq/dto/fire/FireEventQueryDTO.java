package com.gjq.dto.fire;

import com.gjq.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 火灾事件查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FireEventQueryDTO extends PageDTO {
    /**
     * 关联摄像头ID
     */
    private Long cameraId;

    /**
     * 发生位置
     */
    private String location;

    /**
     * 火情等级(1-5)
     */
    private Integer level;

    /**
     * 处理状态(0:未处理 1:处理中 2:已处理)
     */
    private Integer status;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
} 