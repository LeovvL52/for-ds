package com.gjq.dto.camera;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 摄像头设备创建DTO
 */
@Data
public class CameraCreateDTO {
    /**
     * 设备名称
     */
    @NotBlank(message = "设备名称不能为空")
    private String name;

    /**
     * 安装位置
     */
    @NotBlank(message = "安装位置不能为空")
    private String location;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * RTSP地址
     */
    private String rtspUrl;
} 
 