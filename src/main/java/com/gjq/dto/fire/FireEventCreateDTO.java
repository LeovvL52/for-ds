package com.gjq.dto.fire;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 火灾事件创建DTO
 */
@Data
public class FireEventCreateDTO {
    /**
     * 关联摄像头ID
     */
    @NotNull(message = "摄像头ID不能为空")
    private Long cameraId;

    /**
     * 事件发生时间
     */
    @NotNull(message = "事件发生时间不能为空")
    private LocalDateTime eventTime;

    /**
     * 发生位置
     */
    @NotBlank(message = "发生位置不能为空")
    private String location;

    /**
     * 火情等级(1-5)
     */
    @NotNull(message = "火情等级不能为空")
    private Integer level;

    /**
     * 现场图片URL
     */
    private String imageUrl;

    /**
     * 事件描述
     */
    private String description;
} 
 