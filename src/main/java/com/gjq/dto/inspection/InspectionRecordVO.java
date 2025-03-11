package com.gjq.dto.inspection;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消防检查记录详情VO
 */
@Data
public class InspectionRecordVO {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 检查人ID
     */
    private Long inspectorId;

    /**
     * 检查人姓名
     */
    private String inspectorName;

    /**
     * 检查时间
     */
    private LocalDateTime inspectionTime;

    /**
     * 检查位置
     */
    private String location;

    /**
     * 检查结果
     */
    private String result;

    /**
     * 状态(0:异常 1:正常)
     */
    private Integer status;

    /**
     * 检查图片URLs
     */
    private String imageUrls;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 