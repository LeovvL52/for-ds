package com.gjq.dto.inspection;

import com.gjq.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 消防检查记录查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InspectionRecordQueryDTO extends PageDTO {
    /**
     * 检查人ID
     */
    private Long inspectorId;

    /**
     * 检查位置
     */
    private String location;

    /**
     * 状态(0:异常 1:正常)
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