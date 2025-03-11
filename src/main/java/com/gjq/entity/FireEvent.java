package com.gjq.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 火灾事件记录实体类
 */
@Data
@TableName("fire_event")
public class FireEvent {
    /**
     * 事件ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联摄像头ID
     */
    private Long cameraId;

    /**
     * 事件发生时间
     */
    private LocalDateTime eventTime;

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
     * 现场图片URL
     */
    private String imageUrl;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 火灾类型
     */
    @TableField(value = "type")
    private String type;
} 