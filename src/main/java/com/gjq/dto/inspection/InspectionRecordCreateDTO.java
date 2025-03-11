package com.gjq.dto.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消防检查记录创建DTO
 */
@Data
public class InspectionRecordCreateDTO {
    /**
     * 检查人ID
     */
    @NotNull(message = "检查人ID不能为空")
    private Long inspectorId;

    /**
     * 检查时间
     */
    @NotNull(message = "检查时间不能为空")
    private LocalDateTime inspectionTime;

    /**
     * 检查位置
     */
    @NotBlank(message = "检查位置不能为空")
    private String location;

    /**
     * 检查结果
     */
    @NotBlank(message = "检查结果不能为空")
    private String result;

    /**
     * 状态(0:异常 1:正常)
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 检查图片URLs
     */
    private String imageUrls;
} 