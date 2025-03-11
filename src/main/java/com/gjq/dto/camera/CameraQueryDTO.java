package com.gjq.dto.camera;

import com.gjq.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 摄像头设备查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CameraQueryDTO extends PageDTO {
    /**
     * 设备名称
     */
    private String name;

    /**
     * 安装位置
     */
    private String location;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 状态(0:离线 1:在线 2:故障)
     */
    private Integer status;
} 